package com.domain.user.ports

import com.domain.user.User

interface UserRepository {
    fun findAll(): List<User>
    fun findById(id: Long): User?
}