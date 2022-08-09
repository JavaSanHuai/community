package com.nowcoder.community.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.DiscussPost;

import com.nowcoder.community.service.DiscussPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
