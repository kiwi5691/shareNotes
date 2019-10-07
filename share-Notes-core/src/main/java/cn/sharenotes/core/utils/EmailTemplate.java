package cn.sharenotes.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.Date;

/**
 * @auther kiwi
 * @Date 2019/9/7 23:55
 */
@Slf4j
public class EmailTemplate {

    /**
     *@Auther kiwi
     *@Data 2019/9/8
     @param  * @param
     * issue模板
     *@reutn java.lang.String
    */
    public static String issueTemplate(String titleName,String context) throws IOException {
        String fileName = "pod-scale-alarm.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.error("读取文件失败，fileName:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }

        String contentText = context;


        String emailHeadColor = "#8A2BE2";
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor,titleName ,contentText, date);

        return htmlText;
    }
}
