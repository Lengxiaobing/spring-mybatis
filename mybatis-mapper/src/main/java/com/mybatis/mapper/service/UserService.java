package com.mybatis.mapper.service;

import com.mybatis.mapper.domain.UserInfo;

import java.util.List;

/**
 * @Description: UserService
 * @author: xiang
 * @date: 2019/6/19 18:14
 */
public interface UserService {

    /**
     * 根据id查询用户
     *
     * @param id 用户id
     * @return UserInfo
     * @throws Exception 抛出异常
     */
    UserInfo findUserById(int id) throws Exception;

    /**
     * 根据Name查询用户
     *
     * @param name
     * @return
     * @throws Exception
     */
    List<UserInfo> findUserByName(String name) throws Exception;

    /**
     * 添加用户信息
     *
     * @param userInfo 用户实体类
     * @return UserInfo
     * @throws Exception 抛出异常
     */
    void insertUserInfo(UserInfo userInfo) throws Exception;

    /**
     * 删除用户信息
     *
     * @param id 用户id
     * @return int
     * @throws Exception 抛出异常
     */
    void deleteUserInfo(int id) throws Exception;

    /**
     * 更新用户
     *
     * @param userInfo 用户实体类
     * @return int
     * @throws Exception 抛出异常
     */
    void updateUserInfo(UserInfo userInfo) throws Exception;

}
