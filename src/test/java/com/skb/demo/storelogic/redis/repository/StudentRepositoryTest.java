package com.skb.demo.storelogic.redis.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.skb.demo.BaseRedisTest;
import com.skb.demo.domain.dto.StudentDto;
import com.skb.demo.storeLogic.redis.repo.StudentRepository;

public class StudentRepositoryTest extends BaseRedisTest {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void saveStudent_normal_success() throws Exception {
        final StudentDto student = new StudentDto("Eng2015003", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void updateStudent_normal_success() throws Exception {
        final StudentDto student = new StudentDto("Eng2015003", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        student.setName("Richard Watson");
        studentRepository.saveStudent(student);
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertEquals(student.getName(), retrievedStudent.getName());
    }
    
    @Ignore
    @Test
    public void findAllStudents_normal_success() throws Exception {
        final StudentDto engStudent = new StudentDto("Eng2015003", "John Doe", StudentDto.Gender.MALE, 1);
        final StudentDto medStudent = new StudentDto("Med2015003", "Gareth Houston", StudentDto.Gender.MALE, 2);
        studentRepository.saveStudent(engStudent);
        studentRepository.saveStudent(medStudent);
        final Map<Object, Object> retrievedStudent = studentRepository.findAllStudents();
        assertEquals(retrievedStudent.size(), 2);
    }

    @Test
    public void deleteStudent_normal_success() throws Exception {
        final StudentDto student = new StudentDto("Eng2015003", "John Doe", StudentDto.Gender.MALE, 1);
        studentRepository.saveStudent(student);
        studentRepository.deleteStudent(student.getId());
        final StudentDto retrievedStudent = studentRepository.findStudent(student.getId());
        assertNull(retrievedStudent);
    }
}
