package com.skb.demo.storeLogic.redis.repo;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.skb.demo.domain.dto.StudentDto;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static final String KEY = "Student";

    private RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public StudentRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public void saveStudent(final StudentDto student) {
        hashOperations.put(KEY, student.getId(), student);
    }

    public void updateStudent(final StudentDto student) {
        hashOperations.put(KEY, student.getId(), student);
    }

    public StudentDto findStudent(final String id) {
        return (StudentDto) hashOperations.get(KEY, id);
    }

    public Map<Object, Object> findAllStudents() {
        return hashOperations.entries(KEY);
    }

    public void deleteStudent(final String id) {
        hashOperations.delete(KEY, id);
    }
}
