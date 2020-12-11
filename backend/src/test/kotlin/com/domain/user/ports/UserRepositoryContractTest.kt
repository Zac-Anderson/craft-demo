package com.domain.user.ports

import com.database.user.UserEntityFactory
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import javax.transaction.Transactional

@Transactional
abstract class UserRepositoryContractTest {
    private lateinit var subject: UserRepository
    private lateinit var userFactory: UserEntityFactory

    @Before
    fun setUp() {
        subject = buildSubject()
        userFactory = buildUserEntityFactory()
    }

    abstract fun buildSubject(): UserRepository
    abstract fun buildUserEntityFactory(): UserEntityFactory

    @Test
    fun `findAll returns all users`() {
        val lori = userFactory.createUserEntity()
        val ted = userFactory.createUserEntity()

        val result = subject.findAll()

        assertThat(result).contains(lori,ted)
    }

    @Test
    fun `findOne returns a matching user`() {
        val luke = userFactory.createUserEntity()

        val result = subject.findById(luke.id)

        assertThat(result).isEqualTo(luke)
    }

    @Test
    fun `findOne returns null when no user matches the id provided`() {
        val result = subject.findById(-1)

        assertThat(result).isNull()
    }
}