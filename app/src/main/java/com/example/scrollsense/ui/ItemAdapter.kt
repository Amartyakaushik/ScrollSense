package com.example.scrollsense.ui

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
import com.example.scrollsense.model.Item

class ItemAdapter(private var query: String) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    var items = mutableListOf<Item>()

    fun submitList(newItems: List<Item>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(items[position], query)
    }

    override fun getItemCount(): Int = items.size

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.itemTitle)

        fun bind(item: Item, query: String) {
            val spannable = SpannableString(item.title)
            val startIndex = item.title.indexOf(query, ignoreCase = true)
            if (startIndex >= 0) {
                spannable.setSpan(
                    ForegroundColorSpan(Color.YELLOW),
                    startIndex,
                    startIndex + query.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            titleView.text = spannable
        }
    }
    fun updateQuery(newQuery: String) {
        query = newQuery
        notifyDataSetChanged()
    }
}