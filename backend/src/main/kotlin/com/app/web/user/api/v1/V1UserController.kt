package com.app.web.user.api.v1

import com.app.web.util.Page
import com.domain.user.usecases.FindUserUseCase
import com.domain.user.usecases.SearchForUsersUseCase
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/users")
class V1UserController(
    private val findUserUseCase: FindUserUseCase,
    private val searchForUsersUseCase: SearchForUsersUseCase
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun index(): Page<V1UserResponse> {
        val users = searchForUsersUseCase
            .execute()
            .map(V1UserResponseTranslator::translate)

        return Page(data = users)
    }
}