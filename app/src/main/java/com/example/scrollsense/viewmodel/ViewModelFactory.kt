package com.example.scrollsense.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.scrollsense.network.ItemRepository

// Custom ViewModelFactory to instantiate ViewModels with a repository
class ViewModelFactory(private val repository: ItemRepository) : ViewModelProvider.Factory {

    // Method to create ViewModel instances, with the provided repository
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the requested ViewModel is of type ItemViewModel
        if (modelClass.isAssignableFrom(ItemViewModel::class.java)) {
            // Return an instance of ItemViewModel with the repository
            return ItemViewModel(repository) as T
        }
        // Throw an exception if the ViewModel class is not recognized
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
