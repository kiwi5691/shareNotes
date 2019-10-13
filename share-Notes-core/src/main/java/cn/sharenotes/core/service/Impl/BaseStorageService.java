package cn.sharenotes.core.service.Impl;

import cn.sharenotes.db.domain.Attachments;
import cn.sharenotes.db.domain.AttachmentsExample;
import cn.sharenotes.db.mapper.AttachmentsMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.Date;
import java.util.List;

@Service
public class BaseStorageService {
    @Autowired
    private AttachmentsMapper storageMapper;

    public void deleteByKey(String key) {
        AttachmentsExample example = new AttachmentsExample();
        example.or().andKeyEqualTo(key);
        storageMapper.logicalDeleteByExample(example);
    }

    public void add(Attachments storageInfo) {
        storageInfo.setAddTime(new Date());
        storageInfo.setUpdateTime(new Date());
        storageMapper.insertSelective(storageInfo);
    }

    public Attachments findByKey(String key) {

        return storageMapper.selectOneByKey(key);
    }

    public int update(Attachments storageInfo) {
        storageInfo.setUpdateTime(new Date());
        return storageMapper.updateByPrimaryKeySelective(storageInfo);
    }

    public Attachments findById(Integer id) {
        return storageMapper.selectByPrimaryKey(id);
    }

    public List<Attachments> querySelective(String key, String name, Integer page, Integer limit, String sort, String order) {
        AttachmentsExample example = new AttachmentsExample();
        AttachmentsExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(key)) {
            criteria.andKeyEqualTo(key);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, limit);
        return storageMapper.selectByExample(example);
    }
}
