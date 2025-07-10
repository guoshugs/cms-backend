package com.leadnews.comment.repository;

import com.leadnews.comment.document.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @Repository @service @controller
 * MongoRepository: springDataJpa
 * jdbc
 * hibernate: 操作java内存的对象来操作数据表数据,Api复杂, 高并发情况不好（jvm中大量的对象，频繁的gc STW）
 * ibatis(1代) -> mybatis(2代) 封装jdbc，使sql与java分离,解耦
 * springdataJPA 封装了JPA规范，由Hibernate实现。通过方法名来定义查询语句，同时还封装crud与排序和分页, 通过注解支持原生的sql,
 * <Comment,String>  comment 用于指定 要操作的POJO 对应的collection； String 指定 主键的数据类型
 * @version 1.0
 * @description 标题
 * @package com.leadnews.comment.repository
 */
public interface CommentRepository extends MongoRepository<Comment,String> {
    //我要根据userId=2的值进行查询 select * from xx where userId=2 order by likes desc
    List<Comment> findByUserIdOrderByLikesDesc(Integer userId);
}
