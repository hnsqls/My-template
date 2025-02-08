package com.ls.template.controller;


import com.ls.template.common.BaseResponse;
import com.ls.template.common.ErrorCode;
import com.ls.template.common.ResultUtils;
import com.ls.template.exception.BusinessException;
import com.ls.template.exception.ThrowUtils;
import com.ls.template.model.dto.user.UserRegisterRequest;
import com.ls.template.model.entity.User;
import com.ls.template.service.UserService;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    //用户注册
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest){
        if (userRegisterRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
           throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

       Long result =  userService.userRegister(userAccount, userPassword, checkPassword);

        return ResultUtils.success(result);
    }


}
