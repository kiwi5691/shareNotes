package cn.sharenotes.core.notify;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaSubscribeMessage;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.sharenotes.core.service.UserFormIdService;
import cn.sharenotes.db.domain.UserFormid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 微信模版消息通知
 */
public class WxTemplateSender {
    @Autowired
    WxMaService wxMaService;

    @Autowired
    UserFormIdService userFormIdService;

    /**
     * 发送微信消息(模板消息),不带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     */
    public void sendWechatMsg(String touser, String templatId, List<WxMaSubscribeMessage.Data> wxMaSubscribeData) {
        sendMsg(touser, templatId, wxMaSubscribeData, "", "", "");
    }

    /**
     * 发送微信消息(模板消息),带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     * @param page      跳转页面
     */
    public void sendWechatMsg(String touser, String templatId, List<WxMaSubscribeMessage.Data> wxMaSubscribeData, String page) {
        sendMsg(touser, templatId, wxMaSubscribeData, page, "", "");
    }

    private void sendMsg(String touser, String templatId, List<WxMaSubscribeMessage.Data> wxMaSubscribeData, String page, String color, String emphasisKeyword) {
        UserFormid userFormid = userFormIdService.queryByOpenId(touser);
        if (userFormid == null)
            return;

        WxMaSubscribeMessage subscribeMessage  = new WxMaSubscribeMessage ();
        subscribeMessage.setPage("");
        //模板消息id
        subscribeMessage.setTemplateId(templatId);
        //给谁推送 用户的openid （可以调用根据code换openid接口)
        subscribeMessage.setToUser(touser);
        //==========================================创建一个参数集合========================================================
        subscribeMessage.setData(wxMaSubscribeData);


        try {
            wxMaService.getMsgService().sendSubscribeMsg(subscribeMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<WxMaTemplateData> createMsgData(String[] parms) {
        List<WxMaTemplateData> dataList = new ArrayList<>();
        for (int i = 1; i <= parms.length; i++) {
            dataList.add(new WxMaTemplateData("keyword" + i, parms[i - 1]));
        }

        return dataList;
    }
}
