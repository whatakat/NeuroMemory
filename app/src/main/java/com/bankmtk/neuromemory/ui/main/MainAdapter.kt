package com.bankmtk.neuromemory.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker

class MainAdapter(private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<MainAdapter.StickViewHolder>() {
    var stickers: List<Sticker> = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vew  = inflater.inflate(R.layout.item_sticker,parent,false)
        return StickViewHolder(vew)
    }

    override fun getItemCount() = stickers.size

    override fun onBindViewHolder(holder: StickViewHolder, position: Int): Unit{
        holder.bind(stickers[position])
    }
    inner class StickViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val title = itemView.findViewById<TextView>(R.id.titleEt)
        private val langOne = itemView.findViewById<TextView>(R.id.langOne)
        private val langTwo = itemView.findViewById<TextView>(R.id.langTwo)

        fun bind(sticker: Sticker){
            title.text = sticker.title
            langOne.text = sticker.langOne
            langTwo.text = sticker.langTwo
            val color = when(sticker.color){
            Color.WHITE ->R.color.white
                Color.VIOLET ->R.color.violet
                Color.BLUE ->R.color.blue
                Color.GREEN ->R.color.green
                Color.PINK ->R.color.pink
                Color.RED -> R.color.red
                Color.YELLOW -> R.color.yellow
            }
            itemView.setBackgroundColor(itemView.context.resources.getColor(color))
            itemView.setOnClickListener{onItemClickListener.onItemClick(sticker)}
        }
    }
    interface OnItemClickListener{
        fun onItemClick(sticker: Sticker)
    }
}


