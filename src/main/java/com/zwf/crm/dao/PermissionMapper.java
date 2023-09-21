package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.Module;
import com.zwf.crm.pojo.Permission;

import java.util.List;

public interface PermissionMapper extends BaseMapper<Permission,Integer> {

    Integer queryModuleCountByRoleId(Integer roleId);

    void deleteModuleByRoleId(Integer roleId);

    List<Integer> queryAllModuleIdByRoleId(Integer roleId);

    List<String> queryAclValuesByUserId(Integer useId);

    Integer queryRolesCountByModuleId(Integer moduleId);

    Integer deleteDataByModuleId(Integer moduleId);
}