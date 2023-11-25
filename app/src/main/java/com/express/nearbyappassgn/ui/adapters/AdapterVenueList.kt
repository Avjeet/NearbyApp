package com.express.nearbyappassgn.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.express.nearbyappassgn.databinding.ItemChildVenueBinding
import com.express.nearbyappassgn.model.VenueUIItem

class AdapterVenueList(context: Context, private val itemClickListener: OnItemClickListener) :
    PagingDataAdapter<VenueUIItem, AdapterVenueList.VenueViewHolder>(DIFF_CALLBACK) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)


    inner class VenueViewHolder(private val binding: ItemChildVenueBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(venue: VenueUIItem) {
            binding.tvVenueName.text = venue.name
            binding.tvVenueAddress.text = venue.address
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueViewHolder {
        val binding = ItemChildVenueBinding.inflate(layoutInflater, parent, false)
        return VenueViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VenueViewHolder, position: Int) {
        getItem(position)?.let { item ->
            holder.bind(item)
            holder.itemView.setOnClickListener {
                val clickedPos = holder.bindingAdapterPosition
                val currentVenue = getItem(clickedPos)

                currentVenue?.let {
                    itemClickListener.onItemClick(
                        clickedPos, it
                    )
                }
            }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VenueUIItem>() {
            override fun areItemsTheSame(oldItem: VenueUIItem, newItem: VenueUIItem) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: VenueUIItem, newItem: VenueUIItem) =
                oldItem == newItem
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, item: VenueUIItem)
    }

}