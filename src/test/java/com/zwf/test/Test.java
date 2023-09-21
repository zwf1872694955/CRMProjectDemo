package com.zwf.test;

import com.zwf.crm.SpringBootApplicationStarter;
import com.zwf.crm.dao.UserMapper;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-12 14:52
 */
//使用@RunWith(SpringRunner.class) 表示使用spring测试类,前提要导入junit依赖
//springBootTest加载SpringBoot启动类
@SpringBootTest(classes = SpringBootApplicationStarter.class)
public class Test {
    @Resource
    private UserMapper userMapper;
    @org.junit.jupiter.api.Test
    public void testAssignMan(){
        List<Map<String, Object>> maps = userMapper.queryAllAssignMan();
        System.out.println(maps.toString());
    }
}