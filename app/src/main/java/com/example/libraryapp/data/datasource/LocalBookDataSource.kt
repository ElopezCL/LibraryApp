package com.example.libraryapp.data.datasource

import com.example.libraryapp.domain.model.Book
import kotlinx.coroutines.delay

class LocalBookDataSource {

    suspend fun getBooks(): List<Book> {
        delay(1000)
        return BookDatabase.getBooks()
    }

    suspend fun getBook(id: Int): Book? {
        delay(500)
        return BookDatabase.getBook(id)
    }

    suspend fun addBook(book: Book) {
        delay(500)
        BookDatabase.addBook(book)
    }

    suspend fun updateBook(book: Book) {
        BookDatabase.updateBook(book)
    }

     suspend fun getBookById(id: Int): Book? {
        return BookDatabase.getBook(id)
    }



}