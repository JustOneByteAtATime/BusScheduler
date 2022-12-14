package com.example.busschedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.databinding.BusStopItemBinding

// 1. Create a new file BusStopAdapter.kt and a BusStopAdapter class as shown. The class extends a
// generic ListAdapter that takes a list of Schedule objects and a BusStopViewHolder class for the
// UI. For the BusStopViewHolder, you also pass in a DiffCallback type which you'll define soon. The
// BusStopAdapter class itself also takes a parameter, onItemClicked(). This function will be used to
// handle navigation when an item is selected on the first screen, but for the second screen, you'll
// just pass in an empty function.

class BusStopAdapter(private val onItemClicked: (Schedule) -> Unit
) : ListAdapter<Schedule, BusStopAdapter.BusStopViewHolder>(DiffCallback) {

    // Remember that DiffCallback class you specified for the ListAdapter? This is just an object
    // that helps the ListAdapter determine which items in the new and old lists are different when
    // updating the list. There are two methods: areItemsTheSame() checks if the object (or row in
    // the database in your case) is the same by only checking the ID. areContentsTheSame() checks
    // if all properties, not just the ID, are the same. These methods allow the ListAdapter to
    // determine which items have been inserted, updated, and deleted so that the UI can be updated accordingly.
    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Schedule>() {
            override fun areItemsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Schedule, newItem: Schedule): Boolean {
                return oldItem == newItem
            }
        }
    }


    // Override and implement onCreateViewHolder() and inflate the layout and set the onClickListener()
    // to call onItemClicked() for the item at the current position.

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusStopViewHolder {
        val viewHolder = BusStopViewHolder(
            BusStopItemBinding.inflate(
                LayoutInflater.from( parent.context),
                parent,
                false
            )
        )
        viewHolder.itemView.setOnClickListener {
            val position = viewHolder.adapterPosition
            onItemClicked(getItem(position))
        }
        return viewHolder
    }

    // Override and implement onBindViewHolder() and to bind the view at the specified position.
    override fun onBindViewHolder(holder: BusStopViewHolder, position: Int) {
        holder.bind(getItem(position))
    }



    // Similar to a recycler view adapter, you need a view holder so that you can access views created
    // from your layout file in code. The layout for the cells is already created. Simply, create a
    // BusStopViewHolder class as shown and implement the bind() function to set stopNameTextView's
    // text to the stop name and the arrivalTimeTextView's text to the formatted date.
    class BusStopViewHolder(private var binding: BusStopItemBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SimpleDateFormat")
        fun bind(schedule: Schedule) {
            binding.stopNameTextView.text = schedule.stopName
            binding.arrivalTimeTextView.text = SimpleDateFormat(
                "h:mm a").format(Date(schedule.arrivalTime.toLong() * 1000)
            )
        }
    }
}



