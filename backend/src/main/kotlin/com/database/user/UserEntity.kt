package com.database.user

import com.domain.user.User
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name")
    var name: String? = null,

    @Column(name = "username")
    var username: String? = null,

    @Column(name = "email")
    var email: String? = null,

    @OneToOne
    @JoinColumn(name = "address_id")
    var address: AddressEntity? = null,

    @Column(name = "phone")
    var phone: String? = null
) {
    @Entity
    @Table(name = "addresses")
    data class AddressEntity(
        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        @Column(name = "street")
        var street: String? = null,

        @Column(name = "city")
        var city: String? = null,

        @Column(name = "zipcode")
        var zipcode: Int? = null
    )
}

object UserEntityTranslator {
    fun translate(userEntity: UserEntity): User =
        User(
            id = userEntity.id!!,
            name = userEntity.name!!,
            username = userEntity.username!!,
            email = userEntity.email!!,
            address = userEntity.address?.let { User.Address(
                    id = it.id!!,
                    street = it.street!!,
                    city = it.city!!,
                    zipcode = it.zipcode!!) },
            phone = userEntity.phone!!
        )
}
