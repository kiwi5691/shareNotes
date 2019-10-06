package cn.sharenotes.core.service;

import cn.sharenotes.db.domain.User;
import cn.sharenotes.db.model.vo.UserVo;

import java.util.List;

/**
 * @auther kiwi
 */
public interface UserService {
    public User findById(Integer userId);
    public UserVo findUserVoById(Integer userId);
    public User queryByOid(String openId);
    public void add(User user);
    public int updateById(User user);
    public List<User> querySelective(String username, String mobile, Integer page, Integer size, String sort, String order);
    public int count();
    public List<User> queryByUsername(String username);
    public boolean checkByUsername(String username);
    public List<User> queryByMobile(String mobile);
    public List<User> queryByOpenid(String openid);}
