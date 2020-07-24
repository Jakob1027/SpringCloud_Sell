package com.jakob.uer.service;

import com.jakob.uer.dataobject.UserInfo;

public interface UserInfoService {
    UserInfo findByOpenid(String openid);
}
