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

        assertThat(result).contains(lori, ted)
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

    @Test
    fun `search returns users that match the street parameter`() {
        val matchingStreet = "123 The Matching Street"
        val nonMatchingStreet = "456 Non Matching Street"

        val luke = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val carol = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val gina = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = nonMatchingStreet))

        val result = subject.search(matchingStreet)

        assertThat(result).contains(luke, carol)
        assertThat(result).doesNotContain(gina)
    }

    @Test
    fun `search is case insensitive when using the street parameter`() {
        val matchingStreet = "123 The Matching Street"
        val alsoMatchingStreet = "123 ThE MaTcHiNg StReEt"

        val luke = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val carol = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val gina = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = alsoMatchingStreet))

        val result = subject.search(matchingStreet)

        assertThat(result).contains(luke, carol, gina)
    }

    @Test
    fun `search returns partial matches when using the street parameter`() {
        val matchingStreet = "Matching Street"
        val alsoMatchingStreet = "123 ThE MaTcHiNg StReEt"

        val luke = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val carol = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = matchingStreet))
        val gina = userFactory.createUserEntity(address = userFactory.createAddressEntity(street = alsoMatchingStreet))

        val result = subject.search(matchingStreet)

        assertThat(result).contains(luke, carol, gina)
    }

    @Test
    fun `search returns an empty list when no addresses match the street`() {
        val nonMatchingStreet = "456 Non Matching Street"

        userFactory.createUserEntity(address = userFactory.createAddressEntity(street = nonMatchingStreet))
        userFactory.createUserEntity(address = userFactory.createAddressEntity(street = nonMatchingStreet))
        userFactory.createUserEntity(address = userFactory.createAddressEntity(street = nonMatchingStreet))

        val result = subject.search("matchingStreet")

        assertThat(result).isEmpty()
    }
}