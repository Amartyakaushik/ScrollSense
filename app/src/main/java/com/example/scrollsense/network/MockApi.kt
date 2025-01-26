package com.example.scrollsense.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.scrollsense.model.dataClass.Item

class MockApi {

    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        val delayTime = (1000..1700).random().toLong()

        Handler(Looper.getMainLooper()).postDelayed({
            val items = generateMockData(id, direction)
            val hasMore = if (direction == "up") id > 0 else id < 2000

            Log.d("MockApi", "Generated items: $items")
            callback(items, hasMore)
        }, delayTime)
    }
    private fun generateMockData(id: Int, direction: String): List<Item> {
        val range = if (direction == "up") {
            (id-20 until id)
        } else {
            (id+1..id+20)
        }
        Log.d("mockapi", "Generating items in ${direction}ward for range: $range")
        return range.map { Item(it, "Item $it") }.filter { it.id > 0 && it.id <= 2000 }
    }
}