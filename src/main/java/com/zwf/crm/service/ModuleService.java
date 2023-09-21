package com.zwf.crm.service;

import com.zwf.crm.base.BaseService;
import com.zwf.crm.dao.ModuleMapper;
import com.zwf.crm.dao.PermissionMapper;
import com.zwf.crm.pojo.Module;
import com.zwf.crm.pojo.ModuleModel;
import com.zwf.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mr Zeng
 * @version 1.0
 * @date 2023-09-15 14:36
 */
@Service
public class ModuleService extends BaseService<Module,Integer> {
     @Resource
    private ModuleMapper moduleMapper;

     @Resource
     private PermissionMapper permissionMapper;

   public List<ModuleModel> queryAllModuleName(Integer roleId){
       AssertUtil.isTrue(roleId==null,"系统异常！");
       //查询角色Id绑定的moduleId
     List<Integer> moduleIds=permissionMapper.queryAllModuleIdByRoleId(roleId);
     //所有的资源ID
       List<ModuleModel> moduleModels = moduleMapper.queryAllModel();
       if(moduleModels!=null&&moduleModels.size()>0){
           moduleModels.forEach(moduleModel -> { //遍历所有的moduleId 进行匹配
               if(moduleIds.contains(moduleModel.getId())){
                   moduleModel.setChecked(true); //选中用户绑定的资源
               }
           });
       }
      return moduleModels;
   }

    public Map<String, Object> queryAllModulesByTables() {
       Map<String,Object> maps=new HashMap<>();
       List<Module> modules=moduleMapper.queryAllModules();
       maps.put("code",0);
       maps.put("msg","");
       maps.put("data",modules);
       maps.put("count",modules.size());
       return maps;

    }

    /**
     *    需要添加  moduleName:不为null 同一层级唯一性
     *      parentId：必须存在 grade=0设置 -1 其他等级必须设置且必须存在的id
     *      grade：必须只有0 1 2三级 且 不为空
     *      url：grade=1 不为空 同一级的url是唯一
     *      optValue:权限码不为空且是唯一值
     *      设置默认值 isValid=1 有效
     *       更新时间  updateTime 当前时间
     *       创建时间  createTime  当前时间
     * @param module
     */
    @Transactional
    public void addModuleInfoData(Module module) {
        Integer grade=module.getGrade();
        AssertUtil.isTrue(null==grade,"层级关系不为空！");
        AssertUtil.isTrue(!(module.getGrade()==0||module.getGrade()==1||module.getGrade()==2),"层级关系不合法！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"模块名字不为空！");
        //同一级模块名不能重复
        AssertUtil.isTrue(null!=moduleMapper.queryModuleByNames(module.getModuleName(),grade),"模块名已存在！");
        //判断等级  最高级
        if(grade==0){
            module.setParentId(-1);
            module.setUrl(null);
        }else if (grade==1){
            AssertUtil.isTrue(module.getParentId()==null,"父级菜单不能为空！");
            AssertUtil.isTrue(moduleMapper.selectByPrimaryKey(module.getParentId())==null,"父级菜单不存在！");
            AssertUtil.isTrue(StringUtils.isBlank(module.getUrl()),"URL不能为空！");
            AssertUtil.isTrue(moduleMapper.selectModuleUrlByGrade(module.getUrl(),grade)!=null,"url已存在！");
        }else if(grade==2){
            AssertUtil.isTrue(module.getParentId()==null,"父级菜单不能为空！");
            AssertUtil.isTrue(moduleMapper.selectByPrimaryKey(module.getParentId())==null,"父级菜单不存在！");
            module.setUrl(null);
        }
        AssertUtil.isTrue(module.getOptValue()==null,"授权码不能为空！");
        AssertUtil.isTrue(moduleMapper.queryModuleByOptValue(module.getOptValue())!=null,"授权码已存在！");
        module.setIsValid((byte) 1);
        module.setCreateDate(new Date());
        module.setUpdateDate(new Date());
        //进行数据添加操作
        AssertUtil.isTrue(moduleMapper.insertSelective(module)<1,"菜单信息添加失败！");
    }

    /**
     * module模块的id不能为空
     * 同一级的菜单名不能重复 非空 排除自己
     * 同一级的url不能重复 非空  排除自己
     * 授权码不能为空 不能重复
     * 更新时间设置为当前时间
     * 层级不能为空  只能是0 1 2
     * @param module
     */
    @Transactional
    public void updateModuleData(Module module){
        AssertUtil.isTrue(null==module.getId(),"主键属性不能为空！");
        AssertUtil.isTrue(null==module.getGrade()||!(module.getGrade()==0||module.getGrade()==1||module.getGrade()==2),"菜单层级关系异常！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getModuleName()),"菜单名不能为空！");
       Module temp=moduleMapper.queryModuleByNames(module.getModuleName(), module.getGrade());
       AssertUtil.isTrue(temp!=null&&!(temp.getId().equals(module.getId())),"菜单名已被使用！");
        Module url = moduleMapper.selectModuleUrlByGrade(module.getUrl(), module.getGrade());
        AssertUtil.isTrue(url!=null&&!(url.getId().equals(module.getId())),"url已被使用！");
        AssertUtil.isTrue(StringUtils.isBlank(module.getOptValue()),"授权码不能为空！");
        Module opt = moduleMapper.queryModuleByOptValue(module.getOptValue());
        AssertUtil.isTrue(opt!=null&&!(opt.getId().equals(module.getId())),"授权码已被使用！");
        module.setUpdateDate(new Date());
        AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"菜单信息更新失败！");

    }

    /**
     * moduleId不能为空
     * moduleId 记录存在
     * 存在子菜单  不能删除  把moduleId当成parentId查询数量
     * 查询 授权表中 该moduleId是否绑定角色 如果绑定就删除绑定角色
     * 执行删除更新操作。
     * @param moduleId
     */
    @Transactional
    public void deleteModuleDataService(Integer moduleId) {
        AssertUtil.isTrue(null==moduleId,"待删除的记录不存在！");
        Module module = moduleMapper.selectByPrimaryKey(moduleId);
        AssertUtil.isTrue(null==module,"待删除的记录不存在！");
        //查询该主键是否有子菜单
        Integer count=moduleMapper.queryCountByParentId(moduleId);
       AssertUtil.isTrue(count>0,"该菜单资源存在子菜单，不能删除！");
       //查询该资源是否绑定角色
        Integer counts= permissionMapper.queryRolesCountByModuleId(moduleId);
       if(counts>0){
           permissionMapper.deleteDataByModuleId(moduleId);
       }
       module.setIsValid((byte) 0);
       module.setUpdateDate(new Date());
       AssertUtil.isTrue(moduleMapper.updateByPrimaryKeySelective(module)<1,"菜单资源删除失败！");
    }
}