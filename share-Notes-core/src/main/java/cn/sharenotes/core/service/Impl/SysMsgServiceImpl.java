package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.SysMsgService;
import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.mapper.SysMsgMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class SysMsgServiceImpl implements SysMsgService {
    @Resource
    SysMsgMapper sysMsgMapper;
    @Override
    public List<SysMsg> getSysMsg(int recentId) {

        return sysMsgMapper.selectByRecentId(recentId);
    }
}
