package com.example.scrollsense.viewmodel

import android.util.Log
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

        // original
        repository.fetchItems(id, direction) { newItems, hasMore ->
            if(newItems.isEmpty()){
                _error.postValue(true)
            }else{
                if(direction == "up"){
                    _allItems.addAll(0, newItems)  // Prepend items
                }else{
                    _allItems.addAll(newItems)  // Append items
                }
                applyFilter(currentQuery)
                Log.d("ItemViewModel", "Updating LiveData with items: $newItems")
                _error.postValue(false)
            }
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