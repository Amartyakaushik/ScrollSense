package com.example.scrollsense.viewmodel
//
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

    var isLoading = false
//    private var isLoading = false
    private var currentQuery: String = ""

    fun loadItems(id: Int, direction: String) {
        if (isLoading) return
        isLoading = true

        Log.d("ivm", "loadItems: direction: $direction, id= $id")
        // original
        repository.fetchItems(id, direction) { newItems, hasMore ->
            if(newItems.isEmpty()){
                _error.postValue(true)
                Log.d("ivm", "no items fetched")
            }else{
                if(direction == "up"){
//                    _allItems.addAll(0, newItems)  // Prepend items
//                    _allItems.addAll(newItems)  // Prepend items
                    _allItems.addAll(0,newItems)
                    Log.d("ivm", "prependitems = $newItems")
                }else{
                    _allItems.addAll(newItems)  // Append items
                    Log.d("ivm", "append = $newItems")
                }
                applyFilter(currentQuery)
                Log.d("ItemViewModel", "Updating LiveData with items: $newItems")
                _error.postValue(false)
            }
            isLoading = false
        }

//        // to encounter Up Scroll issue
//        repository.fetchItems(id, direction).observeForever { newItems ->
//            if (newItems.isNotEmpty()) {
//                if (direction == "up") {
//                    _allItems.value = (_allItems.value ?: listOf()).toMutableList().apply {
//                        addAll(0, newItems) // Prepend items
//                    }
//                } else {
//                    _allItems.value = (_allItems.value ?: listOf()).toMutableList().apply {
//                        addAll(newItems) // Append items
//                    }
//                }
//            }
//            isLoading = false
//        }
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
//
////package com.example.scrollsense.viewmodel
//
////import android.util.Log
////import androidx.lifecycle.LiveData
////import androidx.lifecycle.MutableLiveData
////import androidx.lifecycle.ViewModel
////import com.example.scrollsense.model.Item
////import com.example.scrollsense.network.ItemRepository
//
//class ItemViewModel(private val repository: ItemRepository) : ViewModel() {
//
//    private val _allItems = MutableLiveData<List<Item>>(emptyList()) // Observable list
//    val allItems: LiveData<List<Item>> get() = _allItems
//
//    private val _filteredItems = MutableLiveData<List<Item>>()
//    val filteredItems: LiveData<List<Item>> get() = _filteredItems
//
//    private val _error = MutableLiveData<Boolean>()
//    val error: LiveData<Boolean> get() = _error
//
//    var isLoading = false
//    private var currentQuery: String = ""
//
//    fun loadItems(id: Int, direction: String) {
//        if (isLoading) return
//        isLoading = true
//
//        Log.d("ItemViewModel", "loadItems: direction=$direction, id=$id")
//
//        // Fetch items from the repository
//        repository.fetchItems(id, direction).observeForever { newItems ->
//            if (newItems.isNullOrEmpty()) {
//                _error.postValue(true)
//                Log.d("ItemViewModel", "No items fetched")
//            } else {
//                val currentList = _allItems.value.orEmpty().toMutableList()
//                if (direction == "up") {
//                    currentList.addAll(0, newItems) // Prepend items
//                    Log.d("ItemViewModel", "Prepended items: $newItems")
//                } else {
//                    currentList.addAll(newItems) // Append items
//                    Log.d("ItemViewModel", "Appended items: $newItems")
//                }
//                _allItems.postValue(currentList)
//                applyFilter(currentQuery) // Apply the current filter to update filteredItems
//                _error.postValue(false)
//            }
//            isLoading = false
//        }
//    }
//
//    fun filterItems(query: String) {
//        currentQuery = query
//        applyFilter(query)
//    }
//
//    private fun applyFilter(query: String) {
//        val filteredList = if (query.isEmpty()) {
//            _allItems.value.orEmpty()
//        } else {
//            _allItems.value.orEmpty().filter { it.title.contains(query, ignoreCase = true) }
//        }
//        _filteredItems.postValue(filteredList)
//    }
//}
