package cn.sharenotes.core.jms;

import cn.sharenotes.core.utils.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author kiwi
 * @date 2019/10/13 18:34
 */
@Service
public class EmailService {

    @Resource
    private EmailUtil emailUtil;

    public void sendEmail(String to, String subject, String content) throws IOException {

        try {
            emailUtil.sendEmail(to,subject,content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
