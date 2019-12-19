package com.ron.mapper;

import com.ron.entity.SystemModule;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @auther Ron
 * @data 2019/12/09
 */
@Mapper
public interface SystemModuleMapper {

    /**
     * 添加模块信息
     *
     * @param systemModule 模块实体
     * @return int
     */
    int addModule(SystemModule systemModule);

    /**
     * 编辑模块信息
     *
     * @param systemModule 模块实体
     * @return int
     */
    int editModule(SystemModule systemModule);

    /**
     * 删除模块信息
     *
     * @param moduleId 模块ID
     * @return int
     */
    int deleteModule(Integer moduleId);

    /**
     * 删除下级模块信息
     *
     * @param parentId 上级模块id
     * @return int
     */
    int deleteChildModule(@Param("parentId") Integer parentId);

    /**
     * 查询某个模块信息
     *
     * @param moduleId
     * @return SystemModule
     */
    SystemModule getModule(Integer moduleId);

    /**
     * 查询所有模块信息
     *
     * @return List
     */
    List<SystemModule> getAllModuleList();

    /**
     *  查询所有模块信息
     *
     *  @return List
     */
    List<SystemModule> getModuleList();

    /**
     * 查询模块的子模块
     *
     * @param parentId 父模块id
     * @return List
     */
    List<SystemModule> getChildModuleList(@Param("parentId") Integer parentId);

    /**
     * 检测模块是否存在
     *
     * @param moduleName 模块名称
     * @param parentId 上级部门ID
     * @return
     */
    SystemModule checkModule(@Param("moduleName") String moduleName, @Param("parentId") Integer parentId);

}
