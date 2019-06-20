package com.mybatis.dao.service.impl;

import com.mybatis.dao.domain.UserInfo;
import com.mybatis.dao.service.UserService;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.List;

/**
 * @Description: UserServiceImpl
 * @author: xiang
 * @date: 2019/6/19 18:16
 */
public class UserServiceImpl implements UserService {

    /**
     * 通过构造方法注入sqlSessionFactory
     */
    private SqlSessionFactory sqlSessionFactory;

    public UserServiceImpl(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public UserInfo findUserById(int id) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserInfo userInfo = session.selectOne("user.findUserById", id);
        session.close();
        return userInfo;
    }

    public List<UserInfo> findUserByName(String name) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        List<UserInfo> list = session.selectList("user.findUserByName", name);
        session.close();
        return list;
    }

    public int insertUserInfo(UserInfo userInfo) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int insert = session.insert("user.insertUserInfo", userInfo);
        session.commit();
        session.close();
        return insert;
    }

    public int deleteUserInfo(int id) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int delete = session.delete("user.deleteUserInfo", id);
        session.commit();
        session.close();
        return delete;
    }

    public int updateUserInfo(UserInfo userInfo) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        int update = session.update("user.updateUserInfo", userInfo);
        session.close();
        return update;
    }
}
