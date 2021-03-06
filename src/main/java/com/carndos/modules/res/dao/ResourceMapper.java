package com.carndos.modules.res.dao;

import com.carndos.modules.res.entity.SysResource;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@org.apache.ibatis.annotations.Mapper
public interface ResourceMapper extends Mapper<SysResource> {
    /**
     * @param sysResource 需要插入的资源信息
     * @return Integer
     */
    Integer insertNewResource(SysResource sysResource);

    /**
     * @return List<SysResource>
     */
    List<SysResource> queryResourceOrderById();
}