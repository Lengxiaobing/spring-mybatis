package com.mybatis.dao;

import com.mybatis.dao.domain.UserInfo;
import com.mybatis.dao.service.UserService;
import com.mybatis.dao.service.impl.UserServiceImpl;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisDaoApplicationTests {

    /**
     * 会话工厂
     */
    private SqlSessionFactory sqlSessionFactory;


    /**
     * 执行前设置mybatis
     */
    @Before
    public void setUp() {
        try {
            String myBatisResource = "mybatis/mybatis-config.xml";
            InputStream inputStream = Resources.getResourceAsStream(myBatisResource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testQuery() throws Exception {
        UserService userDao = new UserServiceImpl(sqlSessionFactory);
        UserInfo user = userDao.findUserById(1);
        System.out.println(user.getName());
    }

    /**
     * 模糊查询测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testQueryLike() throws Exception {
        UserService userDao = new UserServiceImpl(sqlSessionFactory);
        List<UserInfo> user = userDao.findUserByName("明");
        for (int i = 0; i < user.size(); i++) {
            System.out.println(user.get(i).getName());
        }
    }

    /**
     * 插入测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testInsert() throws Exception {
        //创建UserDao对象
        UserService userDao = new UserServiceImpl(sqlSessionFactory);
        UserInfo user = new UserInfo();
        user.setName("DannyHoo");
        user.setBirth("19940113");
        user.setSex("男");
        user.setMobile("18978985632");
        user.setMail("123456@qq.com");
        int info = userDao.insertUserInfo(user);
        System.out.println(info);
    }

    /**
     * 删除测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testDelete() throws Exception {
        UserService userDao = new UserServiceImpl(sqlSessionFactory);
        userDao.deleteUserInfo(5);
    }

    /**
     * 更新测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testUpdate() throws Exception {
        UserService userDao = new UserServiceImpl(sqlSessionFactory);
        UserInfo user = userDao.findUserById(1);
        user.setName("dao");
        userDao.updateUserInfo(user);
    }

}
