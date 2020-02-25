package io.gitee.zhousiwei.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 测试实体
 *
 * @author Created by 試毅-思伟 on 2018/8/16
 */
@Document(indexName = "test", type = "test_type")
@Data
public class Test {
    /** 唯一标识ID */
    @Id
    private Long id;

    /** 姓名 */
    @Field(type = FieldType.Text)
    private String name;

    /** 阅读数量 */
    private Integer readNumber;

    /** 内容 */
    private String content;

    /** 创建时间 */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date createTime;

    /** 更新时间 */
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date updateTime;

}
