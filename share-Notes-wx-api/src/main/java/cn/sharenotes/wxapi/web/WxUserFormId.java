package cn.sharenotes.wxapi.web;


import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author kiwi
 */
@Slf4j
@RestController
@RequestMapping("/wx/formid")
@Validated
public class WxUserFormId {


    @ApiOperation(value = "通过微信openid登录")
    @ApiImplicitParams({
        @ApiImplicitParam(name ="userId",value = "用户id",paramType = "path",required = true,dataType = "Integer"),
        @ApiImplicitParam(name ="formId",value = "微信id",paramType = "path",required = true,dataType = "String")
    })
    @GetMapping("create")
    public Object create(@LoginUser Integer userId, @NotNull String formId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

//        TODO 从user 从提取ID，然后存入 userFormID表
        LocalDateTime.now().plusDays(7);
       //
        return ResponseUtil.ok();
    }
}
