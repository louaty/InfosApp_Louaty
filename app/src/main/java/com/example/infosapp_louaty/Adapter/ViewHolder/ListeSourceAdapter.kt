package com.example.infosapp_louaty.Adapter.ViewHolder

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infosapp_louaty.Interface.ItemClickListener
import com.example.infosapp_louaty.ListeInfos

import com.example.infosapp_louaty.Model.WebSite
import com.example.infosapp_louaty.R


class ListeSourceAdapter(private val context:Context, private val webSite: WebSite):RecyclerView.Adapter<ListeSourceHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeSourceHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.source_news_layout, parent, false)
        return ListeSourceHolder(itemView)
    }

    override fun getItemCount(): Int {

        return webSite.sources!!.size

    }


    //fun String.toEditable(): Editable =  Editable.Factory.getInstance().newEditable(this)
    override fun onBindViewHolder(holder: ListeSourceHolder, position: Int) {
        // val item = webSite.sources!![position].name
        //holder.itemView.bind(item) //
        holder.source_titre.text = webSite.sources!![position].name

        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(context, ListeInfos::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.putExtra("source", webSite.sources!![position].id)

                context.startActivity(intent)


            }


        })
    }
    //protected open fun View.bind(source_title: String) {}

}






