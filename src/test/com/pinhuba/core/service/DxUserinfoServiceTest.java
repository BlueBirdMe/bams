package com.pinhuba.core.service;

import com.pinhuba.core.daoimpl.DxUserinfoDaoImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/3/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
/** 注入相关的配置文件：可以写入多个配置文件 **/
@ContextConfiguration(locations={"classpath:springconf/spring-service.xml",
        "classpath:springconf/spring-config-web.xml",
        "classpath:springconf/spring-servlet.xml",
})
public class DxUserinfoServiceTest {
    @Resource
    private DxUserinfoService dus;

    @Test
    public void countUserByDept_Age() throws Exception {
        List<Object[]> list = dus.countUserByDept_Age("20","40","采购物流支撑中心");
        for(Object[] obj:list){
            for(int i=0;i<obj.length;i++){
                System.out.println(obj[0]+"\t"+obj[1]);
            }
        }
    }

}