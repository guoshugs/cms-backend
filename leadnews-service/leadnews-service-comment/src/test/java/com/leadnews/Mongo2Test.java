package com.leadnews;

import com.leadnews.comment.document.Comment;
import com.leadnews.comment.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @version 1.0
 * @description 标题
 * @package com.leadnews
 */
@SpringBootTest
public class Mongo2Test {


    @Autowired
    private CommentRepository commentRepository;



    //相同的CRUD的操作
    @Test
    public void save(){
        Comment entity = new Comment();
        entity.setUserId(4L);
        entity.setLikes(89);
        commentRepository.save(entity);
    }

    //查询
    @Test
    public void findAll(){
        List<Comment> all = commentRepository.findAll();
        for (Comment comment : all) {
            System.out.println(comment.getLikes());
        }
    }

    //删除
    @Test
    public void delete(){
        Comment contition = new Comment();
        contition.setId("661afe53ae082828f7ef5a21");
        contition.setUserId(4L);

        // delete from xxx where id=? and userId=?
        commentRepository.delete(contition);
    }
    //条件查询
    @Test
    public void find(){
        List<Comment> byUserIdOrderByLikesDesc = commentRepository.findByUserIdOrderByLikesDesc(2);
        for (Comment comment : byUserIdOrderByLikesDesc) {
            System.out.println(comment);
        }
    }

}