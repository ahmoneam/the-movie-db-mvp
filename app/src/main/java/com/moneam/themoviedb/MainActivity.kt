package com.moneam.themoviedb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneam.basemvp.model.Actor
import com.moneam.basemvp.model.Image
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var controller: MainController

    private lateinit var adapter: ActorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        controller = MainController(this, MainModel())

        adapter = ActorsAdapter({ url: String, id: Int ->
            controller.loadImage(url, id)
        }, {
            // todo open details activity
            // fixme test
            // hgjgjgjhgj
        })

        val linearLayoutManager = LinearLayoutManager(this)

        rv_actors.layoutManager = linearLayoutManager
        rv_actors.adapter = adapter

        rv_actors.addOnScrollListener(object :
            EndlessRecyclerViewScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int) {
                controller.loadNextPage()
            }
        })

        controller.onCreated()

    }

    fun addList(list: List<Actor>) {
        adapter.add(list)
    }

    fun updateImage(id: Int, image: Image) {
        adapter.updateImage(id, image)
    }
}


