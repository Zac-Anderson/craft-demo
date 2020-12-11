package com.database.user.adapters

import com.database.DatabaseDependencyLoader
import com.database.user.JpaUserRepository
import com.database.user.UserEntityFactory
import com.domain.user.ports.UserRepository
import com.domain.user.ports.UserRepositoryContractTest
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Transactional
@RunWith(SpringRunner::class)
@ActiveProfiles(profiles = ["test"])
@SpringBootTest(classes = [DatabaseDependencyLoader::class])
class DatabaseUserRepositoryContractTest : UserRepositoryContractTest() {
    @Autowired
    private lateinit var jpaUserRepository: JpaUserRepository

    @Autowired
    private lateinit var entityManager: EntityManager

    override fun buildSubject(): UserRepository {
        return DatabaseUserRepository(jpaUserRepository)
    }

    override fun buildUserEntityFactory(): UserEntityFactory {
        return UserEntityFactory(entityManager)
    }
}