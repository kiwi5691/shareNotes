package cn.sharenotes.wxapi.system.dao.base;

import cn.sharenotes.wxapi.system.entity.base.BaseModel;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author kiwi
 * @date 2019/11/24 15:56
 */

@NoRepositoryBean
public interface BaseRepo<T extends BaseModel> extends PagingAndSortingRepository<T, Long>, JpaSpecificationExecutor<T> {
}