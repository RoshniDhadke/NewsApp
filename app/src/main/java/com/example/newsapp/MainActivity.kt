package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest


class MainActivity : AppCompatActivity(), MyAdapter.itemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var myadapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        myadapter = MyAdapter(this)
        recyclerView.adapter = myadapter

    }

    private fun fetchData() {
        val url=" https://saurav.tech/NewsAPI/top-headlines/category/health/in.json"
        val news = ArrayList<News>()
        val jsonRequest =  JsonObjectRequest(Request.Method.GET, url, null, Response.Listener { response ->
                val jsonArray = response.getJSONArray("articles")
            for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val new = News(
                        jsonObject.getString("title"),
                        jsonObject.getString("author"),
                        jsonObject.getString("url"),
                        jsonObject.getString("urlToImage")
                    )
                    news.add(new)
                }
                myadapter.updateNews(news)
            }, Response.ErrorListener { error ->
             Toast.makeText(applicationContext,"error",Toast.LENGTH_SHORT).show()
            })
        MySingelton.getInstance(this).addToRequestQueue(jsonRequest)
    }

    override fun onItemClick(item: News) {
        val builder=CustomTabsIntent.Builder()
        val customTabsIntent=builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }}