package com.bankmtk.neuromemory.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker

class MainAdapter:RecyclerView.Adapter<StickerViewHolder>() {
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
    }
}