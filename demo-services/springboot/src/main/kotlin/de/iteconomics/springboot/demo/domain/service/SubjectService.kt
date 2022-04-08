package de.iteconomics.springboot.demo.domain.service

import de.iteconomics.springboot.demo.domain.Subject
import de.iteconomics.springboot.demo.domain.UserGroup
import de.iteconomics.springboot.demo.persistence.SubjectStorage
import org.springframework.stereotype.Service
import javax.transaction.Transactional

/**
 * Instances of this class serve all functionality regarding the Subject.
 *
 * Its purpose is to encapsulate the logic behind specific features in function
 * which have the name of the implemented feature.
 * This should make it recognizable where to look first.
 */
@Service
@Transactional
internal class SubjectService(
    private val subjectStorage: SubjectStorage
) {

    /**
     * Stores the user in the database.
     *
     * @param The Subject representing the user that is to be registered. Its id will be overwritten.
     * @return The Subject as it was written to the storage, with a new id.
     */
    fun create(subject: Subject): Subject =
        subjectStorage.persist(subject)

    fun listAllSubjects(): List<Subject> = subjectStorage.list()

    fun getSubject(id: Int): Subject = subjectStorage.get(id)

    fun getSubjectByName(username: String): Subject = subjectStorage.getByName(username)

    fun assignGroupToSubject(id: Int, userGroup: UserGroup): Subject {
        val subject = subjectStorage.get(id)

        return subjectStorage.persist(subject.copy(groups = subject.groups + userGroup))
    }

    fun delete(id: Int) = subjectStorage.delete(id)
}
