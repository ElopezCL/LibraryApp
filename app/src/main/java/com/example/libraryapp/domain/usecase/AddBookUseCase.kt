import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.repository.BookRepository

class AddBookUseCase(private val repository: BookRepository) {
    suspend operator fun invoke(
        title: String,
        author: String,
        year: Int,
        description: String,
        imageUrl: String? = null
    ) {
        require(title.isNotBlank()) { "Title cannot be empty" }
        require(author.isNotBlank()) { "Author cannot be empty" }
        require(year > 0) { "Invalid year" }
        val book = Book(
            id = 0,
            title = title,
            author = author,
            year = year,
            description = description,
            imageUrl = imageUrl,
            isAvailable = true
        )
        repository.addBook(book)
    }
}