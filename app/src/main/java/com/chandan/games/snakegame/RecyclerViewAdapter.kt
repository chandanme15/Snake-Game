package com.chandan.games.snakegame

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter(private val nodeList : List<Node>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return NodeItemViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item_node, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as NodeItemViewHolder).bind(position)
    }

    override fun getItemCount() = nodeList.size

    inner class NodeItemViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val viewOn = itemView.findViewById<View>(R.id.view_on)
        private val viewOFF = itemView.findViewById<View>(R.id.view_off)

        fun bind(position: Int) {
            val isOn =  nodeList[position].isON
            viewOn.visibility = if(isOn) View.VISIBLE else View.GONE
            viewOFF.visibility = if(isOn) View.GONE else View.VISIBLE
        }
    }
}