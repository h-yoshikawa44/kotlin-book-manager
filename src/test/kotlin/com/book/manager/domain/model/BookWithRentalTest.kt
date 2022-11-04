package com.book.manager.domain.model

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class BookWithRentalTest {
    @Test
    fun `isRental when rental is null then return false`() {
        val book = BookModel(1, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRentalModel = BookWithRentalModel(book, null)
        Assertions.assertFalse(bookWithRentalModel.isRental)
    }

    @Test
    fun `isRental when rental is not null then return true`() {
        val book = BookModel(1, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val rental = RentalModel(1, 100, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRentalModel(book, rental)
        Assertions.assertTrue(bookWithRental.isRental)
    }
}
