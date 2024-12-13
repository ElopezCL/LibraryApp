package com.example.libraryapp.domain.usecase

import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository


class GetBookByIdUseCase(private val repository: BookRepository) {

    suspend operator fun invoke(id: Int): Book? {
        return repository.getBookById(id)
    }
}