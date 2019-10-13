package cn.sharenotes.wxapi.web.content;

import cn.sharenotes.core.service.Impl.BaseStorageService;
import cn.sharenotes.core.storage.StorageService;
import cn.sharenotes.core.utils.ResponseUtil;
import cn.sharenotes.db.domain.Attachments;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author kiwi
 * @date 2019/10/13 16:37
 */
@Slf4j
@RestController
@RequestMapping("/wx/storage")
public class WxStorageController {


    @Autowired
    private StorageService storageService;
    @Autowired
    private BaseStorageService baseStorageService;


    @PostMapping("/upload")
    public Object upload(@RequestParam("file") MultipartFile file)  {
        String originalFilename = file.getOriginalFilename();
        Attachments attachments = new Attachments();
        try {
             attachments = storageService.store(file.getInputStream(), file.getSize(), file.getContentType(), originalFilename);
        }catch (Exception e){
            return ResponseUtil.fail(200,"图片不支持");
        }

        return ResponseUtil.ok(attachments);
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
