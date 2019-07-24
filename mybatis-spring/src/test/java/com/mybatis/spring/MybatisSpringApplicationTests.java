package com.mybatis.spring;

import com.mybatis.spring.domain.UserInfo;
import com.mybatis.spring.service.UserService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MybatisSpringApplicationTests {

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
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        UserInfo userInfo = mapper.findUserById(1);
        session.close();
        System.out.println(userInfo.getName());
    }

    /**
     * 模糊查询测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testQueryLike() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        List<UserInfo> list = mapper.findUserByName("明");
        session.close();
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).getName());
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
        UserInfo user = new UserInfo();
        user.setName("代理");
        user.setBirth("19940113");
        user.setSex("男");
        user.setMobile("18978985632");
        user.setMail("123456@qq.com");

        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        mapper.insertUserInfo(user);
        session.commit();
        session.close();
        System.out.println("user_id:" + user.getId());
    }

    /**
     * 删除测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testDelete() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        mapper.deleteUserInfo(5);
        session.commit();
        session.close();
    }

    /**
     * 更新测试
     *
     * @throws Exception 抛出异常
     */
    @Test
    public void testUpdate() throws Exception {
        SqlSession session = sqlSessionFactory.openSession();
        UserService mapper = session.getMapper(UserService.class);
        UserInfo user = mapper.findUserById(1);
        user.setName("mapper");
        mapper.updateUserInfo(user);
        session.commit();
        session.close();
    }

}
