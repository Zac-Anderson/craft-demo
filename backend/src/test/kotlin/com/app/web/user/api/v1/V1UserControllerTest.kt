package com.app.web.user.api.v1

import com.domain.user.User
import com.domain.user.usecases.FindUserByIdUseCase
import com.domain.user.usecases.SearchForUsersUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.stub
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.client.match.MockRestRequestMatchers.content
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@SpringBootTest
@RunWith(SpringRunner::class)
class V1UserControllerTest {
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var context: WebApplicationContext

    @MockBean
    private lateinit var mockSearchForUsersUseCase: SearchForUsersUseCase

    @MockBean
    private lateinit var mockFindUserByIdUseCase: FindUserByIdUseCase

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build()
    }

    @Test
    fun `index should return a page of users`() {
        val june = User(
            id = 1,
            name = "Fake June",
            username = "fakeUserName",
            email = "fake@email.com",
            address = User.Address(
                id = 3,
                street = "123 Fake St.",
                city = "Fakeville",
                zipcode = 12345
            ),
            phone = "111-222-3333"
        )

        val carry = User(
            id = 2,
            name = "Real Carry",
            username = "realUserName",
            email = "real@email.com",
            address = User.Address(
                id = 4,
                street = "456 Real St.",
                city = "Real City",
                zipcode = 54321
            ),
            phone = "444-555-6666"
        )

        whenever(mockSearchForUsersUseCase.execute(null)).thenReturn(listOf(june, carry))

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(
                    """
                         {
                            "data": [
                                {
                                    "id":1,
                                    "name":"Fake June",
                                    "username":"fakeUserName",
                                    "email":"fake@email.com",
                                    "address": {
                                        "street":"123 Fake St.",
                                        "city":"Fakeville",
                                        "zipcode":12345
                                    },
                                    "phone":"111-222-3333"
                                },
                                {
                                    "id":2,
                                    "name":"Real Carry",
                                    "username":"realUserName",
                                    "email":"real@email.com",
                                    "address": {
                                        "street":"456 Real St.",
                                        "city":"Real City",
                                        "zipcode":54321
                                    },
                                    "phone":"444-555-6666"
                                }
                            ]
                        } 
                    """))
    }

    @Test
    fun `index with a street parameter should return a page of users`() {
        val street = "123 Fake St."

        val june = User(
            id = 1,
            name = "Fake June",
            username = "fakeUserName",
            email = "fake@email.com",
            address = User.Address(
                id = 3,
                street = street,
                city = "Fakeville",
                zipcode = 12345
            ),
            phone = "111-222-3333"
        )

        val carry = User(
            id = 2,
            name = "Real Carry",
            username = "realUserName",
            email = "real@email.com",
            address = User.Address(
                id = 4,
                street = street,
                city = "Real City",
                zipcode = 54321
            ),
            phone = "444-555-6666"
        )

        whenever(mockSearchForUsersUseCase.execute(street)).thenReturn(listOf(june, carry))

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users?street=$street"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(
                """
                         {
                            "data": [
                                {
                                    "id":1,
                                    "name":"Fake June",
                                    "username":"fakeUserName",
                                    "email":"fake@email.com",
                                    "address": {
                                        "street":"123 Fake St.",
                                        "city":"Fakeville",
                                        "zipcode":12345
                                    },
                                    "phone":"111-222-3333"
                                },
                                {
                                    "id":2,
                                    "name":"Real Carry",
                                    "username":"realUserName",
                                    "email":"real@email.com",
                                    "address": {
                                        "street":"123 Fake St.",
                                        "city":"Real City",
                                        "zipcode":54321
                                    },
                                    "phone":"444-555-6666"
                                }
                            ]
                        } 
                    """))
    }

    @Test
    fun `findById should return a matching user`() {
        val june = User(
            id = 1,
            name = "Fake June",
            username = "fakeUserName",
            email = "fake@email.com",
            address = User.Address(
                id = 3,
                street = "123 Fake St.",
                city = "Fakeville",
                zipcode = 12345
            ),
            phone = "111-222-3333"
        )

        whenever(mockFindUserByIdUseCase.execute(june.id)).thenReturn(june)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/${june.id}"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(
                """
                    {
                        "id":1,
                        "name":"Fake June",
                        "username":"fakeUserName",
                        "email":"fake@email.com",
                        "address": {
                            "street":"123 Fake St.",
                            "city":"Fakeville",
                            "zipcode":12345
                        },
                        "phone":"111-222-3333"
                    }
                    """))
    }

    @Test
    fun `findById should return a 404 status when no user is found`() {
        whenever(mockFindUserByIdUseCase.execute(any())).thenReturn(null)

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/1"))
            .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }
}