package com.zwf.crm.query;

import com.zwf.crm.base.BaseQuery;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-14 20:30
 */
//多条件分页查询
public class RoleQuery extends BaseQuery {

    private String  roleName;  //根据角色名查询角色数据

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}