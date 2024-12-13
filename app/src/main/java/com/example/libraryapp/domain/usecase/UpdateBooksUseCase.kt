package com.example.libraryapp.domain.usecase

import com.example.libraryapp.data.repository.BookRepositoryProvider
import com.example.libraryapp.domain.model.Book

class UpdateBooksUseCase {
    private val repository = BookRepositoryProvider.provideRepository()

    suspend operator fun invoke(book: Book): Book {
        return repository.updateBook(book)
    }


}