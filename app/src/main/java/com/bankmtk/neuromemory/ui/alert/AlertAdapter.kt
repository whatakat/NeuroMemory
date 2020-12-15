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
import kotlinx.android.synthetic.main.item_sticker.view.*
import java.util.*

class AlertAdapter(private val onItemClickListener: OnItemClickListener)
    : RecyclerView.Adapter<AlertAdapter.StickViewHolder>() {
    var stickers: List<Sticker> = listOf()
       set(value){
    field = value.filter { date->date.lastChanged<Date() }
    notifyDataSetChanged()
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StickViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view  = inflater.inflate(R.layout.item_sticker,parent,false)
        return StickViewHolder(view)
    }
   override fun getItemCount()=stickers.size

    override fun onBindViewHolder(holder: StickViewHolder, position: Int){
            holder.bind(stickers[position])
            //Handler().postDelayed({setAnimation(holder.containerView)},position.toLong())
    }
//    private fun setAnimation(viewToAnimate: View,){
//        val animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.sticker_alert_zoom_in)
//        viewToAnimate.startAnimation(animation)
//        val drawableRing: Drawable? =  viewToAnimate.ring!!.drawable
//        if (drawableRing is Animatable){
//            drawableRing.start()
//        }
//    }

    inner class StickViewHolder(override val containerView: View):
        RecyclerView.ViewHolder(containerView), LayoutContainer {
        fun bind(sticker: Sticker){
                titleStick.text = sticker.title
                langOneI.text = sticker.langOne
                langTwoI.text = sticker.langTwo
                status_star.setImageLevel(sticker.progressSt)
                itemView.titleStick.setBackgroundColor(sticker.color.getColorInt(itemView.context))
                itemView.setOnClickListener{onItemClickListener.onItemClick(itemView)}
                itemView.fabOk.setOnClickListener {consume { onItemClickListener.onItemOkClick(sticker) }  }
                //itemView.fabVolume.setOnClickListener {consume { onItemClickListener.onItemSpeakClick(sticker) }  }
        }
        private inline fun consume(function:()->Unit):Boolean{
            function()
            return true
        }
    }

    interface OnItemClickListener{
        fun onItemClick(itemView: View)
        fun onItemOkClick(sticker: Sticker)
        //fun onItemSpeakClick(sticker: Sticker)

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }
}
