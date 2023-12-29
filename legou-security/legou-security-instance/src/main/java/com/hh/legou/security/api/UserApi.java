package com.hh.legou.security.api;

import com.hh.legou.security.po.Role;
import com.hh.legou.security.po.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping(value = "/user")
public interface UserApi {

    @GetMapping("/get/{userName}")
    public User getByUserName(@PathVariable("userName") String userName);

    @GetMapping("/select-roles/{id}")
    public List<Role> selectRolesByUserId(@PathVariable("id") Long id);

    @GetMapping("/add-point")
    void addPoint(@RequestParam("point") Long point, @RequestParam("username") String username);

}
