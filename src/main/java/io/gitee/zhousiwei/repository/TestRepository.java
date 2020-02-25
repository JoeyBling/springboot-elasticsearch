package io.gitee.zhousiwei.repository;

import io.gitee.zhousiwei.entity.Test;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 测试数据访问仓库接口
 *
 * @author Created by 試毅-思伟 on 2018/8/16
 */
public interface TestRepository extends ElasticsearchRepository<Test, Long> {

}
