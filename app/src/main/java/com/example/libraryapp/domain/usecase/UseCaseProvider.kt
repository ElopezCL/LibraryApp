package com.example.libraryapp.domain.usecase

import AddBookUseCase
import com.example.libraryapp.data.repository.BookRepositoryImpl
import com.example.libraryapp.domain.repository.BookRepository

object UseCaseProvider {
    private var getBooksUseCase: GetBooksUseCase? = null
    private var updateBookUseCase: UpdateBooksUseCase? = null

    private var addBookUseCase: AddBookUseCase? = null
    private val bookRepository: BookRepository = BookRepositoryImpl()


    fun provideGetBooksUseCase(): GetBooksUseCase {
        if (getBooksUseCase == null) {
            getBooksUseCase = GetBooksUseCase()
        }
        return getBooksUseCase!!
    }

    fun provideAddBookUseCase(): AddBookUseCase {
        if (addBookUseCase == null) {
            addBookUseCase = AddBookUseCase(bookRepository)
        }
        return addBookUseCase!!

    }
    private var getBookDetailUseCase: GetBookByIdUseCase? = null

    fun provideGetBookDetailUseCase(): GetBookByIdUseCase {
        if (getBookDetailUseCase == null) {
            getBookDetailUseCase = GetBookByIdUseCase(bookRepository) // Inyectando el repositorio
        }
        return getBookDetailUseCase!!
    }

    fun provideUpdateBookUseCase(): UpdateBooksUseCase {
        if (updateBookUseCase == null) {
            updateBookUseCase = UpdateBooksUseCase()
        }
        return updateBookUseCase!!
    }

}