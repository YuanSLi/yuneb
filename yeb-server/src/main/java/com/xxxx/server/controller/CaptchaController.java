package com.xxxx.server.controller;

import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.xxxx.server.config.config.redis.RedisCache;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码
 */
@RestController
public class CaptchaController {
    @Value("${jwt.expiration}")
    private Integer timeout;

    private static final String captcha = "captcha";


    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @Resource
    private RedisCache redisCache;

    @ApiOperation(value = "验证码")
    @GetMapping(value = "/captcha", produces = "image/jpeg")
    public void captcha(HttpServletRequest request, HttpServletResponse response){
        // 定义 response 输出类型为 image/jpeg 类型
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store,no-cache,must-revalidate");
        response.addHeader("Cache-Control", "post-check=0,pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成验证码 开始
        String text = defaultKaptcha.createText(); // 获取验证码文本内容
        System.out.println("验证码文本内容：" + text);
        //使用session来存储生成的验证码，由于后续的对比
        request.getSession().setAttribute("captcha", text);
//        使用redis存储验证码
//        String uuid = IdUtil.simpleUUID();
//        String captcha = fix + uuid;
//        //参数：key value 超时时间 时间细粒度
//        redisCache.setCacheObject(captcha, text, timeout, TimeUnit.SECONDS);
        BufferedImage image = defaultKaptcha.createImage(text); // 根据文本内容创建图形验证码
        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image,"jpg",outputStream); // 输出流输出图片，格式为jpg
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (null!=outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 生成验证码 结束
        // 查看验证码： http://localhost:8081/captcha

    }
}
