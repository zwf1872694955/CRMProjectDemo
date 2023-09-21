package com.zwf.crm.service;

import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.PermissionMapper;
import com.zwf.crm.pojo.Permission;
import com.zwf.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-15 17:00
 */
@Service
public class PermissionService extends BaseService<Permission,Integer> {
     @Resource
    private PermissionMapper permissionMapper;

    public Integer queryModuleCountByRoleId(Integer roleId) {
      return  permissionMapper.queryModuleCountByRoleId(roleId);
    }

    public void deleteModuleByRoleId(Integer roleId) {
        permissionMapper.deleteModuleByRoleId(roleId);
    }

    public List<String> queryAclValuesByUserId(Integer useId) {
        AssertUtil.isTrue(useId==null,"系统异常！");
        return permissionMapper.queryAclValuesByUserId(useId);
    }
}