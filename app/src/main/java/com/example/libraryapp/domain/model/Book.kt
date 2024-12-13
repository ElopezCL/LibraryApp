package com.example.libraryapp.domain.model

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val year: Int,
    val description: String,
    val isAvailable: Boolean,
    val imageUrl: String? = null
)
