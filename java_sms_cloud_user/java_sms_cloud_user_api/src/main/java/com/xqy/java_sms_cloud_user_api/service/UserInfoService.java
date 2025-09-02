package com.xqy.java_sms_cloud_user_api.service;

import com.xqy.java_sms_cloud_model.model.UserInfo;

import java.util.List;

public interface UserInfoService {
    List<UserInfo> selectAllUserInfo();
}
