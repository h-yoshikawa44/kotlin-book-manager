package com.book.manager.application.service

import com.book.manager.domain.enum.RoleType
import com.book.manager.domain.model.BookModel
import com.book.manager.domain.model.BookWithRentalModel
import com.book.manager.domain.model.RentalModel
import com.book.manager.domain.model.UserModel
import com.book.manager.domain.repository.BookRepository
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.domain.repository.UserRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions


import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.LocalDate
import java.time.LocalDateTime

internal class RentalServiceTest {
    private val userRepository = mock<UserRepository>()
    private val bookRepository = mock<BookRepository>()
    private val rentalRepository = mock<RentalRepository>()

    private val rentalService = RentalService(userRepository, bookRepository, rentalRepository)

    @Test
    fun `endRental when book is rental then delete to rental`() {
        val userId = 100L
        val bookId = 1000L
        val user = UserModel(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
        val book = BookModel(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val rental = RentalModel(bookId, userId, LocalDateTime.now(), LocalDateTime.MAX)
        val bookWithRental = BookWithRentalModel(book, rental)

        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        rentalService.endRental(bookId, userId)

        // モック化した関数が想定通りの引数を渡して実行されているか（times と組み合わせる実行回数も指定できる）
        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository).endRental(bookId)
    }

    // 異常系のテスト
    @Test
    fun `endRental when book is not rental then throw exception`() {
        val userId = 100L
        val bookId = 1000L
        val user = UserModel(userId, "test@test.com", "pass", "kotlin", RoleType.USER)
        val book = BookModel(bookId, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRental = BookWithRentalModel(book, null)

        whenever(userRepository.find(any() as Long)).thenReturn(user)
        whenever(bookRepository.findWithRental(any())).thenReturn(bookWithRental)

        // 例外スローの検証
        val exception = Assertions.assertThrows(IllegalStateException::class.java) {
            rentalService.endRental(bookId, userId)
        }

        assertThat(exception.message).isEqualTo("未貸出の商品です bookId:${bookId}")

        verify(userRepository).find(userId)
        verify(bookRepository).findWithRental(bookId)
        verify(rentalRepository, times(0)).endRental(any())
    }
}
