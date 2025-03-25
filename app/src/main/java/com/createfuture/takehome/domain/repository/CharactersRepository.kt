package com.createfuture.takehome.domain.repository

import com.createfuture.takehome.domain.model.Character

interface CharactersRepository {
    suspend fun geCharacters(): List<Character>
}