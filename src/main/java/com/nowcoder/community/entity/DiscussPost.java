package com.nowcoder.community.entity;

import lombok.Data;
import java.util.Date;

/**
 * @author 17983
 * @date 2022/8/8 16:30
 * @ SJY
 */
//帖子类
@Data
public class DiscussPost
{
    private int id;
    private int userId;
    //帖子标题
    private String title;
    //帖子内容
    private String content;
    //帖子类型 0表示普通 1表示置顶
    private int type;
    //帖子状态 0正常 1精华 2拉黑
    private int status;
    private Date createTime;
    //冗余字段 存的评论数量
    private int commentCount;
    //帖子分数
    private double score;
}
