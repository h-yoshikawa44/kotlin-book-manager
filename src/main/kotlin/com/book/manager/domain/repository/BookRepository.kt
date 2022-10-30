package com.book.manager.domain.repository

import com.book.manager.domain.model.BookWithRentalModel

interface BookRepository {
    fun findAllWithRental(): List<BookWithRentalModel>

    fun findWithRental(id: Long): BookWithRentalModel?
}
