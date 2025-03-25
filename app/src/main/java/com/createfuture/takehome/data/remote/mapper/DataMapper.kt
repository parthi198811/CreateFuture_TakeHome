package com.createfuture.takehome.data.remote.mapper

import com.createfuture.takehome.data.remote.dto.CharacterDto
import com.createfuture.takehome.domain.model.Character

// Extension function to convert CharacterDto [Data Layer] to Character [Domain Layer]
fun CharacterDto.toDomain() = Character(
    name = this.name,
    gender = this.gender,
    culture = culture,
    born = born,
    died = died,
    aliases = aliases,
    tvSeries = tvSeries,
    playedBy = playedBy,
)