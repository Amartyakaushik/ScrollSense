package com.example.scrollsense.network
import com.example.scrollsense.model.Item


class ItemRepository(private val api: MockApi) {
    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        api.fetchItems(id, direction, callback)
    }
}