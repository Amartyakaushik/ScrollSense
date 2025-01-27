package com.example.scrollsense.network

import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.scrollsense.model.dataClass.Item

/**
 * Mock API class that simulates fetching data asynchronously.
 * This class generates mock data and introduces artificial delays to mimic network requests.
 */
class MockApi {
    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        // Introduces a random delay between 1000ms and 1700ms to simulate network latency
        val delayTime = (1000..1700).random().toLong()

        Handler(Looper.getMainLooper()).postDelayed({
            // Generate mock data based on the ID and direction
            val items = generateMockData(id, direction)

            // Determine if there are more items to fetch based on the direction
            val hasMore = if (direction == "up") id > 0 else id < 2000

            // Log the generated items for debugging purposes
            Log.d("MockApi", "Generated items: $items")

            // Invoke the callback with the items and hasMore flag
            callback(items, hasMore)
        }, delayTime)
    }

    private fun generateMockData(id: Int, direction: String): List<Item> {
        // Define the range of items to generate based on the direction
        val range = if (direction == "up") {
            (id - 20 until id) // Generates 20 items before the current ID
        } else {
            (id + 1..id + 20) // Generates 20 items after the current ID
        }

        // Log the range for debugging purposes
        Log.d("MockApi", "Generating items in ${direction}ward for range: $range")

        // Generate a list of Items and filter the valid range (ID > 0 and ID <= 2000)
        return range.map { Item(it, "Item $it") }.filter { it.id > 0 && it.id <= 2000 }
    }
}
