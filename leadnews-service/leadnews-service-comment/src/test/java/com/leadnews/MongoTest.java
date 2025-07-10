package com.leadnews;

import com.leadnews.comment.document.Comment;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews
 */
@SpringBootTest
public class MongoTest {

    @Autowired
    private MongoTemplate mongoTemplate;


    //相同的CRUD的操作
    @Test
    public void insert(){
        Comment comment = new Comment();
        comment.setUserId(2L);
        comment.setLikes(20);
        mongoTemplate.insert(comment);

    }

    @Test
    public void testFindOne() {
        Comment apComment = mongoTemplate.findById("661afa69e98abe28439951ca", Comment.class);
        System.out.println(apComment);
    }

    @Test
    public void testQuery() {
        //Query query = Query.query(Criteria.where("_id").is("5f7012e03ea2da5788227a6f"));
        Query query = Query.query(Criteria.where("likes").gt(8)); // select *from xxx where likes >20
       // query.with(Sort.by(Sort.Direction.DESC, "likes"));
        List<Comment> apComments = mongoTemplate.find(query, Comment.class);
        System.out.println(apComments);
    }

    @Test
    public void testDelete() {
        //delete from xxx where id=?
        mongoTemplate.remove(Query.query(Criteria.where("_id").is("5f7012e03ea2da5788227a6f")), Comment.class);
    }

    @Test
    public void testUpdate() {
        // update  xxx set yy=? where id=?

        Query query = Query.query(Criteria.where("_id").is("5f7015e63ea2da1618d173eb"));
        Update update = new Update().set("content", "itcast");
        mongoTemplate.updateMulti(query, update, Comment.class);
    }
}
