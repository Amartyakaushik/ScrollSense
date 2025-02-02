package com.example.scrollsense.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollsense.R
import com.example.scrollsense.databinding.ActivityMainBinding
import com.example.scrollsense.network.ItemRepository
import com.example.scrollsense.network.MockApi
import com.example.scrollsense.ui.adapter.ItemAdapter
import com.example.scrollsense.viewmodel.ItemViewModel
import com.example.scrollsense.viewmodel.ViewModelFactory
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    // ViewBinding for accessing views directly
    private lateinit var binding: ActivityMainBinding

    // ViewModel for managing UI-related data
    private lateinit var viewModel: ItemViewModel

    // Adapter for populating RecyclerView
    private lateinit var adapter: ItemAdapter

    // Shimmer effect layout to indicate loading
    private lateinit var shimmerLayout: ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize ShimmerFrameLayout
        shimmerLayout = findViewById(R.id.shimmerLayout)

        // Set up ViewModel with a factory for dependency injection
        viewModel = ViewModelProvider(this, ViewModelFactory(ItemRepository(MockApi()))).get(ItemViewModel::class.java)

        // Initialize RecyclerView adapter with an empty query
        adapter = ItemAdapter("")

        // Load initial data (page 0, downward direction)
        viewModel.loadItems(0, "down")

        // Set up RecyclerView with LinearLayoutManager and attach the adapter
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Set up search functionality
        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.queryHint = "Search Items..."
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false // No action required on query submit
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
                adapter.updateQuery(query) // Update query in the adapter
                viewModel.filterItems(query) // Filter items in ViewModel
                return true
            }
        })

        // Observe filtered items and update RecyclerView
        viewModel.filteredItems.observe(this) { items ->
            Log.d("MainActivity", "Items received: ${items.size}")
            val previousSize = adapter.items.size
            adapter.submitList(items) // Submit new items to the adapter

            // Stop shimmer animation and hide layout
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE

            // Notify adapter about new items for smooth transition
            if (items.size > previousSize) {
                adapter.notifyItemRangeInserted(0, items.size - previousSize)
            }
        }

        // Observe error state and display a message if an error occurs
        viewModel.error.observe(this) { error ->
            if (error) {
                binding.errorMessage.visibility = View.VISIBLE
                shimmerLayout.stopShimmer()
                shimmerLayout.visibility = View.GONE

                // Hide the error message after 3 seconds using coroutines
                lifecycleScope.launch {
                    delay(3000) // Wait for 3 seconds
                    binding.errorMessage.visibility = View.GONE
                }
            }
        }

        // Start shimmer effect while loading
        startLoading()

        // Enable pagination functionality
        setupPagination(recyclerView)
    }


     // Sets up pagination by listening to RecyclerView scroll events.

    private fun setupPagination(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                // Load more data when scrolling down and reaching the bottom
                if (dy > 0 && lastVisibleItem >= totalItemCount - 1) {
                    val lastId = adapter.items.lastOrNull()?.id ?: 0
                    Log.d("MainActivity", "Loading more items downward from ID: $lastId")
                    startLoading() // Show shimmer while loading
                    viewModel.loadItems(lastId, "down")

                    // Load more data when scrolling up and reaching the top
                } else if (dy < 0 && firstVisibleItem == 0) {
                    val firstId = adapter.items.firstOrNull()?.id ?: 0
                    if (firstId > 0) { // Ensure no invalid requests below ID 0
                        Log.d("MainActivity", "Loading more items upward from ID: $firstId")
                        startLoading() // Show shimmer while loading
                        viewModel.loadItems(firstId, "up")
                    }
                }
            }
        })
    }


     // Starts the shimmer effect to indicate loading state.
    private fun startLoading() {
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE
    }
}
