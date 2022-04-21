package dev.software.api.model

import com.google.gson.annotations.SerializedName

sealed class Item {
    //class CharacterModel(val characterItem: Character) : Item()
    data class Characters(
        @SerializedName("char_id")
        val id: Int,
        val name: String,
        @SerializedName("img")
        val url: String
    ) : Item()
    object Loading :  Item()
}
