package com.mvvm.model

import com.google.gson.annotations.SerializedName

data class ApiKey(
    val message: String?,
    val key: String?
)

data class Animal(
    val name: String?,
    val taxonomy: Taxonomy?,
    val location: String?,
    val speed: Speed?,
    val diet: String?,

    @SerializedName("lifespan") //to do when the name of the variable in the json and our program differs
    val lifespan: String?,

    @SerializedName("image")
    val imageUrl: String?
)

data class Taxonomy(
    val kingdom: String?,
    val order: String?,
    val family: String?
)

data class Speed(
    val metric: String?,
    val imperial: String?
)