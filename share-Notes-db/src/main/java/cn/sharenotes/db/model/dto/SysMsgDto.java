package cn.sharenotes.db.model.dto;

import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.SysMsg;

public class SysMsgDto  {

    private Posts posts;
    private SysMsg sysMsg;
    private Comments comments;
    private Categories categories;

    public SysMsgDto(){

    }
    public Posts getPosts() {
        return posts;
    }

    public void setPosts(Posts posts) {
        this.posts = posts;
    }

    public SysMsg getSysMsg() {
        return sysMsg;
    }

    public void setSysMsg(SysMsg sysMsg) {
        this.sysMsg = sysMsg;
    }

    public Comments getComments() {
        return comments;
    }

    public void setComments(Comments comments) {
        this.comments = comments;
    }

    public Categories getCategories() {
        return categories;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;
    }

    public SysMsgDto(Posts posts, SysMsg sysMsg, Comments comments, Categories categories) {
        this.posts = posts;
        this.sysMsg = sysMsg;
        this.comments = comments;
        this.categories = categories;
    }
}
