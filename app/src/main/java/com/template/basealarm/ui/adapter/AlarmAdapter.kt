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
import kotlinx.coroutines.launch

class AlarmAdapter() : RecyclerView.Adapter<AlarmAdapter.Holder>() {
    private val differCallback = object : DiffUtil.ItemCallback<AlarmEntity>() {
        override fun areItemsTheSame(
            oldItem: AlarmEntity,
            newItem: AlarmEntity
        ): Boolean = oldItem == newItem

        override fun areContentsTheSame(
            oldItem: AlarmEntity,
            newItem: AlarmEntity
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
        fun bind(data: AlarmEntity) {
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
