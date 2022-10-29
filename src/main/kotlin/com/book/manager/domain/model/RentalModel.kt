package com.book.manager.domain.model

import java.time.LocalDateTime

data class RentalModel(
    val bookId: Long,
    val userId: Long,
    val rentalDateTime: LocalDateTime,
    val returnDeadline: LocalDateTime
)
