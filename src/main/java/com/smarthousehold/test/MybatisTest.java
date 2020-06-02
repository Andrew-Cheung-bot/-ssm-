package com.smarthousehold.test;

import com.smarthousehold.mapper.CurtainMapper;
import com.smarthousehold.pojo.Data_Curtain;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

public class MybatisTest {

    @Test
    public void run1() throws IOException {
        InputStream in = Resources.getResourceAsStream("Sqlconfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(in);
        SqlSession sqlSession = build.openSession();
        CurtainMapper mapper = sqlSession.getMapper(CurtainMapper.class);
        Data_Curtain data= new Data_Curtain();

        //data.setSettime("4-12-5");
        //data.setTem(26.2f);
        //data.setHumidity(23.5f);
        mapper.addCurtainData(data);
        sqlSession.commit();
       /* List<Data_Curtain> allData = mapper.getAllData();
        System.out.println(allData);*/
        /*Data_Curtain data = mapper.getData("12-2-2");
        System.out.println(data);*/

       /* Curtain curtain = mapper.getCurtain("curtain");
        System.out.println(curtain);*/
        /*Curtain curtain = new Curtain();
        curtain.setPojo("curtain");
        curtain.setStem(20.2f);
        curtain.setShumidity(20.5f);
        mapper.updateCurtain(curtain);
        sqlSession.commit();*/
    }
}
