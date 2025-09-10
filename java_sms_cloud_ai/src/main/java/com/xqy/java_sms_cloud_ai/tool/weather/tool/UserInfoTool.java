package com.xqy.java_sms_cloud_ai.tool.weather.tool;

import com.xqy.java_sms_cloud_model.model.UserInfo;
import com.xqy.java_sms_cloud_user_api.service.UserInfoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
public class UserInfoTool {

    @DubboReference
    private UserInfoService userInfoService;

    @Tool(description = "获取所有人员")
    public List<UserInfo> getAllUserInfo() {
        return userInfoService.selectAllUserInfo();
    }


}
