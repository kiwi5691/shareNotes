package cn.sharenotes.db.repository;

import cn.sharenotes.db.domain.PostsWithBLOBs;
import cn.sharenotes.db.domain.index.PostsIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface PostsIndexRepository extends ElasticsearchRepository<PostsIndex, Integer> {
}
