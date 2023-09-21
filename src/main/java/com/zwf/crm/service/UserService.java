package com.zwf.crm.service;

import com.zwf.crm.base.BaseService;
import com.zwf.crm.base.ResultInfo;
import com.zwf.crm.dao.UserMapper;
import com.zwf.crm.dao.UserRoleMapper;
import com.zwf.crm.pojo.User;
import com.zwf.crm.pojo.UserRole;
import com.zwf.crm.utils.AssertUtil;
import com.zwf.crm.utils.Md5Util;
import com.zwf.crm.utils.PhoneUtil;
import com.zwf.crm.utils.UserIDBase64;
import com.zwf.crm.vo.UserModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
 * @date 2023-09-09 14:51
 */
@Service
public class UserService extends BaseService<User,Integer> {
    @Autowired
    private UserMapper userMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
     //处理登录业务
    public ResultInfo LoginService(String username,String password){
        ResultInfo resultInfo=new ResultInfo();
        //判断username和password是否为空
        checkParams(username,password);
        //根据用户名查询用户对象是否存 不正确抛出参数异常
        User user = userMapper.queryUserByName(username);
        AssertUtil.isTrue(null==user,"用户不存在！");
        //查询密码是否正确 不正确抛出参数异常
        checkPasswordMethod(user, password);
        //登录成功后
        UserModel um=new UserModel();
        //对UserId进行加密
        um.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));
        um.setUserName(user.getUserName());
        um.setTrueName(user.getTrueName());
        resultInfo.setResult(um);
        return resultInfo;
    }

    private void checkParams(String username, String password) {
        AssertUtil.isTrue(StringUtils.isBlank(username),"用户名不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(password),"密码不能为空！");
    }

    private void checkPasswordMethod(User user,String password){
        //加密后的密码
        String pwd = Md5Util.encode(password);
        //进行密码匹配
        AssertUtil.isTrue(!(pwd.equals(user.getUserPwd())),"密码输入有误！");
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public ResultInfo updatePasswordService(Integer userId,String oldPwd,String newPwd,String repeatPwd){
        AssertUtil.isTrue(userId==null,"用户Id不能为空！");
        //查询用户是否存在
        User user = userMapper.selectByPrimaryKey(userId);
        AssertUtil.isTrue(null==user,"待更新记录不存在！");
        //原始密码是否与查询密码一致
        AssertUtil.isTrue(StringUtils.isBlank(oldPwd),"原始密码不能为空！");
        AssertUtil.isTrue(!Md5Util.encode(oldPwd).equals(user.getUserPwd()),"原始密码输入有误！");
        //新密码与原始密码不能一致
        AssertUtil.isTrue(StringUtils.isBlank(newPwd),"新密码不能为空！");
        AssertUtil.isTrue(newPwd.equals(oldPwd),"新密码不能与原始密码一致！");
        //确认密码要与新密码一致
        AssertUtil.isTrue(StringUtils.isBlank(repeatPwd),"确认密码不能为空！");
        AssertUtil.isTrue(!newPwd.equals(repeatPwd),"确认密码与新密码不一致！");
        user.setUserPwd(Md5Util.encode(newPwd));
        user.setId(userId);
        //修改密码操作
        Integer row = userMapper.updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(row<1,"密码修改失败！");
        //满足以上条件默认就是成功！
        ResultInfo resultInfo=new ResultInfo();
        return resultInfo;
    }


     public List<Map<String,Object>> queryAssignManName(){

       return userMapper.queryAllAssignMan();
     }
    /**
     * 用户名不能为空  是唯一的
     * 邮箱地址不能为空
     * 手机号不能为空 符合手机号格式
     * 设置数据有效  更新时间、创建时间为当时。
     * 密码设置为默认值123456  MD5盐值加密
     * 插入数据 返回受影响行数不能小于0
     */
    @Transactional(propagation = Propagation.REQUIRED)
     public void insertUserInfoService(User user){
         checkUserParams(user);
          //设置默认值
         user.setIsValid(1);
         user.setUpdateDate(new Date());
         user.setCreateDate(new Date());
         //设置密码
         user.setUserPwd(Md5Util.encode("123456"));
         //进行数据插入
         Integer row = userMapper.insertSelective(user);
         AssertUtil.isTrue(row<1,"用户添加失败！");
         //保存用户数据返回主键字段值  把前台得到的角色ID和用户ID进行绑定
        grantRoleToUser(user.getId(),user.getRoleIds());

     }
       //用户与角色进行绑定
    private void grantRoleToUser(Integer id, String roleIds) {
           AssertUtil.isTrue(id==null,"数据库主键返回异常");
           //查询t_user_role表中的用户id对应的roleId否存在
        Integer row = userRoleMapper.queryRoleCountByUserId(id);
           //如果该用户绑定了角色  需要全部删除
        if(row>0){
            userRoleMapper.deleteRoleByUserId(id);
        }
        List<UserRole> list=new ArrayList<>();
        //进行角色Id绑定
        if(!StringUtils.isBlank(roleIds)){
            String[] split = roleIds.split(",");
            for (int i=0;i<split.length;i++){
                UserRole userRole=new UserRole();
                userRole.setRoleId(Integer.valueOf(split[i]));
                userRole.setUserId(id);
                userRole.setUpdateDate(new Date());
                userRole.setCreateDate(new Date());
                list.add(userRole);
            }
            //进行对象批量插入
            Integer count = userRoleMapper.insertBatch(list);
            AssertUtil.isTrue(count!=list.size(),"用户角色更新失败！");
        }

    }

    private void checkUserParams(User user) {
         //用户名验证
        AssertUtil.isTrue(StringUtils.isBlank(user.getUserName()),"用户名不能为空！");
        User u = userMapper.queryUserByName(user.getUserName());
        AssertUtil.isTrue(u!=null,"用户名已被使用！");
        //邮箱地址验证
        AssertUtil.isTrue(StringUtils.isBlank(user.getEmail()),"邮箱地址不能为空！");
        //手机号验证
        AssertUtil.isTrue(StringUtils.isBlank(user.getPhone()),"手机号不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(user.getPhone()),"手机号格式错误！");

    }
    //更新用户信息
    //检查用户名 手机号 邮箱地址  ID是否为空
    //设置更新时间为现在的时间
    //进行更新数据操作
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserInfoService(User user){
        grantRoleToUser(user.getId(),user.getRoleIds());
         checkUpdateUserParams(user.getId(),user.getUserName(),user.getEmail(),user.getPhone());
         user.setUpdateDate(new Date());
        Integer row = userMapper.updateByPrimaryKeySelective(user);
        AssertUtil.isTrue(row<1,"用户信息更新失败！");

    }

    private void checkUpdateUserParams(Integer id, String userName, String email, String phone) {
        AssertUtil.isTrue(null==id,"待更新数据不存在！");
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        User user = userMapper.queryUserByName(userName);
        AssertUtil.isTrue(user!=null&&!(id==user.getId()),"用户名已存在！");
        AssertUtil.isTrue(StringUtils.isBlank(email),"邮箱地址不能为空！");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空！");
        AssertUtil.isTrue(!PhoneUtil.isMobile(phone),"手机号格式错误！");

    }
     @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserInfoByIdsService(Integer[] ids) {
        AssertUtil.isTrue(ids==null||ids.length==0,"待删除的记录不存在！");
        Integer row = deleteBatch(ids);
        AssertUtil.isTrue(row!=ids.length,"用户数据删除失败！");
         //删除用户角色关联数据
        if(ids.length!=0||ids!=null){
            for (int i=0;i<ids.length;i++){
                Integer count = userRoleMapper.queryRoleCountByUserId(ids[i]);
                if(count>0){
                    Integer delCount = userRoleMapper.deleteRoleByUserId(ids[i]);
                    AssertUtil.isTrue(delCount<1,"用户角色数据删除失败");
                }
            }
        }

    }
}