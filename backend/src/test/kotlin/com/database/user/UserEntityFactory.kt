package com.database.user

import com.domain.user.User
import org.springframework.stereotype.Component
import javax.persistence.EntityManager

@Component
class UserEntityFactory(
    private val em: EntityManager
) {
    fun createUserEntity(
        name: String = "Factory User Name",
        username: String = "Factory User Username",
        email: String = "Factory User Email",
        address: UserEntity.AddressEntity? = createAddressEntity(),
        phone: String = "Factory User Phone"
    ): User {
        val userEntity = UserEntity(
            name = name,
            username = username,
            email = email,
            address = address,
            phone = phone
        )

        em.persist(userEntity)
        return UserEntityTranslator.translate(userEntity)
    }

    fun createAddressEntity(
        street: String = "Factory Address Street",
        city: String = "Factory Address City",
        zipcode: Int = 99999
    ): UserEntity.AddressEntity {
        val addressEntity = UserEntity.AddressEntity(
            street = street,
            city = city,
            zipcode = zipcode
        )

        em.persist(addressEntity)
        return addressEntity
    }
}