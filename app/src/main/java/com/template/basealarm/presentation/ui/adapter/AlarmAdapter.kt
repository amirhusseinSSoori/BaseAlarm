package com.template.basealarm.presentation.ui.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.template.basealarm.R
import com.template.basealarm.databinding.ItemCustomLayoutBinding
import com.template.basealarm.domain.entity.Alarm

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
        ,
            parent.context
        )
    }


    inner class Holder(val bind: ItemCustomLayoutBinding,val context:Context) : RecyclerView.ViewHolder(bind.root) {
        fun bind(data: Alarm) {
            bind.itemTxtDate.text = data.date
            bind.itemTxtTime.text = data.time


            if (data.alarmed!!) {
                bind.relativeContainer.setBackgroundResource(R.drawable.bg_img_alarm_disable)

                bind.itemTxtDate.setTextColor(ContextCompat.getColor(context, R.color.white))
                bind.itemTxtTime.setTextColor(ContextCompat.getColor(context, R.color.white))



            }
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
