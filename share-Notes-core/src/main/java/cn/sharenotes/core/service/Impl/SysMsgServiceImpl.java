package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.SysMsgService;
import cn.sharenotes.core.utils.MsgUtils;
import cn.sharenotes.db.domain.Categories;
import cn.sharenotes.db.domain.Comments;
import cn.sharenotes.db.domain.Posts;
import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.mapper.*;
import cn.sharenotes.db.model.dto.MsgListDto;
import cn.sharenotes.db.model.dto.SysMsgDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class SysMsgServiceImpl implements SysMsgService {
    @Resource
    SysMsgMapper sysMsgMapper;
    @Resource
    PostsMapper postsMapper;
    @Resource
    CategoriesMapper categoriesMapper;
    @Resource
    CommentsMapper commentsMapper;
    @Resource
    PostCategoriesMapper postCategoriesMapper;
     List<SysMsg> sysMsgs = null;
    @Resource
    private UserMapper userMapper;
    @Override
    public List<MsgListDto> getSysMsg(int recentId) {
        sysMsgs  = sysMsgMapper.selectByRecentId(recentId);
        List<SysMsgDto> sysMsgDtos = new ArrayList<>();
        List<MsgListDto> msgListDtos = new ArrayList<>();

        for (SysMsg sysMsg:
             sysMsgs) {
            Posts posts = postsMapper.selectByPrimaryKey(Integer.parseInt(sysMsg.getTitle()));
            int categoriesId = postCategoriesMapper.selectCategeIdByPostId(posts.getId());
            Categories categories = categoriesMapper.selectByPrimaryKey(categoriesId);
            int commentId = sysMsg.getSendId();
            Comments comments = commentsMapper.selectByPrimaryKey((long) commentId);
            SysMsgDto sysMsgDto = new SysMsgDto(posts,sysMsg,comments,categories);
            MsgListDto msgListDto = new MsgListDto(sysMsg.getId(), MsgUtils.getType(sysMsg.getType()),comments.getContent());
                msgListDtos.add(msgListDto);
        }
        return   msgListDtos ;
    }


    @Override
    public Integer getSysMsgNum(int recentId) {
        sysMsgs = sysMsgMapper.selectByRecentId(recentId);

        if(sysMsgs!=null){
            return sysMsgs.size();
        }else {
        return 0;
        }
    }

    @Override
    public SysMsgDto getMsgPostById(int msgId) {
        SysMsg sysMsg = sysMsgMapper.selectByPrimaryKey((long) msgId);
        Posts posts = postsMapper.selectByPrimaryKey(Integer.parseInt(sysMsg.getTitle()));
        int categoriesId = postCategoriesMapper.selectCategeIdByPostId(posts.getId());
        Categories categories = categoriesMapper.selectByPrimaryKey(categoriesId);
        int commentId = sysMsg.getSendId();
        Comments comments = commentsMapper.selectByPrimaryKey((long) commentId);
        SysMsgDto sysMsgDto = new SysMsgDto(posts,sysMsg,comments,categories);

        return sysMsgDto;
    }

    @Override
    public Integer delectMsgById(int msgId) {
        return sysMsgMapper.deleteByPrimaryKey((long) msgId);
    }

    @Override
    public Integer delectMsgByRecentId(int recentId) {
        return sysMsgMapper.deleteByRecentId((long) recentId);
    }

}
