package com.createfuture.takehome.data.remote.dto


import com.google.gson.annotations.SerializedName

data class CharacterDto(
    @SerializedName("aliases")
    val aliases: List<String>,
    @SerializedName("born")
    val born: String,
    @SerializedName("culture")
    val culture: String,
    @SerializedName("died")
    val died: String,
    @SerializedName("father")
    val father: String?,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("mother")
    val mother: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("playedBy")
    val playedBy: List<String>,
    @SerializedName("povBooks")
    val povBooks: List<String>?,
    @SerializedName("titles")
    val titles: List<String>,
    @SerializedName("tvSeries")
    val tvSeries: List<String>
)