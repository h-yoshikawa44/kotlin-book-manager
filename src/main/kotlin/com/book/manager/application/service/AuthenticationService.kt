package com.book.manager.application.service

import com.book.manager.domain.model.UserModel
import com.book.manager.domain.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class AuthenticationService(private val userRepository: UserRepository) {
    fun findUser(email: String): UserModel? {
        return userRepository.find(email)
    }
}
