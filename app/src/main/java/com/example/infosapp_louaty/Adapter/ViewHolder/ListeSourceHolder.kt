package com.example.infosapp_louaty.Adapter.ViewHolder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.infosapp_louaty.Interface.ItemClickListener
import kotlinx.android.synthetic.main.source_news_layout.view.*



class ListeSourceHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {


    private lateinit var itemClickListener:ItemClickListener
    var source_titre = itemView.source_news_name
    init{
        itemView.setOnClickListener(this)
    }
    //var source_title = itemView.findViewById<EditText>(R.id.source_news_name)


    fun setItemClickListener(itemClickListener: ItemClickListener){
        this.itemClickListener = itemClickListener
    }

    override fun onClick(v: View?) {
        itemClickListener.onClick(v!!, adapterPosition)

    }


}