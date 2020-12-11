package com.domain.user.usecases

import com.domain.user.User
import com.domain.user.ports.UserRepository
import org.springframework.stereotype.Component

@Component
class SearchForUsersUseCase(
    private val userRepository: UserRepository
) {
    fun execute(street: String?): List<User> {
        return when(street) {
            null -> userRepository.findAll()
            else -> userRepository.search(street)
        }
    }
}