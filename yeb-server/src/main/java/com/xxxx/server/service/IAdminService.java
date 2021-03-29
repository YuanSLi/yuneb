package com.xxxx.server.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ysl
 * @since 2021-03-06
 */
public interface IAdminService extends IService<Admin> {
    /**
     * 登陆之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    RespBean login(String username, String password, String code, HttpServletRequest request);

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    Admin getAdminByUserName(String username);


}
