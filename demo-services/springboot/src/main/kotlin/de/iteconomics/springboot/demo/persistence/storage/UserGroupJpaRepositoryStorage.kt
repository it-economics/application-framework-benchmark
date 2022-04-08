package de.iteconomics.springboot.demo.persistence.storage

import de.iteconomics.springboot.demo.domain.UserGroup
import de.iteconomics.springboot.demo.exception.UserGroupNotFoundException
import de.iteconomics.springboot.demo.persistence.UserGroupStorage
import de.iteconomics.springboot.demo.persistence.repositories.UserGroupRepository
import de.iteconomics.springboot.demo.persistence.utility.mapToDomain
import de.iteconomics.springboot.demo.persistence.utility.mapToEntity
import org.springframework.stereotype.Service

@Service
internal class UserGroupJpaRepositoryStorage(
    private val userGroupRepository: UserGroupRepository
) : UserGroupStorage {

    override fun list(): List<UserGroup> =
        userGroupRepository.findAll().map { mapToDomain(it) }

    override fun get(id: Int): UserGroup =
        mapToDomain(findEntity(id))

    override fun persist(userGroup: UserGroup): UserGroup =
        mapToDomain(userGroupRepository.save(mapToEntity(userGroup)))

    override fun delete(id: Int) =
        userGroupRepository.delete(findEntity(id))

    private fun findEntity(id: Int) =
        userGroupRepository.findById(id)
            .orElseThrow { UserGroupNotFoundException("UserGroup with id '$id' not found.") }
}
