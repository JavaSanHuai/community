package com.nowcoder.community.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nowcoder.community.dao.UserMapper;
import com.nowcoder.community.entity.User;
import com.nowcoder.community.service.UserService;
import com.nowcoder.community.util.CommunityConstant;
import com.nowcoder.community.util.CommunityUtil;
import com.nowcoder.community.util.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author 17983
 * @date 2022/8/9 17:05
 * @ SJY
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService
{
    @Autowired
    private UserMapper userMapper;

    //注入邮件客户端 在工具类里 用于发送邮件
    @Autowired
    private MailClient mailClient;

    //注入模板引擎
    @Autowired
    private TemplateEngine templateEngine;

    //注入域名
    @Value("${community.path.domain}")
    private String domain;

    //注入项目名
    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 功能描述: 根据id查询user
     * @Param: [id]
     * @Return: com.nowcoder.community.entity.User
     */
    @Override
    public User findUserById(int id)
    {
        //用的mybatisplus的方法
        User user = userMapper.selectById(id);
        return user;
    }

    /**
     * 功能描述: 注册功能开发
     * @Param: [user]
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     */
    @Override
    public Map<String, Object> register(User user)
    {
        //用于存储异常信息 返回值为空表示 注册无异常 注册成功
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if (user == null)
        {
            throw new IllegalArgumentException("参数不能为空!");
        }
        // StringUtils.isBlank(user.getUsername()) 判断是否为空
        if (StringUtils.isBlank(user.getUsername()))
        {
            map.put("usernameMsg", "账号不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword()))
        {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getEmail()))
        {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }

        // 验证账号
        User u = userMapper.selectByName(user.getUsername());
        if (u != null)
        {
            map.put("usernameMsg", "该账号已存在!");
            return map;
        }

        // 验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null)
        {
            map.put("emailMsg", "该邮箱已被注册!");
            return map;
        }

        // 注册用户
        //salt用于拼接密码md5加密
        user.setSalt(CommunityUtil.generateUUID()
                                  .substring(0, 5));
        user.setPassword(CommunityUtil.md5(user.getPassword() + user.getSalt()));
        //0普通用于
        user.setType(0);
        //0未激活
        user.setStatus(0);
        //设置账户激活码
        user.setActivationCode(CommunityUtil.generateUUID());
        //设置随机头像
        user.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",
                new Random().nextInt(1000)));
        user.setCreateTime(new Date());
        userMapper.insertUser(user);

        // 激活邮件
        //thymeleaf提供的类 理解为容器 用于存储需要发送的邮件信息
        Context context = new Context();
        //设置目标用户邮箱地址
        context.setVariable("email", user.getEmail());
        // http://localhost:8080/community/activation/101/code
        // user.getActivationCode() 激活码
        String url =
                domain + contextPath + "/activation/" + user.getId() + "/" + user.getActivationCode();
        context.setVariable("url", url);
        //模板引擎用于 将需要发送的信息 按照提供的activation.html文件的内容格式发送
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(user.getEmail(), "激活账号", content);

        return map;
    }

    /**
     * 功能描述: 注册 激活码认证
     * @Param: [userId, code]
     * @Return: int
     */
    @Override
    public int activation(int userId, String code)
    {
        User user = userMapper.selectById(userId);
        //如果等于1 表示已经激活
        if (user.getStatus() == 1)
        {
            return CommunityConstant.ACTIVATION_REPEAT;
        }
        else if (user.getActivationCode()
                     .equals(code))
        {
            userMapper.updateStatus(userId, 1);
            return CommunityConstant.ACTIVATION_SUCCESS;
        }
        else
        {
            return CommunityConstant.ACTIVATION_FAILURE;
        }
    }

    /**
     * 功能描述: 更新用户头像
     * @Param: [userId, headerUrl]
     * @Return: int
     */
    @Override
    public int updateHeader(int userId, String headerUrl)
    {
        int i = userMapper.updateHeader(userId, headerUrl);
        return i;
    }

    /**
     * 功能描述: 更改密码时 校验原密码是否正确
     * @Param:
     * @Return:
     */
    @Override
    public Map<String, Object> updatePassword(String password, String newPassword, User user)
    {
        Map<String, Object> map = new HashMap<>();
        User user2 = userMapper.selectById(user.getId());
        password=CommunityUtil.md5(password+user.getSalt());
        if (user2.getPassword()
                 .equals(password))
        {
            newPassword = CommunityUtil.md5(newPassword + user.getSalt());
            userMapper.updatePassword(user.getId(), newPassword);
            return map;
        }
        else
        {
            map.put("passwordMsg", "输入密码错误！");
            return map;
        }
    }

//    @Override
//    public Map<String, Object> login(String username, String password, int expiredSeconds)
//    {
//        return null;
//    }

//    @Override
//    public void logout(String ticket)
//    {
//
//    }
}
