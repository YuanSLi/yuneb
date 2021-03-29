package com.xxxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxxx.server.config.config.redis.RedisCache;
import com.xxxx.server.config.security.JwtTokenUtil;
import com.xxxx.server.mapper.AdminMapper;
import com.xxxx.server.pojo.Admin;
import com.xxxx.server.pojo.Menu;
import com.xxxx.server.pojo.RespBean;
import com.xxxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysl
 * @since 2021-03-05
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {
    @Resource
    private RedisCache redisCache;
    @Resource
    private AdminMapper adminMapper;

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        //从session取出存放的验证码，用于和前端输入的验证码对比
        Object captcha = (String) request.getSession().getAttribute("captcha");
        if (StringUtils.isEmpty(code) || !captcha.equals(code)){
            return RespBean.error("验证码不正确，请重新输入");
        }
        //登录
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null==userDetails || passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或者密码不正确");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账户已经禁用");
        }
        //更新security登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        //生成token
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        return RespBean.success("登录成功！",tokenMap);
    }

    /**
     * 根据用户名获取用户
     * @param username
     * @return
     */
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }


}
