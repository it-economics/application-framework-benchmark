package de.iteconomics.springboot.demo.persistence

import de.iteconomics.springboot.demo.domain.Subject
import de.iteconomics.springboot.demo.exception.SubjectNotFoundException

/**
 * Implementations if this Storage interface serve functions to manage all information in the persistence layer.
 */
internal interface SubjectStorage {

    /**
     * Lists all Subjects as they are stored.
     *
     * @return A list of Subjects or an empty list if none are stored.
     */
    fun list(): List<Subject>

    /**
     * Reads a specific Subject from the Storage.
     *
     * @param id ID of the sought Subject.
     * @return the sought Subject.
     * @throws SubjectNotFoundException if the sough Subject does not exist.
     */
    fun get(id: Int): Subject

    /**
     * Searches for a Subject with the given username.
     *
     * @param username Username of the sought Subject
     * @return the sought Subject.
     * @throws SubjectNotFoundException if the sough Subject does not exist.
     */
    fun getByName(username: String): Subject

    /**
     * Stores the given Subject.
     *
     * @return a modified Subject with a new ID.
     */
    fun persist(subject: Subject): Subject

    /**
     * Deletes the Subject with the given ID from the Storage.
     *
     * * @throws SubjectNotFoundException if the sough Subject does not exist.
     */
    fun delete(id: Int)
}
