package com.xqy.java_sms_cloud_web.controller;

import com.xqy.java_sms_cloud_model.model.UserInfo;
import com.xqy.java_sms_cloud_user_api.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @DubboReference
    private UserInfoService userInfoService;

    @GetMapping("/selectAllUserInfo")
    public List<UserInfo> selectAllUserInfo() {
        return userInfoService.selectAllUserInfo();
    }


}
