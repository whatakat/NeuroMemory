package com.bankmtk.neuromemory.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Color
import com.bankmtk.neuromemory.data.model.Sticker

class MainAdapter(private val onItemClickListener: OnItemClickListener):RecyclerView.Adapter<StickerViewHolder>() {
    var stickers: List<Sticker> = listOf()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val vew  = inflater.inflate(R.layout.item_sticker,parent,false)
        return StickerViewHolder(vew)
    }

    override fun getItemCount() = stickers.size

    override fun onBindViewHolder(holder: StickerViewHolder, position: Int): Unit{
        holder.bind(stickers[position])
    }
}
class StickerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    private val langOne = itemView.findViewById<TextView>(R.id.langOne)
    private val langTwo = itemView.findViewById<TextView>(R.id.langTwo)

    fun bind(sticker: Sticker){
        langOne.text = sticker.langOne
        langTwo.text = sticker.langTwo

        val color = when(sticker.color){
            Color.YELLOW->R.color.yellow
            Color.RED->R.color.red
            Color.PINK->R.color.pink
            Color.GREEN->R.color.green
            Color.BLUE->R.color.blue
            Color.VIOLET->R.color.violet
            Color.WHITE->R.color.white
        }
        itemView.setBackgroundColor(itemView.context.resources.getColor(color))

    }
}
interface OnItemClickListener{
    fun onItemClick(sticker: Sticker)
}