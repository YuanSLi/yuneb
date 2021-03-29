package com.xxxx.server.service.impl;

import com.xxxx.server.mapper.NationMapper;
import com.xxxx.server.pojo.Nation;
import com.xxxx.server.service.INationService;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ysl
 * @since 2021-03-06
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements INationService {

}