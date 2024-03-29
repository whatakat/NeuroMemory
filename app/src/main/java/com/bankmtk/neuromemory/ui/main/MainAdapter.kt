package com.bankmtk.neuromemory.ui.main

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bankmtk.neuromemory.R
import com.bankmtk.neuromemory.data.model.Sticker
import com.bankmtk.neuromemory.extentions.getColorInt
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_sticker.*
import kotlinx.android.synthetic.main.item_sticker.view.*

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
           Handler().postDelayed({setAnimation(holder.containerView)},position.toLong())
    }
    private fun setAnimation(viewToAnimate: View){
//            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.sticker_zoom_in)
//            viewToAnimate.startAnimation(animation)
        val drawableRing: Drawable? =  viewToAnimate.ring!!.drawable
        if (drawableRing is Animatable){
            drawableRing.start()
        }
    }
    inner class StickViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer{
        fun bind(sticker: Sticker){
            titleStick.text = sticker.title
            langOneI.text = sticker.langOne
            langTwoI.text = sticker.langTwo
            status_star.setImageLevel(sticker.progressSt)
            itemView.titleStick.setBackgroundColor(sticker.color.getColorInt(itemView.context))
            itemView.setOnClickListener{onItemClickListener.onItemClick(itemView)}
            itemView.setOnLongClickListener{consume { onItemClickListener.onItemLongClick(sticker) }}
            itemView.fabVolume.setOnClickListener {consume { onItemClickListener.onItemSpeakClick(itemView) }  }
        }
        private inline fun consume(function:()->Unit):Boolean{
            function()
            return true
        }
    }
    interface OnItemClickListener{
        fun onItemClick(itemView: View)
        fun onItemLongClick(sticker: Sticker)
        fun onItemSpeakClick(view: View)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


}


