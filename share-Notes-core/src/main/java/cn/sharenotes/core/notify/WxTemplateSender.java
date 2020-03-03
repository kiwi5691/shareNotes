package cn.sharenotes.core.notify;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateData;
import cn.binarywang.wx.miniapp.bean.WxMaTemplateMessage;
import cn.sharenotes.core.service.UserFormIdService;
import cn.sharenotes.db.domain.UserFormid;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void sendWechatMsg(String touser, String templatId, String[] parms) {
        sendMsg(touser, templatId, parms, "", "", "");
    }

    /**
     * 发送微信消息(模板消息),带跳转
     *
     * @param touser    用户 OpenID
     * @param templatId 模板消息ID
     * @param parms     详细内容
     * @param page      跳转页面
     */
    public void sendWechatMsg(String touser, String templatId, String[] parms, String page) {
        sendMsg(touser, templatId, parms, page, "", "");
    }

    private void sendMsg(String touser, String templatId, String[] parms, String page, String color, String emphasisKeyword) {
        UserFormid userFormid = userFormIdService.queryByOpenId(touser);
        if (userFormid == null)
            return;

        WxMaTemplateMessage msg = new WxMaTemplateMessage();
        msg.setTemplateId(templatId);
        msg.setToUser(touser);
        msg.setFormId(userFormid.getFormid());
        msg.setPage(page);
        msg.setColor(color);
        msg.setEmphasisKeyword(emphasisKeyword);
        msg.setData(createMsgData(parms));

        try {
            wxMaService.getMsgService().sendTemplateMsg(msg);
            userFormIdService.updateUserFormId(userFormid);
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
