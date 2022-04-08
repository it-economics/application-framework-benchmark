package de.iteconomics.springboot.demo.api.controller

import de.iteconomics.springboot.demo.api.SubjectReadDto
import de.iteconomics.springboot.demo.api.SubjectWriteDto
import de.iteconomics.springboot.demo.api.UserGroupWriteDto
import de.iteconomics.springboot.demo.api.utility.mapToDomain
import de.iteconomics.springboot.demo.api.utility.mapToReadDto
import de.iteconomics.springboot.demo.domain.service.SubjectService
import de.iteconomics.springboot.demo.exception.SubjectNotFoundException
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * This REST controller contains all direct interactions with a Subject, that
 * includes its crud operations as well as authorization.
 */
@RestController
@RequestMapping(
    value = [SubjectController.PATH],
    produces = [APPLICATION_JSON_VALUE]
)
internal class SubjectController(
    private val subjectService: SubjectService,
) {

    companion object {
        const val PATH = "/subject"
    }

    // TODO verify the security stuff (check if it works)

    @GetMapping
    @Operation(
        summary = "Get all Users",
        description = "Provides a List of all Users"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "Successfully returned all Users.",
            content = [Content(array = ArraySchema(schema = Schema(implementation = SubjectReadDto::class)))]
        )
    )
    fun allUsers(): List<SubjectReadDto> {
        return subjectService.listAllSubjects().map { mapToReadDto(it) }
    }

    @GetMapping("{id}")
    @Operation(
        summary = "Get a specific User",
        description = "Returns a specific User"
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "The User with the given id was found and returned.",
            content = [Content(schema = Schema(implementation = SubjectReadDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "The user could not be found."
        )
    )
    fun userById(@PathVariable("id") id: Int): SubjectReadDto {
        return mapToReadDto(subjectService.getSubject(id))
    }

    @PostMapping
    @Operation(
        summary = "Register a new User",
        description = "The new User is persisted in the database."
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "201",
            description = "The User was successfully created.",
            content = [Content(schema = Schema(implementation = SubjectReadDto::class))]
        ),
        ApiResponse(
            responseCode = "409",
            description = "The user could not be created because it already exists"
        )
    )
    fun create(@RequestBody subject: SubjectWriteDto): ResponseEntity<SubjectReadDto> {
        val persistedSubject = mapToReadDto(subjectService.create(mapToDomain(subject)))
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(persistedSubject)
    }

    @PostMapping("{id}/groups")
    @Operation(
        summary = "Assign to Group",
        description = "Assigns the user to a specified group, the group has to be persisted beforehand."
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "200",
            description = "The User was modified and the modified version of the user was returned.",
            content = [Content(schema = Schema(implementation = SubjectReadDto::class))]
        ),
        ApiResponse(
            responseCode = "404",
            description = "The user or user group could not be found."
        )
    )
    fun assignGroup(@PathVariable("id") id: Int, @RequestBody group: UserGroupWriteDto): SubjectReadDto {
        // TODO consider accepting a set of names or ids instead of write dto
        return mapToReadDto(subjectService.assignGroupToSubject(id, mapToDomain(group)))
    }

    @DeleteMapping("{id}")
    @Operation(
        summary = "Delete a User",
        description = "Deletes the User from the System."
    )
    @ApiResponses(
        ApiResponse(
            responseCode = "204",
            description = "The User was successfully deleted."
        ),
        ApiResponse(
            responseCode = "230",
            description = "The user could not be deleted because it doesn't exist."
        )
    )
    fun delete(@PathVariable("id") id: Int): ResponseEntity<Any> {
        return try {
            subjectService.delete(id)
            ResponseEntity.noContent().build()
        } catch (e: SubjectNotFoundException) {
            ResponseEntity.status(230).build()
        }
    }

}
