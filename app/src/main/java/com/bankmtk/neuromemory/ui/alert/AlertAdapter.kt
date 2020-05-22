package com.bankmtk.neuromemory.ui.alert

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.getColorInt
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sticker.*

class AlertAdapter(private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<AlertAdapter.StickViewHolder>() {
    var stickers: List<Sticker> = listOf()
        set(value){
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.item_sticker,parent,false)
        return StickViewHolder(view)
    }

    override fun getItemCount() = stickers.size


    override fun onBindViewHolder(holder: StickViewHolder, position: Int){
        holder.bind(stickers[position])
    }
    inner class StickViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(sticker: Sticker){
            titleStick.text = sticker.title
            langOneI.text = sticker.langOne
            langTwoI.text = sticker.langTwo
            itemView.setBackgroundColor(sticker.color.getColorInt(itemView.context))
            itemView.setOnClickListener{onItemClickListener.onItemClick(itemView)}
        }
    }
    interface OnItemClickListener{
        fun onItemClick(itemView: View)
    }

}