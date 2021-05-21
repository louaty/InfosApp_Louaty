package com.example.infosapp_louaty

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.widget.Toast

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.infosapp_louaty.Adapter.ViewHolder.ListeSourceAdapter
import com.example.infosapp_louaty.Common.Common
import com.example.infosapp_louaty.Interface.InfosService
import com.example.infosapp_louaty.Model.WebSite
import com.example.infosapp_louaty.R
//import com.example.appnews_louaty.R.id.searchView
//import com.example.appnews_louaty.Common.Common
import com.google.gson.Gson
import dmax.dialog.SpotsDialog
import io.paperdb.Paper
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var layoutManager: LinearLayoutManager
    lateinit var mService: InfosService
    lateinit var adapter: ListeSourceAdapter
    lateinit var dialog: AlertDialog





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init cache
        Paper.init(this)
        mService = Common.infosService
        //init swipe
        swipe_to_refresh.setOnRefreshListener {
            loadWebSiteSource(true)
        }

        recycler_view_source_news.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recycler_view_source_news.layoutManager = layoutManager

        dialog = SpotsDialog(this)
        loadWebSiteSource(false)



    }

    //cache
    private fun loadWebSiteSource(isRefresh: Boolean) {
        if(!isRefresh)
        {
            val cache = Paper.book().read<String>("cache")
            if(cache != null && !cache.isBlank() && cache != "null"){
                val webSite = Gson().fromJson<WebSite>(cache,WebSite::class.java)
                adapter = ListeSourceAdapter(baseContext, webSite)
                adapter.notifyDataSetChanged()
                recycler_view_source_news.adapter = adapter


            }
            else{
                dialog.show()
                mService.sources.enqueue(object :retrofit2.Callback<WebSite>{
                    override fun onFailure(call: Call<WebSite>, t: Throwable) {
                        Toast.makeText(baseContext,"Check your conection",Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                        adapter = ListeSourceAdapter(baseContext,response.body()!!)
                        adapter.notifyDataSetChanged()
                        recycler_view_source_news.adapter = adapter

                        Paper.book().write("cache",Gson().toJson(response.body()!!))
                        dialog.dismiss()
                    }
                })

            }
        }
        else
        {
            swipe_to_refresh.isRefreshing=true
            mService.sources.enqueue(object :retrofit2.Callback<WebSite>{
                override fun onFailure(call: Call<WebSite>, t: Throwable) {
                    Toast.makeText(baseContext,"Failed",Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<WebSite>, response: Response<WebSite>) {
                    adapter = ListeSourceAdapter(baseContext,response.body()!!)
                    adapter.notifyDataSetChanged()
                    recycler_view_source_news.adapter = adapter

                    Paper.book().write("cache",Gson().toJson(response.body()!!))
                    swipe_to_refresh.isRefreshing=false
                }
            })

        }
    }

    /*  override fun onCreateOptionsMenu(menu: Menu?): Boolean {

          menuInflater.inflate(R.menu.menu, menu)
          val menuItem = menu!!.findItem(R.id.searchView)

          val searchView = menuItem.actionView as SearchView


         // searchView.maxWidth = Int.MAX_VALUE


          searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
              override fun onQueryTextSubmit(query: String?): Boolean {
                  return true
              }

              override fun onQueryTextChange(newText: String?): Boolean {
                  return false
              }

          })

          return true
      }*/
}