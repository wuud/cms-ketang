package cn.henau.cms.utils;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MybatisUtil {

    public static <T> T getSession(Class<T> clazz) {
        InputStream inputStream;
        SqlSession session = null;
        T t = null;
        try {
            inputStream = Resources.getResourceAsStream("mybatis-config.xml");
            SqlSessionFactory ssf = new SqlSessionFactoryBuilder().build(inputStream);
            session = ssf.openSession();
            t = session.getMapper(clazz);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//			session.close();
        }
        return t;
    }

}
