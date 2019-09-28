package cn.sharenotes.wxapi.web.content;

import org.springframework.web.bind.annotation.*;

/**
 * Post controller.
 *
 * @author kiwi
 * @date 3/19/19
 */
@RestController
@RequestMapping("/wx/posts")
public class PostController {




//  todo
//    @GetMapping
//    @ApiOperation("Lists posts")
//    public Page<?> pageBy(Integer page, Integer size,排序方式
//    }




// todo
//    @GetMapping("{postId:\\d+}")
//    public PostVO getBy(@PathVariable("postId") Integer postId) {
//            获取文章
//        return 文章;
//    }





    //      todo
//    @PostMapping
//    public PostVO createBy(@Valid @RequestBody  PostDto post,
//                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
//        // TODO: 2019/9/28 创建文章，需要自动存储
//    }





// todo
//    @PutMapping("{postId:\\d+}")
//    public PostVO updateBy(@Valid @RequestBody PostDto postdto,
//                                 @PathVariable("postId") Integer postId,
//                                 @RequestParam(value = "autoSave", required = false, defaultValue = "false") Boolean autoSave) {
//         获取文章id
//        Post postToUpdate = postService.getById(postId);
//        postdto.update(postToUpdate);
//        自动更新
//        return 更新进数据库;
//    }



    @DeleteMapping("{postId:\\d+}")
    public void deletePermanently(@PathVariable("postId") Integer postId) {
//       todo 删除文章
    }


}
