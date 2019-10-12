package cn.sharenotes.core.service;

import cn.sharenotes.db.domain.SysMsg;

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
    public List<SysMsg> getSysMsg(int recentId);
    /**
     * 通过目录id获取评论数量
     *
     * @return 评论数量
     */
    public Integer getSysMsgNum(int recentId);
}
