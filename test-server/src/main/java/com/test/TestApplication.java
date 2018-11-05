package com.test;

import com.test.dao.student.StudentMapper;
import com.test.dao.student.entity.Student;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.List;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) throws Exception {
        ConfigurableApplicationContext context = SpringApplication.run(TestApplication.class, args);
        DataSource ds = context.getBean(DataSource.class);
        System.out.println(ds.getClass().getName());
        Connection connection = ds.getConnection();
        System.out.println(connection.getCatalog());
        System.out.println(context.getBean(JdbcTemplate.class));
        connection.close();

        System.out.println("------------------------------------");
        SqlSession sqlSession = context.getBean(SqlSession.class);
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> students = studentMapper.findAllStudents();
        Optional.of(students).ifPresent(item -> item.forEach(System.out::println));

        System.out.println("------------------------------------");
        log.error("test error log msg...");
    }
}
