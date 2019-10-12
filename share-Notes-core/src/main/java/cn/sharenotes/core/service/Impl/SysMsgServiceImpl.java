package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.SysMsgService;
import cn.sharenotes.db.domain.SysMsg;
import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.mapper.SysMsgMapper;
import cn.sharenotes.db.mapper.UserMapper;
import cn.sharenotes.db.model.dto.SysMsgDto;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Service
public class SysMsgServiceImpl implements SysMsgService {
    @Resource
    SysMsgMapper sysMsgMapper;
     List<SysMsg> sysMsgs = null;
    @Resource
    private UserMapper userMapper;
    @Override
    public List<SysMsgDto> getSysMsg(int recentId) {
        sysMsgs  = sysMsgMapper.selectByRecentId(recentId);
        List<SysMsgDto> sysMsgDtos = new ArrayList<>();
        String avatar;
        if(CollectionUtils.isEmpty(sysMsgs)){
            return  null;
        }
        for (SysMsg sysmsg:
                sysMsgs  ) {

            User user = userMapper.selectByPrimaryKey(sysmsg.getSendId());
            avatar = user.getAvatar();
            SysMsgDto sysMsgDto = new SysMsgDto(avatar,sysmsg);
            sysMsgDtos.add(sysMsgDto);
        }

        return sysMsgDtos;
    }


    @Override
    public Integer getSysMsgNum(int recentId) {
        sysMsgs = sysMsgMapper.selectByRecentId(recentId);
        return sysMsgs.size();
    }

    @Override
    public Integer delectMsgById(int msgId) {
        return sysMsgMapper.deleteByPrimaryKey((long) msgId);
    }

}
