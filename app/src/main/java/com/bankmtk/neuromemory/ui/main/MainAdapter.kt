package com.bankmtk.neuromemory.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.getColorInt
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sticker.*

class MainAdapter(private val onItemClickListener: OnItemClickListener)
    :RecyclerView.Adapter<MainAdapter.StickViewHolder>() {
    var stickers: List<Sticker> = listOf()
    set(value){
        field = value.sortedWith(compareBy {it.progressSt})
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
        RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bind(sticker: Sticker){
            titleStick.text = sticker.title
            langOneI.text = sticker.langOne
            langTwoI.text = sticker.langTwo
            status_star.setImageLevel(sticker.progressSt)
            itemView.setBackgroundColor(sticker.color.getColorInt(itemView.context))
            itemView.setOnClickListener{onItemClickListener.onItemClick(itemView)}
            itemView.setOnLongClickListener{consume { onItemClickListener.onItemLongClick(sticker) }}
        }
        private inline fun consume(function:()->Unit):Boolean{
            function()
            return true
        }
    }
    interface OnItemClickListener{
        fun onItemClick(itemView: View)
        fun onItemLongClick(sticker: Sticker)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

}


