package com.test.dao.student;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentDao {

    private final SqlSession sqlSession;

    @Autowired
    public StudentDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void addStudent() throws Exception {
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        studentMapper.addStudent(444L, "test444");
        throw new Exception("test");
    }
}
