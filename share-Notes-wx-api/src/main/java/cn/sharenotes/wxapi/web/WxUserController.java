package cn.sharenotes.wxapi.web;

import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.wxapi.annotation.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 * @author kiwi
 */
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {


    /**
     * 用户个人页面数据
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("index")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
       //显示个人信息
        data.put("order", "个人信息");
        return ResponseUtil.ok(data);
    }

}