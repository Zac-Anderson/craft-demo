package com.app.web.user.api.v1

import com.app.web.util.Page
import com.domain.user.User
import com.domain.user.usecases.FindUserByIdUseCase
import com.domain.user.usecases.SearchForUsersUseCase
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class V1UserController(
    private val findUserByIdUseCase: FindUserByIdUseCase,
    private val searchForUsersUseCase: SearchForUsersUseCase
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun index(
        @RequestParam(required = false) q: String?
    ): Page<V1UserResponse> {
        val users = searchForUsersUseCase
            .execute()
            .map(V1UserResponseTranslator::translate)

        return Page(users)
    }

    @GetMapping("{id}")
    fun findById(@PathVariable id: Long): ResponseEntity<V1UserResponse> {
        return when(val user = findUserByIdUseCase.execute(id)) {
            is User -> ResponseEntity.ok(V1UserResponseTranslator.translate(user))
            else -> ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }
}