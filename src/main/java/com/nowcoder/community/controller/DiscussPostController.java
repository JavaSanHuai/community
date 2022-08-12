package com.nowcoder.community.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nowcoder.community.dao.CommentMapper;
import com.nowcoder.community.dao.DiscussPostMapper;
import com.nowcoder.community.entity.Comment;
import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.CommentService;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.HostHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static com.nowcoder.community.util.CommunityConstant.ENTITY_TYPE_COMMENT;
import static com.nowcoder.community.util.CommunityConstant.ENTITY_TYPE_POST;

/**
 * @author 17983
 * @date 2022/8/9 15:47
 * @ SJY
 */
@Controller
@Slf4j
@RequestMapping("/discuss")
public class DiscussPostController
{
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    /**
     * 功能描述: 新增帖子
     * @Param: [discussPost]
     * @Return: int
     */
    @RequestMapping(path = "/add", method = RequestMethod.POST)
    @ResponseBody
    public String addDiscussPost(String title, String content)
    {
        User user = hostHolder.getUser();
        if (user == null)
        {
            return CommunityUtil.getJSONString(403, "您还没有登录");
        }

        DiscussPost post = new DiscussPost();

        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());

        int i = discussPostService.insertDiscussPost(post);

        //报错以后统一处理
        return CommunityUtil.getJSONString(0, "发布成功");
    }

    /**
     * 功能描述: 查询帖子详情
     * @Param:
     * @Return:
     */
    @RequestMapping(path = "/detail/{discussPostId}", method = RequestMethod.GET)
    public String findDiscussPostById(@PathVariable("discussPostId") int discussPostId,
                                      Model model, Page page)
    {
        DiscussPost post = discussPostService.selectDiscussPostById(discussPostId);
        //帖子
        model.addAttribute("post", post);

        User user = userService.findUserById(post.getUserId());
        //作者
        model.addAttribute("user", user);

        //查评论分页信息
        log.info("==============current=================" + page.getCurrent());
        page.setLimit(5);
        page.setPath("/discuss/detail" + discussPostId);
        page.setRows(post.getCommentCount());

        //评论: 帖子的评论
        //回复: 评论的评论
        //评论列表
        List<Comment> commentList = commentService.selectCommentsByEntity(ENTITY_TYPE_POST,
                discussPostId, page.getOffset(), page.getLimit());

        log.info("==============List<Comment> commentList=================" + commentList);
        //评论显示列表 Vo列表
        List<Map<String, Object>> commentVoList = new ArrayList<>();

        if (commentList != null)
        {
            for (Comment comment : commentList)
            {
                log.info("======================comment===========================" + comment);
                //显示的评论 Vo评论
                Map<String, Object> commentVo = new HashMap<>();
                //评论
                commentVo.put("comment", comment);
                //作者
                commentVo.put("user", userService.findUserById(comment.getUserId()));
                //回复列表

                List<Comment> replyList =
                        commentService.selectCommentsByEntity(ENTITY_TYPE_COMMENT,
                                comment.getId(), 0, Integer.MAX_VALUE);

                log.info("==============List<Comment> replyList=================" + replyList);
                //回复Vo列表
                List<Map<String, Object>> replyVoList = new ArrayList<>();
                if (replyList != null)
                {
                    for (Comment reply : replyList)
                    {
                        log.info("=====================reply==================" + reply);
                        Map<String, Object> replyVo = new HashMap<>();
                        //存回复
                        replyVo.put("reply", reply);
                        //作者
                        replyVo.put("user", userService.findUserById(reply.getUserId()));
                        //回复目标
                        User target = reply.getTargetId() == 0 ? null :
                                userService.findUserById(reply.getTargetId());
                        replyVo.put("target", target);

                        replyVoList.add(replyVo);
                    }
                }

                log.info("=================replyVoList===========================" + replyVoList);
                commentVo.put("replys", replyVoList);

                //回复数量
                int replyCount = commentService.selectCountByEntity(ENTITY_TYPE_COMMENT,
                        comment.getId());
                commentVo.put("replyCount", replyCount);

                commentVoList.add(commentVo);
                log.info("================commentVoList========================" + commentVoList);
            }
        }

        model.addAttribute("comments", commentVoList);

        return "/site/discuss-detail";
    }






//    /**
//     * 功能描述: 分页查询
//     * @Param: [userId, offset, limit]
//     * @Return: java.util.List<com.nowcoder.community.entity.DiscussPost>
//     */
//    //offset起始行号 limit每页数据数
//    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit)
//    {
//        return null;
//    }
//
//    /**
//     * 功能描述: 查询帖子总数
//     * @Param:
//     * @Return:
//     */
//    //@Param给参数取别名 如果只有一个参数 并且在<if>里使用 则必须加别名
//    int selectDiscussPostRows(@Param("userId") int userId)
//    {
//        return 0;
//    }

}
