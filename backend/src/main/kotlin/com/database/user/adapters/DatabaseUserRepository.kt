package com.database.user.adapters

import com.database.user.JpaUserRepository
import com.database.user.UserEntityTranslator
import com.domain.user.User
import com.domain.user.ports.UserRepository
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
class DatabaseUserRepository(
    private val jpaUserRepository: JpaUserRepository
) : UserRepository {

    @Transactional
    override fun findAll(): List<User> {
        return jpaUserRepository
            .findAll()
            .map(UserEntityTranslator::translate)
    }

    override fun findById(id: Long): User? {
        return jpaUserRepository
            .findByIdOrNull(id)
            ?.let { UserEntityTranslator.translate(it) }
    }
}