package com.ls.template.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ls.template.common.ErrorCode;
import com.ls.template.exception.BusinessException;
import com.ls.template.exception.ThrowUtils;
import com.ls.template.model.entity.User;
import com.ls.template.service.UserService;
import com.ls.template.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

/**
* @author hnsqls
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    /**
     * 盐值，混淆密码
     */
    public static final String SALT = "hnsqls";

    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        ThrowUtils.throwIf(StringUtils.isAnyBlank(userAccount, userPassword, checkPassword), ErrorCode.PARAMS_ERROR, "参数为空");
        ThrowUtils.throwIf((userAccount.length() < 4), ErrorCode.PARAMS_ERROR, "用户账号过短");
        ThrowUtils.throwIf((userPassword.length() < 8 || checkPassword.length() < 8), ErrorCode.PARAMS_ERROR, "用户密码过短");
        // 密码和校验密码相同
        ThrowUtils.throwIf(!userPassword.equals(checkPassword), ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        synchronized (userAccount.intern()) {
            // 账户不能重复
//            LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
//            queryWrapper1.eq(User::getUserAccount, userAccount);
//            queryWrapper1.select(User::getId, User::getUserAccount, User::getUserPassword);
//            //执行queryWrapper
//            this.getOne(queryWrapper1);
//            System.out.println("----------------------测试---------");

            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.lambda()
                            .eq(User::getUserAccount, userAccount)
                    .eq(User::getUserPassword, userPassword);
            long count = this.baseMapper.selectCount(queryWrapper);
            if (count > 0) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
            }
            // 2. 加密
            String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
            // 3. 插入数据
            User user = new User();
            user.setUserAccount(userAccount);
            user.setUserPassword(encryptPassword);
            boolean saveResult = this.save(user);
            ThrowUtils.throwIf(!saveResult, ErrorCode.OPERATION_ERROR, "注册失败");
            return user.getId();
        }

    }
}




