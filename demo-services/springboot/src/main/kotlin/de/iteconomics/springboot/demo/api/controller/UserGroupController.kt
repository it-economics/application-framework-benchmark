package de.iteconomics.springboot.demo.api.controller

import de.iteconomics.springboot.demo.api.UserGroupReadDto
import de.iteconomics.springboot.demo.api.utility.mapToDomain
import de.iteconomics.springboot.demo.api.utility.mapToReadDto
import de.iteconomics.springboot.demo.domain.service.UserGroupService
import de.iteconomics.springboot.demo.exception.UserGroupNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This Controller contains all CRUD-Operations for User-Groups.
 */
@RestController
@RequestMapping(
    value = ["/usergroup"],
    produces = [MediaType.APPLICATION_JSON_VALUE]
)
internal class UserGroupController(
    private val userGroupService: UserGroupService
) {

    @GetMapping
    @Operation(
        summary = "Get all user groups",
        description = "Provides a List of all user groups"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully returned all user groups.",
            content = [Content(array = ArraySchema(schema = Schema(implementation = UserGroupReadDto::class)))]
        )
    )
    fun allGroups(): List<UserGroupReadDto> {
        return userGroupService.listAllUserGroups().map { mapToReadDto(it) }
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Get a specific User",
        description = "Returns a specific User"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "The user group with the given id was found and returned.",
            content = [Content(schema = Schema(implementation = UserGroupReadDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "The user group could not be found."
        )
    )
    fun userGroupById(@PathVariable("id") id: Int): UserGroupReadDto {
        return mapToReadDto(userGroupService.getUserGroup(id))
    }

    @PostMapping
    @Operation(
        summary = "Get a specific user group",
        description = "Returns a specific user group"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "The user group with the given id was created and returned.",
            content = [Content(schema = Schema(implementation = UserGroupReadDto::class))]
        ),
        ApiResponse(
            responseCode = "409",
            description = "The user group already exists."
        )
    )
    fun create(groupName: String): ResponseEntity<UserGroupReadDto> {
        val persistedGroup = mapToReadDto(userGroupService.createUserGroup(mapToDomain(groupName)))
        return ResponseEntity.status(CREATED).body(persistedGroup)
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Delete a user group",
        description = "Deletes the user group from the System and removes it from all users."
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "The user group was successfully deleted."
        ),
        ApiResponse(
            responseCode = "230",
            description = "The user group could not be deleted because it doesn't exist."
        )
    )
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        return try {
            userGroupService.deleteUserGroup(id)
            ResponseEntity.noContent().build()
        } catch (e: UserGroupNotFoundException) {
            ResponseEntity.status(230).build()
        }
    }
}
