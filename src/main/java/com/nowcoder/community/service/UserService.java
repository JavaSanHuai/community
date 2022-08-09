package com.nowcoder.community.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nowcoder.community.entity.User;

/**
 * @author 17983
 * @date 2022/8/9 17:05
 * @ SJY
 */
public interface UserService extends IService<User>
{
    User findUserById(int id);
}
