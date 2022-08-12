package com.nowcoder.community.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;

import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.util.SensitiveFilter;
import com.nowcoder.community.util.SensitiveFilterCopy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import javax.xml.ws.Action;
import java.util.List;

/**
 * @author 17983
 * @date 2022/8/9 12:14
 * @ SJY
 */
@Slf4j
@Service
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostService
{
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private SensitiveFilter sensitiveFilter;

    /**
     * 功能描述: 分页查询
     * @Param: [userId, offset, limit]
     * @Return: java.util.List<com.nowcoder.community.entity.DiscussPost>
     */
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit)
    {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(userId, offset,
                limit);
        return discussPosts;
    }

    /**
     * 功能描述: 查询用户帖子数量
     * @Param: [userId]
     * @Return: int
     */
    public int findDiscussPostRows(int userId)
    {
        int i = discussPostMapper.selectDiscussPostRows(userId);
        return i;
    }

    /**
     * 功能描述: 分页查询
     * @Param: [userId, offset, limit]
     * @Return: java.util.List<com.nowcoder.community.entity.DiscussPost>
     */
    @Override
    public List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit)
    {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscussPosts(userId, offset,
                limit);
        return discussPosts;
    }

    /**
     * 功能描述: 查询用户帖子数量
     * @Param: [userId]
     * @Return: int
     */
    @Override
    public int selectDiscussPostRows(int userId)
    {
        int i = discussPostMapper.selectDiscussPostRows(userId);
        return i;
    }

    /**
     * 功能描述: 新增帖子
     * @Param: [discussPost]
     * @Return: int
     */
    @Override
    public int insertDiscussPost(DiscussPost discussPost)
    {
        if(discussPost==null){
            throw new IllegalArgumentException("参数不能为空");
        }
        log.info("====================================="+discussPost.getContent());

        //转义html标记
        discussPost.setTitle(HtmlUtils.htmlEscape(discussPost.getTitle()));
        discussPost.setContent(HtmlUtils.htmlEscape(discussPost.getContent()));
        //过滤敏感词
        discussPost.setTitle(sensitiveFilter.filter(discussPost.getTitle()));
        discussPost.setContent(sensitiveFilter.filter(discussPost.getContent()));

        log.info("====================================="+discussPost.getContent());
        int insert = discussPostMapper.insert(discussPost);
        return insert;
    }

    /**
     * 功能描述: 查询帖子详情
     * @Param: [id]
     * @Return: com.nowcoder.community.entity.DiscussPost
     */
    @Override
    public DiscussPost selectDiscussPostById(int id)
    {
        DiscussPost post = discussPostMapper.selectById(id);
        return post;
    }

    /**
     * 功能描述: 更新帖子评论数量
     * @Param: [id, commentCount]
     * @Return: int
     */
    public int updateCommentCount(int id, int commentCount)
    {
        DiscussPost post = discussPostMapper.selectById(id);
        post.setCommentCount(commentCount);
        int i = discussPostMapper.updateById(post);
        return i;
    }
}
