package com.createfuture.takehome.data.repository

import com.createfuture.takehome.data.remote.CharactersApi
import com.createfuture.takehome.data.remote.mapper.toDomain
import com.createfuture.takehome.domain.model.Character
import com.createfuture.takehome.domain.repository.CharactersRepository
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val api: CharactersApi
) : CharactersRepository {

    override suspend fun geCharacters(): List<Character> {
        val response = api.getCharacters()
        return response.map { it.toDomain() }
    }
}