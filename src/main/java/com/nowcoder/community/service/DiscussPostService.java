package com.nowcoder.community.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 17983
 * @date 2022/8/9 11:57
 * @ SJY
 */

public interface DiscussPostService extends IService<DiscussPost>
{
    /**
     * 功能描述: 分页查询
     * @Param: [userId, offset, limit]
     * @Return: java.util.List<com.nowcoder.community.entity.DiscussPost>
     */
    //offset起始行号 limit每页数据数
    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);

    /**
     * 功能描述: 查询帖子总数
     * @Param:
     * @Return:
     */
    //@Param给参数取别名 如果只有一个参数 并且在<if>里使用 则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId);
}
