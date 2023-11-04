package com.hh.legou.security.service;

import com.hh.legou.core.service.ICrudService;
import com.hh.legou.security.po.Role;
import com.hh.legou.security.po.User;

import java.util.List;

/**
 * @Title:
 * @Description: 
 *
 * @Copyright 2019 hh - Powered By 雪松
 * @Author: hh
 * @Date:  2019/10/9
 * @Version V1.0
 */
public interface IRoleService extends ICrudService<Role> {

    /**
     * 查询角色的用户
     * @param id
     * @return
     */
    public List<User> selectUserByRole(Long id);

}
