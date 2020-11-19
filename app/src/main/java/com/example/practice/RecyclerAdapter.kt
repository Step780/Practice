package com.example.practice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private var titles = arrayOf("Chapter One", "Chapter Two")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        holder.itemTitle.text = titles[position]

    }

    override fun getItemCount(): Int {
        return  titles.size
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {


        var itemTitle: TextView

        init {
            itemTitle = itemView.findViewById(R.id.item_title)



            itemView.setOnClickListener {

                val position: Int = adapterPosition
                Toast.makeText(itemView.context, "you clicked on ${titles[position]}", Toast.LENGTH_LONG).show()
            }

        }
    }
}