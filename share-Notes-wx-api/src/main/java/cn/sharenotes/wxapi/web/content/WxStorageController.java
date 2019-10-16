package cn.sharenotes.wxapi.web.content;

import cn.binarywang.wx.miniapp.api.WxMaSecCheckService;
import cn.sharenotes.core.service.Impl.BaseStorageService;
import cn.sharenotes.core.storage.StorageService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.Attachments;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;

/**
 * @author kiwi
 * @date 2019/10/13 16:37
 */
@Slf4j
@RestController
@RequestMapping("/wx/storage")
public class WxStorageController {

    @Autowired
    private WxMaSecCheckService wxMaSecCheckService;
    @Autowired
    private StorageService storageService;
    @Autowired
    private BaseStorageService baseStorageService;


    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file) throws WxErrorException {
        Attachments attachments =null;
//        if(wxMaSecCheckService.checkImage(file)){
            String originalFilename = file.getOriginalFilename();
             attachments = new Attachments();
        try {
            attachments = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File f = null;
        if(file.equals("")||file.getSize()<=0){
            file = null;
        }else{
            InputStream ins = null;
            try {
                ins = file.getInputStream();
                f=new File(file.getOriginalFilename());
                inputStreamToFile(ins, f);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        try {
            if (wxMaSecCheckService.checkImage(f)) {
                log.info("here");
                File del = new File(f.toURI());
                del.delete();
                return ResponseUtil.ok(attachments);

            }
        }catch (Exception e) {
            File del = new File(f.toURI());
            del.delete();
            return ResponseUtil.fail(201, "违法违规内容");
        }

        return ResponseUtil.fail(501,"????");

    }
    public static void inputStreamToFile(InputStream ins,File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 访问存储对象
     *
     * @param key 存储对象key
     * @return
     */
    @GetMapping("/fetch/{key:.+}")
    public ResponseEntity<Resource> fetch(@PathVariable String key) {
        Attachments attachments = baseStorageService.findByKey(key);
        if (key == null) {
            return ResponseEntity.notFound().build();
        }
        if (key.contains("../")) {
            return ResponseEntity.badRequest().build();
        }
        String type = attachments.getType();
        MediaType mediaType = MediaType.parseMediaType(type);

        Resource file = storageService.loadAsResource(key);
        if (file == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().contentType(mediaType).body(file);
    }
}
