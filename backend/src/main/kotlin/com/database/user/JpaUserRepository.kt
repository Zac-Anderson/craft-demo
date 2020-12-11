package com.database.user

import org.springframework.data.repository.CrudRepository

interface JpaUserRepository : CrudRepository<UserEntity, Long>