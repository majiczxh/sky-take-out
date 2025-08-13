package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.Employee;
import com.sky.entity.User;
import com.sky.result.PageResult;

public interface UserService {


    /**
     * 微信用户登录
     * @param userLoginDTO
     * @return
     */
    User wxLogIn(UserLoginDTO userLoginDTO);
}
