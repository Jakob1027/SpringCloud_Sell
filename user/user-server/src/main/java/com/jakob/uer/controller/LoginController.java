package com.jakob.uer.controller;

import com.jakob.uer.dataobject.UserInfo;
import com.jakob.uer.enums.ResultEnum;
import com.jakob.uer.enums.RoleEnum;
import com.jakob.uer.service.UserInfoService;
import com.jakob.uer.utils.CookieUtil;
import com.jakob.uer.vo.ResultVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.jakob.uer.constant.CookieConstants.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserInfoService userInfoService;
    private final StringRedisTemplate stringRedisTemplate;

    public LoginController(UserInfoService userInfoService, StringRedisTemplate stringRedisTemplate) {
        this.userInfoService = userInfoService;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @GetMapping("/buyer")
    public ResultVO buyer(String openid, HttpServletResponse response) {
        UserInfo userInfo = userInfoService.findByOpenid(openid);

        if (userInfo == null) {
            return ResultVO.error(ResultEnum.LOGIN_FAILED);
        }

        if (!userInfo.getRole().equals(RoleEnum.BUYER.getCode())) {
            return ResultVO.error(ResultEnum.ROLE_ERROR);
        }

        CookieUtil.setCookie(OPENID, openid, MAX_AGE, response);
        return ResultVO.success();
    }

    @GetMapping("/seller")
    public ResultVO seller(String openid, HttpServletRequest request, HttpServletResponse response) {
        UserInfo userInfo = userInfoService.findByOpenid(openid);

        Cookie cookie = CookieUtil.getCookie(TOKEN, request);
        if (cookie != null &&
                !StringUtils.isEmpty((stringRedisTemplate.opsForValue().get(String.format(TOKEN_TEMPLATE, cookie.getValue()))))) {
            return ResultVO.success();
        }

        if (userInfo == null) {
            return ResultVO.error(ResultEnum.LOGIN_FAILED);
        }

        if (!userInfo.getRole().equals(RoleEnum.SELLER.getCode())) {
            return ResultVO.error(ResultEnum.ROLE_ERROR);
        }

        String token = UUID.randomUUID().toString();

        CookieUtil.setCookie(TOKEN, token, MAX_AGE, response);
        stringRedisTemplate.opsForValue().set(String.format(TOKEN_TEMPLATE, token), openid, MAX_AGE, TimeUnit.SECONDS);
        return ResultVO.success();
    }
}
