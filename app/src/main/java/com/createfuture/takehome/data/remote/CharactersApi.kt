package com.createfuture.takehome.data.remote

import com.createfuture.takehome.data.remote.dto.CharacterDto
import retrofit2.http.GET

interface CharactersApi {
    @GET("/characters")
    suspend fun getCharacters(): List<CharacterDto>
}
