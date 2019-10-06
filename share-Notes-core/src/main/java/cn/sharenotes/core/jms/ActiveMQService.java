package cn.sharenotes.core.jms;

import java.io.IOException;

/**
 * @auther kiwi
 * @Date 2019/10/4 16:42
 */
public interface ActiveMQService {
    public void sendEmail(String to, String subject, String content) throws IOException;

    void textQueue(String content) throws IOException;
}
