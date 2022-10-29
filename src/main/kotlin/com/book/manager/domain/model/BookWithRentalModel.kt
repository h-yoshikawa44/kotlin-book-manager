package com.book.manager.domain.model

data class BookWithRentalModel(
    val book: BookModel,
    val rental: RentalModel?
) {
    val isRental: Boolean
        get() = rental != null
}
