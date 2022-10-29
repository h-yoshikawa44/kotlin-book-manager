package com.book.manager.domain.model

import java.time.LocalDate

data class BookModel (
    val id: Long,
    val title: String,
    val author: String,
    val releaseDate: LocalDate
)
