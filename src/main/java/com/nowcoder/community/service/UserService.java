package com.nowcoder.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nowcoder.community.entity.User;

import java.util.Map;

/**
 * @author 17983
 * @date 2022/8/9 17:05
 * @ SJY
 */
public interface UserService extends IService<User>
{
    /**
     * 功能描述: 根据id查询user
     * @Param: [id]
     * @Return: com.nowcoder.community.entity.User
     */
    User findUserById(int id);

    /**
     * 功能描述: 注册功能开发
     * @Param: [user]
     * @Return: java.util.Map<java.lang.String, java.lang.Object>
     */
    Map<String, Object> register(User user);

    /**
     * 功能描述: 注册 激活码认证
     * @Param: [userId, code]
     * @Return: int
     */
    int activation(int userId, String code);

//    Map<String, Object> login(String username, String password, int expiredSeconds);

//    void logout(String ticket);

    /**
     * 功能描述: 更新用户头像
     * @Param: [userId, headerUrl]
     * @Return: int
     */
    int updateHeader(int userId, String headerUrl);

    /**
     * 功能描述: 更改密码时 校验原密码是否正确
     * @Param:
     * @Return:
     */
    Map<String, Object> updatePassword(String password, String newPassword, User user);
}
