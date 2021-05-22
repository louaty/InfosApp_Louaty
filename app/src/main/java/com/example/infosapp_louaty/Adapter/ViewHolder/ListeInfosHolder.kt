package com.example.infosapp_louaty.Adapter.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.infosapp_louaty.Interface.ItemClickListener
import kotlinx.android.synthetic.main.infos_layout.view.*

class ListeInfosHolder (itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener{
    private lateinit var itemClickListener: ItemClickListener

    var article_titre = itemView.article_title
    var article_time  = itemView.article_time
    var article_image = itemView.article_image

    init{
        itemView.setOnClickListener(this)
    }
    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!,adapterPosition)
    }


}