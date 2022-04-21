package dev.software.api.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import dev.software.api.databinding.ItemCharacterBinding
import dev.software.api.model.Item

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
    private val onCharacterClicked: (Item.Characters) -> Unit
) : RecyclerView.ViewHolder(binding.root){

    fun bind(character: Item.Characters) {
        with(binding) {
            picture.load(character.url){
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            textName.text = character.name

            root.setOnClickListener{
                onCharacterClicked(character)
            }
        }
    }
}