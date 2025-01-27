package com.example.scrollsense.network

import com.example.scrollsense.model.dataClass.Item

/**
 * A repository class responsible for managing data operations
 * and acting as a bridge between the MockApi and the rest of the app.
 *
 * @property api An instance of MockApi used to fetch items from the data source.
 */
class ItemRepository(private val api: MockApi) {
    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        // Calls the fetchItems method in MockApi, passing the parameters and callback
        api.fetchItems(id, direction, callback)
    }
}
