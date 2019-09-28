package cn.sharenotes.wxapi.web;


import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
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
