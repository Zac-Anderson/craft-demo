package com.app.web.user.api.v1

import com.app.web.util.Page
import com.domain.user.usecases.FindUserUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class V1UserController(
    private val findUserUseCase: FindUserUseCase
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun index(
        @RequestParam(required = false) q: String?
    ): Page<V1UserResponse> {
        val user = findUserUseCase
            .execute(1)
            ?.let { V1UserResponseTranslator.translate(it) }

        return Page(data = listOf(user!!))
    }
}