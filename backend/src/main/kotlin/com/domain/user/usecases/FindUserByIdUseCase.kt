package com.domain.user.usecases

import com.domain.user.ports.UserRepository
import org.springframework.stereotype.Component

@Component
class FindUserByIdUseCase(
    private val userRepository: UserRepository
) {
    fun execute(id: Long) = userRepository.findById(id)
}