package com.book.manager.application.service

import com.book.manager.domain.model.BookWithRentalModel
import com.book.manager.domain.repository.BookRepository
import org.springframework.stereotype.Service

// Repository でのデータ操作の処理などを行い、ビジネスロジックを実装する層
@Service
class BookService(
    private val bookRepository: BookRepository
) {
    fun getList(): List<BookWithRentalModel> {
        return bookRepository.findAllWithRental()
    }
}
