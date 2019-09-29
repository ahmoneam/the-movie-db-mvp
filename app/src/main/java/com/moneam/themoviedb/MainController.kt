package com.moneam.themoviedb

import com.moneam.basemvp.model.Image

class MainController(val view: MainActivity, val model: MainModel) {
    private var page: Int = 1

    fun onCreated() {
        model.getActors(page) {
            view.addList(it)
        }
    }

    fun loadImage(url: String, id: Int) {
        val image = Image(url.replaceFirst("/", ""), id, null)
        model.loadImage(image) {
            view.updateImage(it.id, it)
        }
    }

    fun loadNextPage() {
        page++
        model.getActors(page) {
            view.addList(it)
        }
    }
}