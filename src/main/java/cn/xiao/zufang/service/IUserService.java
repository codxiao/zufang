package cn.xiao.zufang.service;

import cn.xiao.zufang.entity.User;

/**
 * service
 */
public interface IUserService {
    User findUserByName(String userName);
}
