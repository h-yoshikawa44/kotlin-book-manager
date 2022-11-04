package com.book.manager.application.service

import com.book.manager.domain.model.BookModel
import com.book.manager.domain.model.BookWithRentalModel
import com.book.manager.domain.repository.BookRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.time.LocalDate

internal class BookServiceTest {
    // モック化
    private val bookRepository = mock<BookRepository>()

    // DI を使用していることで、テスト時はモックオブジェクトをコンストラクタに渡すだけで差し替えができる
    private val bookService = BookService(bookRepository)

    @Test
    fun `getList when book list is exist then return list`() {
        val book = BookModel(1, "Kotlin入門", "コトリン太郎", LocalDate.now())
        val bookWithRental = BookWithRentalModel(book, null)
        val expected = listOf(bookWithRental)

        // モックオブジェクトが処理する関数と戻り値を設定
        whenever(bookRepository.findAllWithRental()).thenReturn(expected)

        val result = bookService.getList()
        Assertions.assertThat(expected).isEqualTo(result)
    }
}
