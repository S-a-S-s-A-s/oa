package com.atguigu.auth.controller;

import com.atguigu.auth.service.SysRoleService;
import com.atguigu.common.config.exception.GuiguException;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysRole;
import com.atguigu.vo.system.SysRoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Api(tags = "角色管理")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {
    //注入service
    @Autowired
    private SysRoleService sysRoleService;
    //查询所有角色
    @ApiOperation("得到所有角色")
    @GetMapping("/findAll")
    public Result findAll(){
        List<SysRole> sysRoles = sysRoleService.list();
        try {
            int i = 10/0;
        }catch (Exception e){
            throw new GuiguException(20001,"执行了自定义异常处理");
        }


        return Result.ok(sysRoles);
    }
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQuery(@PathVariable Long page, @PathVariable Long limit, SysRoleQueryVo sysRoleQueryVo){
        Page<SysRole> sysRolePage = new Page<>(page,limit);
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        String roleName = sysRoleQueryVo.getRoleName();
        if (!StringUtils.isEmpty(roleName)){
            queryWrapper.like(SysRole::getRoleName, roleName);
        }
        sysRoleService.page(sysRolePage,queryWrapper);
        return Result.ok(sysRolePage);
    }
    @ApiOperation("添加角色")
    @PostMapping ("save")
    public Result save(@RequestBody SysRole sysRole){
        boolean status = sysRoleService.save(sysRole);
        if (status){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("根据id查询")
    @GetMapping("get/{id}")
    public Result getId(@PathVariable Long id){
        SysRole sysRole = sysRoleService.getById(id);
        return Result.ok(sysRole);
    }
    @ApiOperation("更新")
    @PutMapping("update")
    public Result update(@RequestBody SysRole sysRole){
        boolean status = sysRoleService.updateById(sysRole);
        if (status){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("根据id删除")
    @DeleteMapping ("remove/{id}")
    public Result deleteId(@PathVariable Long id){
        boolean status = sysRoleService.removeById(id);
        if (status){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("批量删除")
    @DeleteMapping ("batchRemove")
    public Result batchRemove(@RequestBody List<Long> ids){
        boolean status = sysRoleService.removeByIds(ids);
        if (status){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
}
