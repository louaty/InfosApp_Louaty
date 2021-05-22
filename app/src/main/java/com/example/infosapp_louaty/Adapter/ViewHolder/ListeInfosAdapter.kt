package com.example.infosapp_louaty.Adapter.ViewHolder

import android.content.Context
//import kotlinx.android.synthetic.main.activity_add_sales.*
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.infosapp_louaty.InfosDetailles

import com.example.infosapp_louaty.Model.Article

import com.example.infosapp_louaty.Interface.ItemClickListener
import com.example.infosapp_louaty.Common.ISO8601ParseInfos
import com.example.infosapp_louaty.R


import com.squareup.picasso.Picasso
import java.text.ParseException
import java.util.*

class ListeInfosAdapter(val articleList:MutableList<Article>, private val context: Context):RecyclerView.Adapter<ListeInfosHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeInfosHolder {
        val inflater = LayoutInflater.from(parent!!.context)
        val itemView = inflater.inflate(R.layout.infos_layout,parent,false)
        return ListeInfosHolder(itemView)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ListeInfosHolder, position: Int) {
        Picasso.with(context)
            .load(articleList[position].urlToImage)
            .into(holder.article_image)
        if(articleList[position].title!!.length > 65){
            holder.article_titre.text = articleList[position].title!!.substring(0,65)+"..."
        }
        else{
            holder.article_titre.text = articleList[position].title!!

        }

        if(articleList[position].publishedAt != null){
            // var date = findViewById<View>(R.id.date) as? EditText
            var date: Date? = null
            try{
                date = ISO8601ParseInfos.parse(articleList[position].publishedAt!!)
            }catch (e:ParseException)
            {
                e.printStackTrace()
            }

//            holder.article_time.setReferenceTime(date!!.time)
        }

        //holder.itemView.setOnClickListener {
        holder.setItemClickListener(object : ItemClickListener {
            override fun onClick(view: View, position:Int){
                /////
                val detail = Intent(context, InfosDetailles::class.java)

                detail.putExtra("webURL",articleList[position].url)
                detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)

                // detail.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(detail)

            }
        })
    }

}