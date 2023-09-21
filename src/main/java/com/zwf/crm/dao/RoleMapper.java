package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.Role;
import com.zwf.crm.query.RoleQuery;

import java.util.List;
import java.util.Map;

public interface RoleMapper extends BaseMapper<Role,Integer> {
    public List<Map<String,Object>> queryAllRoles(Integer userId);

     //多条件查询
    List<Role> selectByParams(RoleQuery roleQuery);

    //查询角色名是否存在
    Role selectRoleByName(String roleName);

}