package ru.q2l3ntk.api.students.controller

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import ru.q2l3ntk.api.students.model.CommonUser
import ru.q2l3ntk.api.students.model.Student
import ru.q2l3ntk.api.students.service.AppService
import ru.q2l3ntk.api.students.service.CommonUserDetailsService

@RestController
@RequestMapping("/api/v1/students")
class AppController {
    @Qualifier("appServiceImpl")
    @Autowired //Outdated
    private lateinit var service: AppService

    @Autowired
    private lateinit var userService: CommonUserDetailsService

    @GetMapping
    fun findAllStudents(): List<Student> = service.findAllStudents()

    @PostMapping("save_student")
    @PreAuthorize("hasRole('ADMIN')")
    fun saveStudent(@RequestBody student: Student): String {
        service.saveStudent(student)
        return "Student successfully saved"
    }

    @GetMapping("/{email}")
    fun findByEmail(@PathVariable email: String): Student? = service.findByEmail(email)

    @PutMapping("update_student")
    @PreAuthorize("hasRole('ADMIN')")
    fun updateStudent(@RequestBody student: Student): Student? = service.updateStudent(student)

    @DeleteMapping("delete_student/{email}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    fun deleteStudent(@PathVariable email: String) { service.deleteStudent(email) }

    @PostMapping("/new-user")
    fun addUser(@RequestBody user: CommonUser): String {
        userService.addUser(user)
        return "New user successfully added"
    }
}