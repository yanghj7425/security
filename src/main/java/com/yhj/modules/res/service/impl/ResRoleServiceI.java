package com.yhj.modules.res.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yhj.modules.commons.service.impl.BaseService;
import com.yhj.modules.res.dao.ResRoleMapper;
import com.yhj.modules.res.entity.SysResRole;
import com.yhj.modules.res.pojo.PoJoResRole;
import com.yhj.modules.res.service.ResRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@Service("resRoleService")
@Transactional(rollbackFor = {Exception.class})
public class ResRoleServiceI extends BaseService<PoJoResRole, Mapper<PoJoResRole>> implements ResRoleService {

    @Autowired
    private ResRoleMapper resRoleMapper;

    @Override
    public List<PoJoResRole> queryAll() {
        return resRoleMapper.queryAllResRole();
    }


    /**
     * @param resId   resource`s id that would be assigned to roles
     * @param roleIds this is a array of role`id
     * @return list  res_role`s primary keys
     * @description this option will be divide two step, at first set res_role`s res_status in 0.The second will core option that assign roles
     */
    @Override
    public List<Integer> saveResToRole(String resId, String[] roleIds) {
        List<Integer> list = Lists.newArrayList();
        SysResRole sysResRole = new SysResRole();
        sysResRole.setResId(resId);

        updateResStatus("0", resId, roleIds);

        for (String roleId : roleIds) {
            sysResRole.setRoleId(roleId);
            resRoleMapper.saveResToRole(sysResRole);
            list.add(sysResRole.getId());
        }
        return list;
    }

    /**
     * @param resStatus res_role`s res_status
     * @param resId     sys_resource`s Id
     * @param roleIds   role`s id array
     * @return count of updated records
     */
    public Integer updateResStatus(String resStatus, String resId, String[] roleIds) {
        Map<String, Object> resMap = Maps.newHashMap();
        resMap.put("resStatus", resStatus);
        resMap.put("resId", resId);
        resMap.put("roleIds", roleIds);
        return resRoleMapper.updateResStatus(resMap);
    }


    /**
     * @param resId The resource`s Id
     * @return list
     */
    @Override
    public List<Integer> queryResAssignedRoleIds(String resId) {
        return resRoleMapper.queryResAssignedRoleIds(resId);
    }
}
