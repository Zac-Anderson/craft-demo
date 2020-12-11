package com.app.web.user.api.v1

import com.domain.user.User

data class V1UserResponse(
    val id: Long,
    val name: String,
    val username: String,
    val email: String,
    val address: V1AddressResponse?,
    val phone: String
) {
    data class V1AddressResponse(
        val street: String,
        val city: String,
        val zipcode: Int
    )
}

object V1UserResponseTranslator {
    fun translate(user: User) =
        V1UserResponse(
            id = user.id,
            name = user.name,
            username = user.username,
            email = user.email,
            address = user.address?.let { V1UserResponse.V1AddressResponse(
                street = it.street,
                city = it.city,
                zipcode = it.zipcode) },
            phone = user.phone
        )
}