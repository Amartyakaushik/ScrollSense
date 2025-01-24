package com.example.scrollsense.ui

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollsense.R
import com.example.scrollsense.databinding.ActivityMainBinding
import com.example.scrollsense.network.ItemRepository
import com.example.scrollsense.network.MockApi
import com.example.scrollsense.viewmodel.ItemViewModel
//import com.google.android.ads.mediationtestsuite.viewmodels.ViewModelFactory
import com.example.scrollsense.viewmodel.ViewModelFactory
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewModel: ItemViewModel
    private lateinit var adapter: ItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, ViewModelFactory(ItemRepository(MockApi())) ).get(ItemViewModel::class.java)
        adapter = ItemAdapter("")
//        viewModel = ViewModelProvider(this, ViewModelFactory(ItemRepository(MockApi()))).get(ItemViewModel::class.java)
//        adapter = ItemAdapter("")

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

        viewModel.filteredItems.observe(this, Observer { items ->
            adapter.submitList(items)
        })

        viewModel.error.observe(this, Observer { error ->
            (binding.errorMessage).visibility = if (error) View.VISIBLE else View.GONE
        })

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

                if (dy > 0 && lastVisibleItem >= totalItemCount - 1) {
                    val lastId = adapter.items.lastOrNull()?.id ?: 0
                    viewModel.loadItems(lastId, "down")
                } else if (dy < 0 && firstVisibleItem <= 0) {
                    val firstId = adapter.items.firstOrNull()?.id ?: 0
                    viewModel.loadItems(firstId, "up")
                }
            }
        })
    }
}