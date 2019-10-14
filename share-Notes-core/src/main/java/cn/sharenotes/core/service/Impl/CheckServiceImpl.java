package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.CheckService;
import cn.sharenotes.core.utils.filter.HttpUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CheckServiceImpl implements CheckService {

    //	小程序的access_token
    String ACCESS_TOKEN = "";

    /**
     * 小程序敏感词过滤
     *
     * @param content 待校验的正文
     * @return 0 无敏感词，1 有敏感词，2 校验失败
     */
    @Override
    public Integer checkWords(String content) {
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token=" + ACCESS_TOKEN;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("content", content);
        String result = HttpUtils.sendPost(url, JSONObject.toJSONString(map));
        System.out.println("敏感词校验结果：" + result);
        Map<String, Object> resultMap = JSONObject.parseObject(result, Map.class);
        if (resultMap.containsKey("errcode") && resultMap.get("errcode").toString().equals("0")) {
            return 0;
        }
        if (resultMap.get("errcode").toString().equals("42001")) {
            System.out.println("小程序accessToken过期");
            return 2;
        }
        return 1;
    }

}
