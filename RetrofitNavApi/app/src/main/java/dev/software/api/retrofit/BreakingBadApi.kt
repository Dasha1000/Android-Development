package dev.software.api.retrofit

import dev.software.api.model.CharacterDescription
import dev.software.api.model.Item
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BreakingBadApi {
    @GET("characters")
    fun getCharacters(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ) : Call<List<Item.Characters>>

    @GET("characters/{char_id}")
    fun characterDescription(
        @Path("char_id") char_id: Int
    ): Call<List<CharacterDescription>>
}

//https://www.breakingbadapi.com/api/characters/?limit=10&offset=5//example