package com.example.scrollsense.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.scrollsense.model.Item

class MockApi {

    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        val delayTime = (1000..2000).random().toLong()

        Handler(Looper.getMainLooper()).postDelayed({
            val items = generateMockData(id, direction)
            val hasMore = if (direction == "up") id > 0 else id < 2000

            Log.d("MockApi", "Generated items: $items")
            callback(items, hasMore)
        }, delayTime)
    }
// original
//    private fun generateMockData(id: Int, direction: String): List<Item> {
//        return if (direction == "up") {
//            (id-10 until id).map { Item(it, "Item $it") }.filter { it.id >= 0 }
//        } else {
//            (id+1..id+10).map { Item(it, "Item $it") }.filter { it.id <= 2000 }
//        }
//    }

//    // temp generating data
//    private fun generateMockData(id: Int, direction: String): List<Item> {
//        return if (direction == "up") {
//            (id-10 until id).map { Item(it, "Item $it") }.filter { it.id >= 0 }
//        } else {
//            (id+1..id+20).map { Item(it, "Item $it") }.filter { it.id <= 2000 }
//        }
//    }

    // temp next encounter while scrolling up only specified number of data should be shown
    private fun generateMockData(id: Int, direction: String): List<Item> {
        return if (direction == "up") {
            (id-20 until id).map { Item(it, "Item $it") }.filter { it.id >= 0 }
        } else {
            (id+1..id+20).map { Item(it, "Item $it") }.filter { it.id <= 2000 }
        }
    }
}