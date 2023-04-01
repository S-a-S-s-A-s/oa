package com.atguigu.auth.controller;


import com.atguigu.auth.service.SysUserService;
import com.atguigu.common.result.Result;
import com.atguigu.model.system.SysUser;
import com.atguigu.vo.system.SysUserQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author sas
 * @since 2023-04-01
 */
@Api(tags = "用户模块")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @ApiOperation("条件分页查询")
    @GetMapping("{page}/{limit}")
    public Result pageQuery(@PathVariable Long page, @PathVariable Long limit, SysUserQueryVo seacherObj){
        Page<SysUser> userPage = new Page<>(page,limit);
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        String username = seacherObj.getKeyword();
        String timeBegin = seacherObj.getCreateTimeBegin();
        String timeEnd = seacherObj.getCreateTimeEnd();
        if (!StringUtils.isEmpty(username)){
            queryWrapper.like(SysUser::getUsername,username);
        }
        if (!StringUtils.isEmpty(timeBegin)){
            queryWrapper.ge(SysUser::getCreateTime,timeBegin);
        }
        if (!StringUtils.isEmpty(timeEnd)){
            queryWrapper.le(SysUser::getCreateTime,timeEnd);
        }
        sysUserService.page(userPage,queryWrapper);
        return Result.ok(userPage);
    }
    @ApiOperation("修改用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable Long id,@PathVariable Integer status){
        SysUser user = sysUserService.getById(id);
        user.setStatus(status);
        sysUserService.updateById(user);
        return Result.ok(null);
    }
    @ApiOperation("根据id查询用户")
    @GetMapping("get/{id}")
    public Result findUserById(@PathVariable Long id){
        SysUser user = sysUserService.getById(id);
        return Result.ok(user);
    }
    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result addUser(@RequestBody SysUser sysUser){
        boolean success = sysUserService.save(sysUser);
        if (success){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("更新用户")
    @PutMapping("update")
    public Result updateUser(@RequestBody SysUser sysUser){
        boolean success = sysUserService.updateById(sysUser);
        if (success){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("根据id删除用户")
    @DeleteMapping("remove/{id}")
    public Result deleteUser(@PathVariable Long id){
        boolean success = sysUserService.removeById(id);
        if (success){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
    @ApiOperation("批量删除用户")
    @DeleteMapping("batchRemove")
    public Result deleteUser(@RequestBody List<Long> ids){
        boolean success = sysUserService.removeByIds(ids);
        if (success){
            return Result.ok(null);
        }else{
            return Result.fail(null);
        }
    }
}

