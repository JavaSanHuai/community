package com.nowcoder.community.controller;

import com.nowcoder.community.entity.DiscussPost;
import com.nowcoder.community.entity.Page;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.DiscussPostService;
import com.nowcoder.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 17983
 * @date 2022/8/9 17:17
 * @ SJY
 */
@RestController
public class HomeController
{
    @Autowired
    private DiscussPostService discussPostService;
    @Autowired
    private UserService userService;

    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public Model getIndexPage(Model model, Page page)
    {
        //方法调用之前 springmvc会自动实例化model和page 并将page注入model
        //所以 在thymeleaf中可以直接访问page对象的中的数据
        page.setRows(discussPostService.selectDiscussPostRows(0));
        page.setPath("/index");

        List<DiscussPost> posts = discussPostService.selectDiscussPosts(0, page.getOffset(),
                page.getLimit());

//        System.out.println(posts);
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if (posts != null)
        {
            for (DiscussPost post : posts)
            {
//                System.out.println("=========================="+post);
                HashMap<String, Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findUserById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return model;
    }
}
