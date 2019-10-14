package cn.sharenotes.core.service;

import cn.sharenotes.db.model.dto.MsgListDto;
import cn.sharenotes.db.model.dto.SysMsgDto;

import java.util.List;

/**
 * @author hu
 * @Date 2019/10/11 15:08
 */
public interface SysMsgService {
    /**
     * 通过目录id获取评论
     *
     * @return 评论
     */
    public List<MsgListDto> getSysMsg(int recentId);
    /**
     * 通过目录id获取评论数量
     *
     * @return 评论数量
     */
    public Integer getSysMsgNum(int recentId);
    /**
     * 通过消息id删除消息
     *
     * @return
     */
    public SysMsgDto getMsgPostById(int msgId);
    /**
     * 通过消息id删除消息
     *
     * @return
     */
    public Integer delectMsgById(int msgId);
    /**
     * 通过消息id删除消息
     *
     * @return
     */
    public Integer delectMsgByRecentId(int recentId);
}
