package com.test;

import com.test.dao.student.StudentDao;
import com.test.dao.student.StudentMapper;
import com.test.dao.student.entity.Student;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestApplicationTests {

    @Autowired
    private SqlSession sqlSession;
    @Autowired
    private StudentDao studentDao;

    @Test
    public void contextLoads() {
    }

    @Test
    public void testFindAllStudents() {
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.findAllStudents();
        Optional.of(students).ifPresent(item -> item.forEach(System.out::println));
    }

    @Test
    public void testAddStudent1() {
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        studentMapper.addStudent(253L, "test253");
    }

    @Test
    public void testAddStudent2() throws Exception {
        studentDao.addStudent();
    }
}
