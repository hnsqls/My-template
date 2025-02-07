package com.ls.template.service;

import com.ls.template.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 26611
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-02-07 14:39:18
*/
public interface UserService extends IService<User> {

    Long userRegister(String userAccount, String userPassword, String checkPassword);
}
