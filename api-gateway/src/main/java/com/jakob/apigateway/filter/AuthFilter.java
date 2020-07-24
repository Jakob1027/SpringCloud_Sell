//package com.jakob.apigateway.filter;
//
//import com.jakob.apigateway.constant.CookieConstants;
//import com.jakob.apigateway.utils.CookieUtil;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.apache.commons.lang.StringUtils;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import static com.jakob.apigateway.constant.CookieConstants.TOKEN_TEMPLATE;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//
//@Component
//public class AuthFilter extends ZuulFilter {
//
//    private final StringRedisTemplate stringRedisTemplate;
//
//    public AuthFilter(StringRedisTemplate stringRedisTemplate) {
//        this.stringRedisTemplate = stringRedisTemplate;
//    }
//
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext context = RequestContext.getCurrentContext();
//        HttpServletRequest request = context.getRequest();
//
//        /**
//         * /order/create 买家
//         * /order/finish 卖家
//         */
//        if ("/order/order/create".equals(request.getRequestURI())) {
//            Cookie cookie = CookieUtil.getCookie(CookieConstants.OPENID, request);
//            if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
//                context.setSendZuulResponse(false);
//                context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        if ("/order/order/finish".equals(request.getRequestURI())) {
//            Cookie cookie = CookieUtil.getCookie(CookieConstants.TOKEN, request);
//            if (cookie == null ||
//                    StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(TOKEN_TEMPLATE, cookie.getValue())))) {
//                context.setSendZuulResponse(false);
//                context.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//        return null;
//    }
//}
