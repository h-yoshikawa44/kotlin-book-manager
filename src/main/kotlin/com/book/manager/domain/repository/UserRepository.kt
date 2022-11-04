package com.book.manager.domain.repository

import com.book.manager.domain.model.UserModel

interface UserRepository {
    fun find(email: String): UserModel?

    fun find(id: Long): UserModel?
}
