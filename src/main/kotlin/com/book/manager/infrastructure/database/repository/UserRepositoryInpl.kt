package com.book.manager.infrastructure.database.repository

import com.book.manager.domain.model.UserModel
import com.book.manager.domain.repository.UserRepository
import com.book.manager.infrastructure.database.mapper.UserDynamicSqlSupport.user
import com.book.manager.infrastructure.database.mapper.UserMapper
import com.book.manager.infrastructure.database.mapper.selectOne
import com.book.manager.infrastructure.database.record.User
import org.springframework.stereotype.Repository

@Suppress("SpringJavaInjectionPointsAutowiringInspection")
@Repository
class UserRepositoryImpl(
    private val mapper: UserMapper
) : UserRepository {
    override fun find(email: String): UserModel? {
        val record = mapper.selectOne {
            where { user.email isEqualTo email }
        }
        return record?.let { toModel(it) }
    }

    private fun toModel(record: User): UserModel {
        return UserModel(
            record.id!!,
            record.email!!,
            record.password!!,
            record.name!!,
            record.roleType!!
        )
    }
}
