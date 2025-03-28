package com.createfuture.takehome.domain.model

data class Character(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)