package com.example.libraryapp.data.repository

import com.example.libraryapp.data.datasource.LocalBookDataSource
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository

class BookRepositoryImpl: BookRepository {

    private val localDataSource = LocalBookDataSource()

    //  metodo getBooks
    override suspend fun getBooks(): List<Book> {
        return try {
            localDataSource.getBooks()
        } catch (e: Exception) {
            throw Exception("Error fetching books", e)
        }
    }

    // método addBook
    override suspend fun addBook(book: Book) {
        try {
            localDataSource.addBook(book)
        } catch (e: Exception) {
            throw Exception("Error adding book", e)
        }

    }

    // método getBookById
    override suspend fun getBookById(id: Int): Book? {
        return try {
            localDataSource.getBook(id)
        } catch (e: Exception) {
            throw Exception("Error fetching book by id", e)
        }
    }

    override suspend fun updateBook(book: Book): Book {
        return try {
            localDataSource.updateBook(book)
            book
        } catch (e: Exception) {
            throw Exception("Error updating book", e)
        }
    }
}