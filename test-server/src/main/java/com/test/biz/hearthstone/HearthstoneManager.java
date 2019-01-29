package com.test.biz.hearthstone;

import com.test.aspect.annotation.RunTimeLog;
import com.test.dao.hearthstone.CardMapper;
import com.test.dao.hearthstone.entity.Card;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class HearthstoneManager {

    private SqlSession sqlSession;

    @Autowired
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    @RunTimeLog
    public void addCard(Card card) {
        CardMapper cardMapper = sqlSession.getMapper(CardMapper.class);
        cardMapper.addCard(card);
    }
}
