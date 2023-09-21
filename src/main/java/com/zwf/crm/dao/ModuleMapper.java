package com.zwf.crm.dao;

import com.zwf.crm.base.BaseMapper;
import com.zwf.crm.pojo.Module;
import com.zwf.crm.pojo.ModuleModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMapper extends BaseMapper<Module,Integer> {
    List<ModuleModel> queryAllModel();

    List<Module> queryAllModules();

    Module queryModuleByNames(@Param("moduleName") String moduleName,@Param("grade") Integer grade);

    Module queryModuleByOptValue(String optValue);

    Module selectModuleUrlByGrade(@Param("url") String url,@Param("grade") Integer grade);

    Integer queryCountByParentId(Integer moduleId);

}