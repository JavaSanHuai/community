package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.service.DiscussPostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 17983
 * @date 2022/8/9 15:47
 * @ SJY
 */
@RestController
@Slf4j
@RequestMapping("/discussPost")
public class DiscussPostController
{
    @Autowired
    private DiscussPostService discussPostService;

    /**
     * 功能描述: 分页查询
     * @Param: [userId, offset, limit]
     * @Return: java.util.List<com.nowcoder.community.entity.DiscussPost>
     */
    //offset起始行号 limit每页数据数
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit)
    {
        return null;
    }

    /**
     * 功能描述: 查询帖子总数
     * @Param:
     * @Return:
     */
    //@Param给参数取别名 如果只有一个参数 并且在<if>里使用 则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId)
    {
        return 0;
    }
}
