package com.database.user

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface JpaUserRepository : CrudRepository<UserEntity, Long> {

    @Query(value =
        """
            SELECT *
            FROM users u
                LEFT JOIN addresses a ON u.address_id = a.id
            WHERE lower(a.street) like concat('%', lower(:street), '%')
        """,
        nativeQuery = true
    )
    fun search(@Param("street") street: String): List<UserEntity>
}