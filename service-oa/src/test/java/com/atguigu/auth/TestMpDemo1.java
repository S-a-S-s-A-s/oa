package com.atguigu.auth;


import com.atguigu.auth.mapper.SysRoleMapper;
import com.atguigu.model.system.SysRole;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
public class TestMpDemo1 {

    //注入
    @Autowired
    private SysRoleMapper mapper;
    @Test
    public void getAll(){
        List<SysRole> sysRoles = mapper.selectList(null);
        System.out.println(sysRoles);
    }

    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("角色管理员");
        sysRole.setRoleCode("role");
        sysRole.setDescription("角色管理员");
        int row = mapper.insert(sysRole);
        System.out.println(row);
        System.out.println(sysRole.getId());
    }
    @Test
    public void update(){
        SysRole sysRole = mapper.selectById(11);
        sysRole.setRoleName("管理员二");
        int i = mapper.updateById(sysRole);
        System.out.println(i);
    }
    @Test
    public void deleteById(){
        mapper.deleteById(11);
    }
    @Test
    public void delete(){
        mapper.deleteBatchIds(Arrays.asList(1,2));
    }
    @Test
    public void selectqw(){
        QueryWrapper<SysRole> sysRoleQueryWrapper = new QueryWrapper<>();
        sysRoleQueryWrapper.eq("role_name","总经理");
        List<SysRole> sysRoles = mapper.selectList(sysRoleQueryWrapper);
        System.out.println(sysRoles);
    }
    @Test
    public void selectlqw(){
        LambdaQueryWrapper<SysRole> sysRoleLambdaQueryWrapper = new LambdaQueryWrapper<>();
        sysRoleLambdaQueryWrapper.eq(SysRole::getRoleName,"总经理");
        mapper.selectList(sysRoleLambdaQueryWrapper);
    }
}
