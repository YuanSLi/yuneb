package com.xxxx.server.service.impl;


import com.xxxx.server.mapper.MenuMapper;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.service.IMenuService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysl
 * @since 2021-03-06
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

    @Override
    public List<Menu> getMenuByAdminId() {
        return null;
    }
}
