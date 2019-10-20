package cn.sharenotes.wxapi.web;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.sharenotes.core.enums.ContentBase;
import cn.sharenotes.core.redis.KeyPrefix.IssueSubmitKey;
import cn.sharenotes.core.redis.KeyPrefix.UpdateInfoSubmitKey;
import cn.sharenotes.core.redis.RedisManager;
import cn.sharenotes.core.utils.IpUtil;
import cn.sharenotes.core.utils.JacksonUtil;
import cn.sharenotes.core.utils.RegexUtil;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.core.utils.bcrypt.BCryptPasswordEncoder;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.model.dto.UserDto;
import cn.sharenotes.db.model.dto.WxLoginInfo;
import cn.sharenotes.core.service.UserService;
import cn.sharenotes.wxapi.annotation.LoginUser;
import cn.sharenotes.wxapi.service.UserTokenManager;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.sharenotes.core.utils.WxResponseCode.*;


/**
 * 鉴权服务
 */
@Slf4j
@RestController
@RequestMapping("/wx/auth")
@Validated
public class WxAuthController {

    @Autowired
    private UserService userService;

    @Resource
    private RedisManager redisManager;

    @Autowired
    private WxMaService wxService;

    @GetMapping("getId")
    public Object getId(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("id", userId);
        return ResponseUtil.ok(data);
    }



    /**
     * 账号登录
     *
     * @param body    请求内容，{ username: xxx, password: xxx }
     * @param request 请求对象
     * @return 登录结果
     */
    @PostMapping("login")
    public Object login(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        if (username == null || password == null) {
            return ResponseUtil.badArgument();
        }

        List<User> userList = userService.queryByUsername(username);
        User user = null;
        if (userList.size() > 1) {
            return ResponseUtil.serious();
        } else if (userList.size() == 0) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号不存在");
        } else {
            user = userList.get(0);
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, user.getPassword())) {
            return ResponseUtil.fail(AUTH_INVALID_ACCOUNT, "账号密码不对");
        }

        // 更新登录情况
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        UserDto userDto = new UserDto();
        userDto.setNickName(username);
        userDto.setAvatarUrl(user.getAvatar());

        String token = UserTokenManager.generateToken(user.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userDto);
        return ResponseUtil.ok(result);
    }

    /**
     * 微信登录
     *
     * @param wxLoginInfo 请求内容，{ code: xxx, userInfo: xxx }
     * @param request     请求对象
     * @return 登录结果
     */
    @ApiOperation(value = "通过微信openid登录")
    @PostMapping("login_by_weixin")
    public Object loginByWeixin(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserDto userDto = wxLoginInfo.getUserInfo();
        if (code == null || userDto == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }

        User user = userService.queryByOid(openId);
        if (user == null) {
            user = new User();
            user.setUsername(openId);
            user.setPassword(openId);
            user.setWeixinOpenid(openId);
            user.setAvatar(userDto.getAvatarUrl());
            user.setNickname(userDto.getNickName());
            user.setGender(userDto.getGender());
            user.setStatus((byte) 0);
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);

            userService.add(user);

        } else {
            user.setLastLoginTime(new Date());
            user.setLastLoginIp(IpUtil.getIpAddr(request));
            user.setSessionKey(sessionKey);
            if (userService.updateById(user) == 0) {
                return ResponseUtil.updatedDataFailed();
            }
        }

        User user1 = userService.queryByOid(openId);
        // token
        String token = UserTokenManager.generateToken(user1.getId());

        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userDto);
        result.put("userId", user1.getId());


        return ResponseUtil.ok(result);
    }



    /**
     * 账号注册
     *
     * @param body    请求内容
     *                {
     *                username: xxx,
     *                password: xxx,
     *                mobile: xxx
     *                code: xxx
     *                }
     *                其中code是手机验证码，目前还不支持手机短信验证码
     * @param request 请求对象
     * @return 登录结果
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * token: xxx,
     * tokenExpire: xxx,
     * userInfo: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("register")
    public Object register(@RequestBody String body, HttpServletRequest request) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");
        String mobile = JacksonUtil.parseString(body, "mobile");
        String code = JacksonUtil.parseString(body, "code");
        // 如果是小程序注册，则必须非空
        // 其他情况，可以为空
        String wxCode = JacksonUtil.parseString(body, "wxCode");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(code)) {
            return ResponseUtil.badArgument();
        }

        List<User> userList = userService.queryByUsername(username);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_NAME_REGISTERED, "用户名已注册");
        }

        userList = userService.queryByMobile(mobile);
        if (userList.size() > 0) {
            return ResponseUtil.fail(AUTH_MOBILE_REGISTERED, "手机号已注册");
        }
        if (!RegexUtil.isMobileExact(mobile)) {
            return ResponseUtil.fail(AUTH_INVALID_MOBILE, "手机号格式不正确");
        }
        //判断验证码是否正确
//        String cacheCode = CaptchaCodeManager.getCachedCaptcha(mobile);
//        if (cacheCode == null || cacheCode.isEmpty() || !cacheCode.equals(code)) {
//            return ResponseUtil.fail(AUTH_CAPTCHA_UNMATCH, "验证码错误");
//        }

        String openId = "";
        // 非空，则是小程序注册
        // 继续验证openid
        if(!StringUtils.isEmpty(wxCode)) {
            try {
                WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(wxCode);
                openId = result.getOpenid();
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseUtil.fail(AUTH_OPENID_UNACCESS, "openid 获取失败");
            }
            userList = userService.queryByOpenid(openId);
            if (userList.size() > 1) {
                return ResponseUtil.serious();
            }
            if (userList.size() == 1) {
                User checkUser = userList.get(0);
                String checkUsername = checkUser.getUsername();
                String checkPassword = checkUser.getPassword();
                if (!checkUsername.equals(openId) || !checkPassword.equals(openId)) {
                    return ResponseUtil.fail(AUTH_OPENID_BINDED, "openid已绑定账号");
                }
            }
        }

        User user = null;
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(password);
        user = new User();
        user.setUsername(username);
        user.setPassword(encodedPassword);
        user.setMobile(mobile);
        user.setWeixinOpenid(openId);
        user.setAvatar("https://yanxuan.nosdn.127.net/80841d741d7fa3073e0ae27bf487339f.jpg?imageView&quality=90&thumbnail=64x64");
        user.setNickname(username);
        user.setGender((byte) 0);
        user.setStatus((byte) 0);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        userService.add(user);


        UserDto userInfo = new UserDto();
        userInfo.setNickName(username);
        userInfo.setAvatarUrl(user.getAvatar());

        // token
        String token = UserTokenManager.generateToken(user.getId());
        
        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("token", token);
        result.put("userInfo", userInfo);
        return ResponseUtil.ok(result);
    }

  

    /**
     * 账号信息更新
     *
     * @param body    请求内容
     * @return 登录结果
     * 成功则 { errno: 0, errmsg: '成功' }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @PostMapping("profile")
    public Object profile(@LoginUser Integer userId, @RequestBody String body) {
        Integer updteInfo;
        if(userId == null){
            return ResponseUtil.unlogin();
        }
        String nickname = JacksonUtil.parseString(body, "nickname");

        updteInfo=redisManager.get(UpdateInfoSubmitKey.board, "userId :"+userId, Integer .class);
        if (updteInfo == null){
            updteInfo=1;
            redisManager.set(UpdateInfoSubmitKey.board, "userId :"+userId, updteInfo);
        }
        else if(updteInfo <= ContentBase.LIMITTIMESINFO.getValue()){
            redisManager.incr(UpdateInfoSubmitKey.board, "userId :"+userId);
        }else if(updteInfo > ContentBase.LIMITTIMESINFO.getValue()){
            return ResponseUtil.fail(102,"这礼拜修改上限");
        }

        User user = userService.findById(userId);
        if(!StringUtils.isEmpty(nickname)){
            user.setNickname(nickname);
        }

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }

        return ResponseUtil.ok();
    }

//同步微信信息
    @PostMapping("sync")
    public Object sync(@RequestBody WxLoginInfo wxLoginInfo, HttpServletRequest request) {
        String code = wxLoginInfo.getCode();
        UserDto userDto = wxLoginInfo.getUserInfo();
        if (code == null || userDto == null) {
            return ResponseUtil.badArgument();
        }

        String sessionKey = null;
        String openId = null;
        try {
            WxMaJscode2SessionResult result = this.wxService.getUserService().getSessionInfo(code);
            sessionKey = result.getSessionKey();
            openId = result.getOpenid();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sessionKey == null || openId == null) {
            return ResponseUtil.fail();
        }
        Integer updteInfo;

        updteInfo=redisManager.get(UpdateInfoSubmitKey.board, "userOpId :"+openId, Integer .class);
        if (updteInfo == null){
            updteInfo=1;
            redisManager.set(UpdateInfoSubmitKey.board, "userOpId :"+openId, updteInfo);
        }
        else if(updteInfo <= ContentBase.LIMITTIMESINFO.getValue()){
            redisManager.incr(UpdateInfoSubmitKey.board, "userOpId :"+openId);
        }else if(updteInfo > ContentBase.LIMITTIMESINFO.getValue()){
            return ResponseUtil.fail(102,"这礼拜同步上限");
        }

        User user = userService.queryByOid(openId);
        user.setAvatar(userDto.getAvatarUrl());
        user.setNickname(userDto.getNickName());
        user.setGender(userDto.getGender());
        user.setStatus((byte) 0);
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(IpUtil.getIpAddr(request));
        user.setSessionKey(sessionKey);

        if (userService.updateById(user) == 0) {
            return ResponseUtil.updatedDataFailed();
        }


        Map<Object, Object> result = new HashMap<Object, Object>();
        result.put("userInfo", userDto);
        return ResponseUtil.ok(result);
    }


    @PostMapping("logout")
    public Object logout(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        return ResponseUtil.ok();
    }

}
