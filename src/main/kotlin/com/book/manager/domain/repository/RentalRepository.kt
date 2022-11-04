package com.book.manager.domain.repository

import com.book.manager.domain.model.RentalModel

interface RentalRepository {
    fun startRental(rental: RentalModel)
}
