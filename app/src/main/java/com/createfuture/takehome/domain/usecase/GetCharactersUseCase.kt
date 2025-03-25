package com.createfuture.takehome.domain.usecase

import com.createfuture.takehome.domain.model.Character
import com.createfuture.takehome.domain.repository.CharactersRepository

class GetCharactersUseCase(private val repository: CharactersRepository) {
    suspend operator fun invoke(): List<Character> {
        return repository.geCharacters()
    }
}