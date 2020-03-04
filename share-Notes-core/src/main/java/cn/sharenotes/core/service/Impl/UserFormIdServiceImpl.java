package cn.sharenotes.core.service.Impl;

import cn.sharenotes.core.service.UserFormIdService;
import cn.sharenotes.db.domain.UserFormid;
import cn.sharenotes.db.domain.UserFormidExample;
import cn.sharenotes.db.mapper.UserFormidMapper;
import javafx.print.Collation;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserFormIdServiceImpl implements UserFormIdService {

    @Resource
    private UserFormidMapper userFormidMapper;

    @Override
    public UserFormid queryByOpenId(String openId) {
        UserFormidExample example = new UserFormidExample();
        //符合找到该用户记录，且可用次数大于1，且还未过期
        example.or().andOpenidEqualTo(openId).andExpireTimeGreaterThan(new Date());
        List<UserFormid> userFormidList =userFormidMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(userFormidList)){
            return null;
        }
        Optional<UserFormid> userFormid = Optional.ofNullable(userFormidList.get(0));
        return userFormid.orElse(null);
    }

    @Override
    public void updateUserFormId(UserFormid userFormid) {
        //更新或者删除缓存
        if (userFormid.getIsprepay() && userFormid.getUseamount() > 1) {
            userFormid.setUseamount(userFormid.getUseamount() - 1);
            userFormidMapper.updateByPrimaryKey(userFormid);
        } else {
            userFormidMapper.deleteByPrimaryKey(userFormid.getId());
        }
    }

    @Override
    public void addUserFormId(UserFormid userFormid) {
        userFormidMapper.insertSelective(userFormid);
    }
}
