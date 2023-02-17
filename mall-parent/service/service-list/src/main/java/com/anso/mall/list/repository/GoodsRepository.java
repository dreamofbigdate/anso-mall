package com.anso.mall.list.repository;

import com.anso.mall.model.list.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author sc
 * @Description //TODO
 * @create 2023-02-06 9:27
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods, Long> {
}
