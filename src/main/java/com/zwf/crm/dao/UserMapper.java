package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.User;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
public interface UserMapper extends BaseMapper<User,Integer> {
    User queryUserByName(String userName);
    //查询销售人员的assignManId和用户名
    List<Map<String,Object>> queryAllAssignMan();
}