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

//import com.example.scrollsense.databinding.ShimmerLayoutBinding

//import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
//import androidx.lifecycle.Observer
//import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ItemViewModel
    private lateinit var adapter: ItemAdapter
    private lateinit var shimmerLayout : ShimmerFrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shimmerLayout = findViewById(R.id.shimmerLayout)
        viewModel = ViewModelProvider(this, ViewModelFactory(ItemRepository(MockApi())) ).get(ItemViewModel::class.java)
        adapter = ItemAdapter("")
//        viewModel = ViewModelProvider(this, ViewModelFactory(ItemRepository(MockApi()))).get(ItemViewModel::class.java)
//        adapter = ItemAdapter("")

        viewModel.loadItems(0, "down") // Ensure this is called to load initial data
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val searchView = findViewById<SearchView>(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener  {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val query = newText.orEmpty()
//                adapter = ItemAdapter(query)
//                recyclerView.adapter = adapter
                adapter.updateQuery(query)
                viewModel.filterItems(query)
                return true
            }
        })

        // ORIGINAL
//        viewModel.filteredItems.observe(this, Observer { items ->
//            Log.d("MainActivity", "Items received: ${items.size}")
//            adapter.submitList(items)
//        })

        // cross check
        viewModel.filteredItems.observe(this) { items ->
            Log.d("MainActivity", "Items received: ${items.size}")
            val previousSize = adapter.items.size
            adapter.submitList(items)
            shimmerLayout.stopShimmer()
            shimmerLayout.visibility = View.GONE
            if (items.size > previousSize) {
                adapter.notifyItemRangeInserted(0, items.size - previousSize)
            }
        }

//        viewModel.error.observe(this) { error ->
//            (binding.errorMessage).visibility = if (error) View.VISIBLE else View.GONE
//            shimmerLayout.stopShimmer()
//            shimmerLayout.visibility = View.GONE
//        }

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



//        // Start shimmer when loading data
//        shimmerLayout.startShimmer()
//        shimmerLayout.visibility = View.VISIBLE
        startLoading()
        setupPagination(recyclerView)
    }

    private fun setupPagination(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

//                if (dy > 0 && lastVisibleItem >= totalItemCount - 1) {
//                    val lastId = adapter.items.lastOrNull()?.id ?: 0
//                    Log.d("main", "direction down Loading down from1 ID: $lastId")
//                    startLoading() // shimmer effect till the data is loaded
//                    viewModel.loadItems(lastId,"down")
//
//                    // // Upward Scroll Condition
//                     }      else if (dy < 0 && firstVisibleItem == 0) {
////                } else if (dy < 0 && firstVisibleItem <= 5 && !viewModel.isLoading) {  // before <= 0
//                    val firstId = adapter.items.firstOrNull()?.id ?: 0
////                    if(firstId > 1){ // Ensure not fetching below 0 or fetching a batch of items
//                        Log.d("main", "direction up Loading up from2 ID: $firstId")
//
//                        startLoading() // shimmer effect till the data is loaded
//                        viewModel.loadItems(firstId, "up")
////                    }
//                }
                if (dy > 0 && lastVisibleItem >= totalItemCount - 1) {
                    val lastId = adapter.items.lastOrNull()?.id ?: 0
                    if (lastId < 2000) {
                        Log.d("main", "direction down Loading down from ID: $lastId")
                        startLoading()
                        viewModel.loadItems(lastId, "down")
                    }
                } else if (dy < 0 && firstVisibleItem == 0) {
                    val firstId = adapter.items.firstOrNull()?.id ?: 0
                    if (firstId > 20) {
                        Log.d("main", "direction up Loading up from ID: $firstId")
                        startLoading()
                        viewModel.loadItems(firstId, "up")
                    }
                }

            }
        })
    }
// to display shimmer effect
    private fun startLoading() {
        shimmerLayout.startShimmer()
        shimmerLayout.visibility = View.VISIBLE
    }
}