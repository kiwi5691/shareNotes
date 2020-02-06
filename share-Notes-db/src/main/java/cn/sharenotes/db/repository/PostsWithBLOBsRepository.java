package cn.sharenotes.db.repository;

import cn.sharenotes.db.domain.PostsWithBLOBs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostsWithBLOBsRepository extends ElasticsearchRepository<PostsWithBLOBs, Integer> {
}
