package cn.sharenotes.core.jms;

import cn.sharenotes.core.utils.EmailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

//import javax.jms.MapMessage;
//import javax.jms.Message;

/**
 * @auther kiwi
 * @Date 2019/10/4 16:47
 */
@Slf4j
@Service
public class ConsumerService {

//    @Autowired
//    private EmailUtil emailUtil;
    /**
     * 通过监听目标队列实现功能
     */
//    @JmsListener(destination = "issueSend")
//    public void sendMail(Message message) throws Exception {
//        MapMessage mapMessage = (MapMessage) message;
//        String content = mapMessage.getString("content");
//        String address = mapMessage.getString("address");
//        String subject = mapMessage.getString("subject");
//        emailUtil.sendEmail(address,subject,content);
//    }


//    @JmsListener(destination = "textQueue")
//    public void textQueue(Message message) throws Exception {
//        MapMessage mapMessage = (MapMessage) message;
//        String content = mapMessage.getString("content");
//        log.info(content);
//    }
}
