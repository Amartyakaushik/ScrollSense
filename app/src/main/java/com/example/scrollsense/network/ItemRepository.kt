package com.example.scrollsense.network
import com.example.scrollsense.model.dataClass.Item


class ItemRepository(private val api: MockApi) {
    fun fetchItems(id: Int, direction: String, callback: (List<Item>, Boolean) -> Unit) {
        api.fetchItems(id, direction, callback)
    }
}

//package com.example.scrollsense.network
//
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import com.example.scrollsense.model.dataClass.Item
//
//class ItemRepository(private val api: MockApi) {
//
//    fun fetchItems(id: Int, direction: String): LiveData<List<Item>> {
//        val liveData = MutableLiveData<List<Item>>()
//
//        // Use the callback to post results to LiveData
//        api.fetchItems(id, direction) { items, _ ->
//            liveData.postValue(items)
//        }
//
//        return liveData
//    }
//}
