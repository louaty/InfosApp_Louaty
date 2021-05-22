package com.example.infosapp_louaty

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infosapp_louaty.Adapter.ViewHolder.ListeInfosAdapter
import com.example.infosapp_louaty.Common.Common
import com.example.infosapp_louaty.Interface.InfosService
import com.example.infosapp_louaty.Model.Infos
import com.squareup.picasso.Picasso
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.list_news.*


//import kotlinx.android.synthetic.main.activity_main.swipe_to_refresh
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListeInfos : AppCompatActivity() {

    var source=""
    var webHotUrl:String?=""

    lateinit var dialog: AlertDialog
    lateinit var mService:InfosService
    lateinit var adapter:ListeInfosAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_news)


        mService = Common.infosService
        dialog = SpotsDialog(this)

        swipe_to_refresh.setOnRefreshListener { loadNews(source,true) }
        diagonalLayout.setOnClickListener{
            //////////
            val detaille = Intent(baseContext, InfosDetailles::class.java)
            detaille.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            detaille.putExtra("webURL",webHotUrl)
            startActivity(detaille)

        }

        list_news.setHasFixedSize(true)
        list_news.layoutManager = LinearLayoutManager(this)


        if(intent != null){
            source = intent.getStringExtra("source")!!
            if(!source.isEmpty())
                loadNews(source,false)
        }
    }

    private fun loadNews(source: String?, isRefreshed: Boolean) {
        if(isRefreshed){
            dialog.show()
            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<Infos>{
                    override fun onFailure(call: Call<Infos>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call<Infos>?, response: Response<Infos>?) {
                        dialog.dismiss()
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(top_image)
                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem!!.removeAt(0)
                        adapter = ListeInfosAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })
        }
        else
        {
            swipe_to_refresh.isRefreshing = true

            mService.getNewsFromSource(Common.getNewsAPI(source!!))
                .enqueue(object : Callback<Infos>{
                    override fun onFailure(call: Call<Infos>, t: Throwable) {
                        //
                    }

                    override fun onResponse(call: Call<Infos>?, response: Response<Infos>?) {
                        swipe_to_refresh.isRefreshing = false
                        Picasso.with(baseContext)
                            .load(response!!.body()!!.articles!![0].urlToImage)
                            .into(top_image)
                        top_title.text = response.body()!!.articles!![0].title
                        top_author.text = response.body()!!.articles!![0].author

                        webHotUrl = response.body()!!.articles!![0].url

                        val removeFirstItem = response.body()!!.articles
                        removeFirstItem!!.removeAt(0)
                        adapter = ListeInfosAdapter(removeFirstItem,baseContext)
                        adapter.notifyDataSetChanged()
                        list_news.adapter = adapter
                    }

                })

        }
    }
}