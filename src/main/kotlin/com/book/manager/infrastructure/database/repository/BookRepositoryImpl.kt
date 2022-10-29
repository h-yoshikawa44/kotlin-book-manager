package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.BookModel
import com.book.manager.domain.model.BookWithRentalModel
import com.book.manager.domain.model.RentalModel
import com.book.manager.domain.repository.BookRepository
import com.book.manager.infrastructure.database.mapper.custom.BookWithRentalMapper
import com.book.manager.infrastructure.database.mapper.custom.select
import org.springframework.stereotype.Repository

// interface を使用した上で実装することで、DB 関連の実装をこの中に閉じ込め、呼出し層から意識する必要がなくなる
@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class BookRepositoryImpl(
    private val bookWithRentalMapper: BookWithRentalMapper,
) : BookRepository {
    override fun findAllWithRental(): List<BookWithRentalModel> {
        // Record クラスはあくまで MyBatis に依存するため、Model クラスに変換することで、呼出し側からは Model クラスだけ意識でよくなる
        return bookWithRentalMapper.select().map { toModel(it) }
    }

    private fun toModel(record: com.book.manager.infrastructure.database.record.custom.BookWithRental): BookWithRentalModel {
        val bookModel = BookModel(
            record.id!!,
            record.title!!,
            record.author!!,
            record.releaseDate!!
        )
        val rentalModel = record.userId?.let {
            RentalModel(
                record.id!!,
                record.userId!!,
                record.rentalDatetime!!,
                record.returnDeadline!!
            )
        }
        return BookWithRentalModel(bookModel, rentalModel)
    }
}
