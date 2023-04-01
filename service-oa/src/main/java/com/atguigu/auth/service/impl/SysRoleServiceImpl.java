package com.atguigu.auth.service.impl;

import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.auth.service.SysRoleService;
import com.atguigu.auth.service.SysUserRoleService;
import com.atguigu.model.system.SysRole;
import com.atguigu.model.system.SysUserRole;
import com.atguigu.vo.system.AssignRoleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Override
    public Map<String, Object> toAssign(Long id) {
        Map<String, Object> map = new HashMap<>();
        //返回所有角色以及已经分配的角色
        //1.得到所有角色
        List<SysRole> roles = baseMapper.selectList(null);
        //2.查到用户已经分配的角色
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,id);
        List<SysUserRole> userRoles = sysUserRoleService.list(queryWrapper);
        //3.得到roleid
        List<Long> assignRoleIds = userRoles.stream().map((c) -> c.getRoleId()).collect(Collectors.toList());
        ArrayList<SysRole> assignRoles = new ArrayList<>();
        for (SysRole role : roles) {
            if (assignRoleIds.contains(role.getId())){
                assignRoles.add(role);
            }
        }
        map.put("assignRoleList",assignRoles);
        map.put("allRolesList",roles);
        return map;
    }

    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //1.userId 分配角色的ids
        //1.先把旧关系清除
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,assignRoleVo.getUserId());
        sysUserRoleService.remove(queryWrapper);

        //2.建立新关系
        List<SysUserRole> sysUserRoles = new ArrayList<>();
        for (Long roleId : assignRoleVo.getRoleIdList()) {
            SysUserRole userRole = new SysUserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(assignRoleVo.getUserId());
            sysUserRoles.add(userRole);
        }
        sysUserRoleService.saveBatch(sysUserRoles);
    }
}
