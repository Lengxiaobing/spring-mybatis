package com.mybatis.mapper.service.impl;

import com.mybatis.mapper.domain.UserInfo;
import com.mybatis.mapper.service.UserService;
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
        UserService mapper = session.getMapper(UserService.class);
        UserInfo userInfo = mapper.findUserById(id);
        session.close();
        return userInfo;
    }

    public List<UserInfo> findUserByName(String name) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        List<UserInfo> list = mapper.findUserByName(name);
        session.close();
        return list;
    }

    public void insertUserInfo(UserInfo userInfo) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        mapper.insertUserInfo(userInfo);
        session.close();
    }

    public void deleteUserInfo(int id) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        mapper.deleteUserInfo(id);
        session.close();
    }

    public void updateUserInfo(UserInfo userInfo) throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        mapper.updateUserInfo(userInfo);
        session.close();
    }
}
