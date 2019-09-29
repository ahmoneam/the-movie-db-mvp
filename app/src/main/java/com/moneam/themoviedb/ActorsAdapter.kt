package com.moneam.themoviedb

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneam.basemvp.model.Actor
import com.moneam.basemvp.model.Image
import com.moneam.themoviedb.MainModel.Companion.PROFILE_IMAGE_URL
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_actor.view.*

class ActorsAdapter(
    val getImage: (url: String, id: Int) -> Unit,
    val onCellClick: (actor: Actor) -> Unit
) :
    RecyclerView.Adapter<ActorsAdapter.ActorViewHolder>() {

    private val list = arrayListOf<Actor>()

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actor, null)
        return ActorViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun add(list: List<Actor>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }

    fun updateImage(id: Int, image: Image) {
        list.firstOrNull {
            it.id == id
        }?.image = image
        notifyDataSetChanged()
    }

    inner class ActorViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(actor: Actor) {
            with(view) {
                tv_name.text = actor.name
                tv_popularity.text = actor.popularity.toString()

                Picasso.get().load(PROFILE_IMAGE_URL + actor.profilePath)
                    .into(iv_actor)

//                if (actor.image?.bitmap != null) {
//                    iv_actor.setImageBitmap(actor.image?.bitmap)
//                } else {
//                    getImage(actor.profilePath, actor.id)
//                }

                view.setOnClickListener {
                    onCellClick(actor)
                }
            }
        }
    }
}