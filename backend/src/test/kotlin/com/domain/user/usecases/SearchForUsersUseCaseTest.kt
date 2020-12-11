package com.domain.user.usecases

import com.domain.user.ports.UserRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class SearchForUsersUseCaseTest {
    private lateinit var subject: SearchForUsersUseCase
    private val mockUserRepository = mock<UserRepository>()

    @Before
    fun setUp() {
        subject = SearchForUsersUseCase(mockUserRepository)
    }

    @Test
    fun `execute calls UserRepository_findAll when called without a street parameter`() {
        subject.execute(null)

        Mockito.verify(mockUserRepository).findAll()
    }

    @Test
    fun `execute calls UserRepository_search when called with a street parameter`() {
        val street = "123 Cool St."
        subject.execute(street)

        Mockito.verify(mockUserRepository).search(street)
    }
}