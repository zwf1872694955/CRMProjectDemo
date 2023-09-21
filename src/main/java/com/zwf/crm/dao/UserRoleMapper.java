package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.UserRole;

public interface UserRoleMapper extends BaseMapper<UserRole,Integer> {
      //根据用户ID查询 是否有绑定的角色
     Integer queryRoleCountByUserId(Integer userId);
     //删除用户ID中所有的角色绑定
    Integer deleteRoleByUserId(Integer userId);


}