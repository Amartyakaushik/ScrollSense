package com.example.scrollsense.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrollsense.model.Item
import com.example.scrollsense.network.ItemRepository

class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    private val _allItems = mutableListOf<Item>()
    private val _filteredItems = MutableLiveData<List<Item>>()
    val filteredItems: LiveData<List<Item>> get() = _filteredItems

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    private var isLoading = false
    private var currentQuery: String = ""

    fun loadItems(id: Int, direction: String) {
        if (isLoading) return
        isLoading = true

        repository.fetchItems(id, direction) { newItems, hasMore ->
            _allItems.addAll(newItems)
            applyFilter(currentQuery)
            _error.postValue(false)
            isLoading = false
        }
    }

    fun filterItems(query: String) {
        currentQuery = query
        applyFilter(query)
    }

    private fun applyFilter(query: String) {
        val filteredList = if (query.isEmpty()) {
            _allItems
        } else {
            _allItems.filter { it.title.contains(query, ignoreCase = true) }
        }
        _filteredItems.postValue(filteredList)
    }
}