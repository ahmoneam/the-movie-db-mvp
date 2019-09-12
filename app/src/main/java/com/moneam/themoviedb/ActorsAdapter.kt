package com.moneam.themoviedb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    private val list = arrayListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actor, null)
        return ActorViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class ActorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Actor) {

        }

    }
}