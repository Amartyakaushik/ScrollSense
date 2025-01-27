package com.example.scrollsense.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.scrollsense.model.dataClass.Item
import com.example.scrollsense.network.ItemRepository

// ViewModel class for managing the items and their filtered state
class ItemViewModel(private val repository: ItemRepository) : ViewModel() {

    // Mutable list to hold all items, initially empty
    private val _allItems = mutableListOf<Item>()

    // LiveData to observe the filtered list of items
    private val _filteredItems = MutableLiveData<List<Item>>()
    val filteredItems: LiveData<List<Item>> get() = _filteredItems

    // LiveData to observe error state
    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> get() = _error

    // Flag to track loading state
    var isLoading = false

    // To keep track of the current filter query
    private var currentQuery: String = ""

    // Function to load items from repository, with pagination (up or down direction)
    fun loadItems(id: Int, direction: String) {
        // Prevent multiple calls while already loading
        if (isLoading) return
        isLoading = true

        Log.d("ivm", "loadItems: direction: $direction, id= $id")

        // Fetch items from the repository, passing a callback to handle the response
        repository.fetchItems(id, direction) { newItems, hasMore ->
            // If no items are fetched, show an error
            if(newItems.isEmpty()){
                _error.postValue(true)
                Log.d("ivm", "no items fetched")
            } else {
                // Handle appending or prepending items based on the direction
                if (direction == "up") {
                    _allItems.clear()
                    _allItems.addAll(0, newItems)  // Prepend items to the list
                    Log.d("ivm", "prependitems = $newItems")
                } else {
                    _allItems.clear()
                    _allItems.addAll(newItems)  // Append items to the list
                    Log.d("ivm", "append = $newItems")
                }
                // Apply the current filter to newly loaded items
                applyFilter(currentQuery)
                Log.d("ItemViewModel", "Updating LiveData with items: $newItems")
                _error.postValue(false)
            }
            isLoading = false  // Set loading to false after data is fetched
        }
    }

    // Function to filter items based on a query string
    fun filterItems(query: String) {
        currentQuery = query
        applyFilter(query)  // Apply the filter immediately
    }

    // Helper function to apply the current filter to the list of items
    private fun applyFilter(query: String) {
        // Filter the items based on the title matching the query (case-insensitive)
        val filteredList = if (query.isEmpty()) {
            _allItems  // If the query is empty, show all items
        } else {
            _allItems.filter { it.title.contains(query, ignoreCase = true) }
        }
        // Post the filtered list to the LiveData
        _filteredItems.postValue(filteredList)
    }
}
