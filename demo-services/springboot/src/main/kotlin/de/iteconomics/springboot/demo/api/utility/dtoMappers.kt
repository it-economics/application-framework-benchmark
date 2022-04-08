package de.iteconomics.springboot.demo.api.utility

import de.iteconomics.springboot.demo.api.SubjectReadDto
import de.iteconomics.springboot.demo.api.SubjectWriteDto
import de.iteconomics.springboot.demo.api.UserGroupReadDto
import de.iteconomics.springboot.demo.api.UserGroupWriteDto
import de.iteconomics.springboot.demo.domain.Subject
import de.iteconomics.springboot.demo.domain.UserGroup

internal fun mapToReadDto(subject: Subject): SubjectReadDto =
    SubjectReadDto(
        sub = subject.id,
        username = subject.username,
        groups = subject.groups.map { mapToReadDto(it) }.toSet()
    )

internal fun mapToReadDto(userGroup: UserGroup): UserGroupReadDto =
    UserGroupReadDto(
        id = userGroup.id,
        name = userGroup.name
    )

internal fun mapToDomain(subject: SubjectWriteDto) =
    Subject(
        id = -1,
        username = subject.username,
        password = subject.password,
        groups = emptySet()
    )

internal fun mapToDomain(userGroup: UserGroupWriteDto) =
    UserGroup(
        id = userGroup.id,
        name = userGroup.name
    )

internal fun mapToDomain(userGroup: String) =
    UserGroup(
        id = -1,
        name = userGroup
    )
