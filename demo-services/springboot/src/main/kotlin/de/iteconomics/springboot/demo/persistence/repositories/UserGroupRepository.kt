package de.iteconomics.springboot.demo.persistence.repositories

import de.iteconomics.springboot.demo.persistence.entity.UserGroupEntity
import org.springframework.data.jpa.repository.JpaRepository

internal interface UserGroupRepository : JpaRepository<UserGroupEntity, Int>
