package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.RentalModel
import com.book.manager.domain.repository.RentalRepository
import com.book.manager.infrastructure.database.mapper.RentalMapper
import com.book.manager.infrastructure.database.mapper.insert
import com.book.manager.infrastructure.database.record.Rental
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class RentalRepositoryImpl(
    private val rentalMapper: RentalMapper
) : RentalRepository {
    override fun startRental(rental: RentalModel) {
        rentalMapper.insert(toRecord(rental))
    }

    private fun toRecord(model: RentalModel): Rental {
        return Rental(model.bookId, model.userId, model.rentalDateTime, model.returnDeadline)
    }
}
