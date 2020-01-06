package cn.sharenotes.wxapi.service.esService.respo;

import cn.sharenotes.db.domain.Posts;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface postRepository extends ElasticsearchRepository<Posts,Long> {
}
