package com.moneam.themoviedb

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ActorsAdapter : RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    private val list = arrayListOf<Actor>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    inner class ActorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}