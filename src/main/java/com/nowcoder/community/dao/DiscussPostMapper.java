package com.nowcoder.community.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 17983
 * @date 2022/8/8 16:42
 * @ SJY
 */

@Mapper
public interface DiscussPostMapper extends BaseMapper<DiscussPost>
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
