package com.example.rainbowlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Collections

class MyAdapter(val list: MutableList<Cor>) : RecyclerView.Adapter<MyAdapter.MyHolder>() {

    var onItemClick: OnItemClickRecyclerView? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.MyHolder {
        var view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list, parent, false
        )
        return MyHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.MyHolder, position: Int) {
        val color = this.list.get(position)
        holder.tvNome?.text = color.getName()


    }



    override fun getItemCount(): Int = this.list.size

    fun add(cor: Cor){
        this.list.add(cor)
        this.notifyDataSetChanged()
    }

    fun del(position: Int){
        this.list.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.list.size)
        this.notifyDataSetChanged()
    }

    fun mov(from: Int, to: Int){
        Collections.swap(this.list, from, to)
        notifyItemMoved(from, to)
    }

    inner class MyHolder(item: View): RecyclerView.ViewHolder(item){
        var tvNome: TextView?
        var color_circle: ImageView?

        init {
            this.tvNome = item.findViewById(R.id.tvNome)
            this.color_circle = item.findViewById(R.id.color_circle)
            itemView.setOnClickListener{
                this@MyAdapter.onItemClick?.onItemClick(this.adapterPosition)
            }

            itemView.setOnClickListener{
                this@MyAdapter.onItemClick?.onItemClick(this.adapterPosition)
            }
        }
    }

}