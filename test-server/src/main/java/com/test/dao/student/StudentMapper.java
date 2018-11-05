package com.test.dao.student;

import com.test.dao.student.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StudentMapper {

    @Select("SELECT * FROM student")
    List<Student> findAllStudents();

    @Insert("INSERT INTO student VALUES (#{id}, #{name})")
    void addStudent(@Param("id") Long id, @Param("name") String name);
}
