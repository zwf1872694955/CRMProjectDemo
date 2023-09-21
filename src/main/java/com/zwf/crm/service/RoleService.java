package com.zwf.crm.service;

import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.RoleMapper;
import com.zwf.crm.pojo.Permission;
import com.zwf.crm.pojo.Role;
import com.zwf.crm.query.RoleQuery;
import com.zwf.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-14 13:57
 */
@Service
public class RoleService extends BaseService<Role,Integer> {
     @Resource
    private RoleMapper roleMapper;
     @Resource
     private PermissionService permissionService;

     @Resource
     private ModuleService moduleService;

     public List<Map<String,Object>> queryRolesService(Integer userId){
         return roleMapper.queryAllRoles(userId);

     }
    @Transactional(propagation = Propagation.REQUIRED)
     public void AddRoleService(Role role){
         //判断角色名不能为空
         AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空");
         //查询插入的角色名是否存在
         AssertUtil.isTrue(roleMapper.selectRoleByName(role.getRoleName())!=null,"角色名已存在！");
         role.setIsValid(1);
         role.setCreateDate(new Date());
         role.setUpdateDate(new Date());
         //添加角色信息
         AssertUtil.isTrue(roleMapper.insertSelective(role)<1,"用户信息添加失败！");
     }

    @Transactional(propagation = Propagation.REQUIRED)
    public void updateRoleService(Role role){
         AssertUtil.isTrue(role.getId()==null||roleMapper.selectByPrimaryKey(role.getId())==null,"待更新的用户信息不存在！");
         AssertUtil.isTrue(StringUtils.isBlank(role.getRoleName()),"角色名不能为空！");
         //角色名不能重复
        Role r1 = roleMapper.selectRoleByName(role.getRoleName());
         AssertUtil.isTrue(r1!=null&&!(role.getId()==r1.getId()),"用户名已存在！");
         //设置数据有效
        role.setIsValid(1);
        //设置更新时间
        role.setUpdateDate(new Date());
        //执行更新操作
        AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"用户信息更新失败！");

    }
     @Transactional(propagation=Propagation.REQUIRED)
    public void deleteRoleService(Integer roleId) {
         Role role = roleMapper.selectByPrimaryKey(roleId);
         AssertUtil.isTrue(role==null,"待删除的记录不存在！");
         role.setIsValid(0);
         role.setUpdateDate(new Date());
         AssertUtil.isTrue(roleMapper.updateByPrimaryKeySelective(role)<1,"用户信息删除失败！");
    }
      @Transactional(propagation = Propagation.REQUIRED)
    public void grantRoleToModuleService(Integer roleId, Integer[] moduleIds) {
         //查询角色是否绑定资源 如果绑定资源先删除所有的资源 再重新绑定
          AssertUtil.isTrue(roleId==null,"授权角色不存在！");
          //查询用户拥有的资源
        Integer count=permissionService.queryModuleCountByRoleId(roleId);
          if (count>0){
              //删除用户绑定的所有资源
              permissionService.deleteModuleByRoleId(roleId);
          }
          List<Permission> permissions=new ArrayList<>();
          //再重新绑定
          if(moduleIds!=null&&moduleIds.length>0){
              for(int i=0;i<moduleIds.length;i++){
                  Permission permission=new Permission();
                  permission.setRoleId(roleId);
                  permission.setModuleId(moduleIds[i]);
                  //存储当前资源对象的授权码
                  permission.setAclValue(moduleService.selectByPrimaryKey(moduleIds[i]).getOptValue());
                  permission.setCreateDate(new Date());
                  permission.setUpdateDate(new Date());
                  permissions.add(permission);
              }
              AssertUtil.isTrue(permissionService.insertBatch(permissions)!=moduleIds.length,"角色授权失败！");
          }


    }
}