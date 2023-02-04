package com.example.newsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter(private val listener:itemClickListener): RecyclerView.Adapter<MyAdapter.MyViewHolder>(){
    private val item:ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_list,parent,false)
        val viewHolder=MyViewHolder(view)
        view.setOnClickListener {
            listener.onItemClick(item[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val a=item[position]
        holder.title.text=a.title
        holder.author.text=a.author
        Glide.with(holder.itemView.context).load(a.urlToImage).into(holder.imageview)

    }
    fun updateNews(updateNews:ArrayList<News>){
        item.clear()
        item.addAll(updateNews)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return item.size
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title:TextView=itemView.findViewById(R.id.title)
        val imageview:ImageView=itemView.findViewById(R.id.image)
        val author:TextView=itemView.findViewById(R.id.author)
    }
    interface itemClickListener{
        fun onItemClick(item:News)
    }
}