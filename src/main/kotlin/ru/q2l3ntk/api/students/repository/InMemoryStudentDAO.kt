package ru.q2l3ntk.api.students.repository

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Repository
import ru.q2l3ntk.api.students.model.Student
import java.util.ArrayList
import java.util.stream.IntStream

@Repository
class InMemoryStudentDAO {
    private var STUDENTS: MutableList<Student> = ArrayList()

    fun findAllStudents(): List<Student> {
        return STUDENTS
    }

    fun saveStudent(student: Student): Student {
        STUDENTS.add(student)
        return student
    }

    fun findByEmail(email:String) = runBlocking {
        return@runBlocking STUDENTS.asFlow().filter { element -> element.getEmail().equals(email) }.firstOrNull()
    }

    fun updateStudent(student: Student): Student? {
        // This index was found by IntStream and this is not recommended. There is a way to do it with coroutines
        var studentIndex = IntStream.range(0, STUDENTS.size - 1)
            .filter { index -> STUDENTS.get(index).getEmail().equals(student.getEmail()) }
            .findFirst()
            .orElse(-1)

        if (studentIndex > -1) {
            STUDENTS.set(studentIndex, student)
            return student
        }

        return null
    }

    fun deleteStudent(email: String) {
        val student = findByEmail(email)

        if (student != null) {
            STUDENTS.remove(student)
        }
    }
}