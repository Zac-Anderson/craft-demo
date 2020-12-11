package com.domain.user

data class User(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: Address?,
    val phone: String
) {
    data class Address(
        val id: Long,
        val street: String,
        val city: String,
        val zipcode: Int
    )
}
