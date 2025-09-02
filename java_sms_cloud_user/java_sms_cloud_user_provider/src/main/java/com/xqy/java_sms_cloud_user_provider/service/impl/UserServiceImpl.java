package com.xqy.java_sms_cloud_user_provider.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xqy.java_sms_cloud_model.model.UserInfo;
import com.xqy.java_sms_cloud_user_api.service.UserInfoService;
import com.xqy.java_sms_cloud_user_provider.mapper.UserInfoMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class UserServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

    private final UserInfoMapper userInfoMapper;

    // 使用构造函数注入
    public UserServiceImpl(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public List<UserInfo> selectAllUserInfo() {
        Wrapper wrapper = new QueryWrapper(UserInfo.class);
        return userInfoMapper.selectList(wrapper);
    }
}
