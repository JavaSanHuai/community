package com.nowcoder.community;

import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 17983
 * @date 2022/8/9 11:18
 * @ SJY
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class userMapperTest
{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    public void testSelectById(){
        User user = userMapper.selectById(101);
        System.out.println(user);
    }

    @Test
    public void testDiscussPostPageSel(){
        int i = discussPostMapper.selectDiscussPostRows(101);
        System.out.println("===================================="+i);

        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(101, 0, 5);
        for (DiscussPost discussPost : discussPosts)
        {
            System.out.println("====="+discussPost);
        }
    }
}
