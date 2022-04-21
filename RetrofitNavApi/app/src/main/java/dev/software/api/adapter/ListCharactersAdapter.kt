package dev.software.api.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.software.api.databinding.ItemCharacterBinding
import dev.software.api.databinding.ItemLoadingBinding
import dev.software.api.model.Item


class ListCharactersAdapter(
    private val onCharacterClicked: (Item.Characters) -> Unit
) : ListAdapter<Item, RecyclerView.ViewHolder>(DIFF_UTIL) {




    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Item.Characters -> TYPE_CHARACTERS
            Item.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_CHARACTERS -> {
                CharacterViewHolder(
                    binding = ItemCharacterBinding.inflate(layoutInflater, parent, false),
                    onCharacterClicked = onCharacterClicked
                )
            }
            TYPE_LOADING -> {
                LoadingViewHolder (
                    binding = ItemLoadingBinding.inflate(layoutInflater, parent, false)
                )
            }
            else -> error("Incorrect view type - $viewType")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val characterViewHolder = holder as? CharacterViewHolder ?: return
        val item = getItem(position) as? Item.Characters ?: return
        characterViewHolder.bind(item)
    }



    companion object {

        private const val TYPE_CHARACTERS = 0
        private const val TYPE_LOADING = 1

        private var DIFF_UTIL = object : DiffUtil.ItemCallback<Item>() {

            override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
                return oldItem == newItem
            }
        }
    }
}
