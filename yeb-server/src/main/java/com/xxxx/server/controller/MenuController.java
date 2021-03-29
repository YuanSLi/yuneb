package com.xxxx.server.controller;


import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IAdminService;
import com.xxxx.server.service.IMenuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ysl
 * @since 2021-03-06
 */
@RestController
@RequestMapping("/system/cfg")
public class MenuController {
    @Resource
    private IMenuService menuService;

    @ApiOperation(value = "通过用户id查询菜单列表")
    @GetMapping("/menu")
    public List<Menu> getMenuByAdminId(){

        return menuService.getMenuByAdminId();
    }

}
