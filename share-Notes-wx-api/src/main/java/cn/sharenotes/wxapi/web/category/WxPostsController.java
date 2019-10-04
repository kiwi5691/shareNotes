package cn.sharenotes.wxapi.web.category;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther kiwi
 * @Date 2019/10/4 1:04
 */
@RestController
@RequestMapping("/wx/posts")
public class WxPostsController {

    @ApiOperation(value = "通过 categoryId获取目录")
    @GetMapping("/getAll/{cateId}")
    public Object getAllCategories(/*@LoginUser Integer userId,*/ @PathVariable("cateId") Integer cateId) {
        return "fuck";
    }
}
