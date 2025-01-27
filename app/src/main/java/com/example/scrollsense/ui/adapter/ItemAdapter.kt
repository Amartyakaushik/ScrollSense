package com.example.scrollsense.ui.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollsense.R
import com.example.scrollsense.model.dataClass.Item

/**
 * Adapter class for displaying a list of items in a RecyclerView.
 * Supports highlighting parts of item titles based on a search query.
 */
class ItemAdapter(private var query: String) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    // A mutable list to hold the items displayed in the RecyclerView
    var items = mutableListOf<Item>()


    //Updates the list of items displayed in the RecyclerView.
    fun submitList(newItems: List<Item>) {
        items.clear() // Clear the current list
        items.addAll(newItems) // Add new items to the list
        notifyDataSetChanged() // Notify the adapter that data has changed
    }


     // Inflates the layout for each item view and returns the corresponding ViewHolder.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }


     // Binds data to the ViewHolder for the specified position.

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], query) // Pass the item and query to the ViewHolder's bind method
    }

    override fun getItemCount(): Int = items.size


    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Reference to the TextView displaying the item's title
        private val titleView: TextView = itemView.findViewById(R.id.itemTitle)


        //Binds an Item to the ViewHolder and highlights parts of the title matching the query.

        fun bind(item: Item, query: String) {
            val spannable = SpannableString(item.title) // Convert the title to a SpannableString
            val startIndex = item.title.indexOf(query, ignoreCase = true) // Find the start index of the query
            if (startIndex >= 0) {
                // Highlight the query substring with a gray color
                spannable.setSpan(
                    ForegroundColorSpan(Color.GRAY),
                    startIndex,
                    startIndex + query.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            titleView.text = spannable // Set the SpannableString as the TextView's text
        }
    }


    //Updates the search query used for highlighting and refreshes the RecyclerView.

    fun updateQuery(newQuery: String) {
        query = newQuery // Update the query
        notifyDataSetChanged() // Refresh the RecyclerView to apply the new query
    }
}
