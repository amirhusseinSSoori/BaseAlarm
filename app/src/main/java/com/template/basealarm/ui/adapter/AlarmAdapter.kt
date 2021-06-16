package com.template.basealarm.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.template.basealarm.R
import com.template.basealarm.data.db.entity.AlarmEntity
import com.template.basealarm.databinding.ItemCustomLayoutBinding
import com.template.basealarm.domain.entity.Alarm
import kotlinx.coroutines.launch

class AlarmAdapter() : RecyclerView.Adapter<AlarmAdapter.Holder>() {
    private val differCallback = object : DiffUtil.ItemCallback<Alarm>() {
        override fun areItemsTheSame(
            oldItem: Alarm,
            newItem: Alarm
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: Alarm,
            newItem: Alarm
        ): Boolean = oldItem.id == newItem.id
    }


    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            ItemCustomLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    inner class Holder(val bind: ItemCustomLayoutBinding) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Alarm) {
            bind.itemTxtDate.text = data.date
            bind.itemTxtTime.text = data.time
        }

    }

    override fun onBindViewHolder(holder: AlarmAdapter.Holder, position: Int) {
        val currentItem = differ.currentList[position]
        holder.bind(currentItem)
    }


    override fun getItemCount(): Int = differ.currentList.size


    override fun getItemViewType(position: Int): Int = position


    override fun getItemId(position: Int): Long = position.toLong()


}
