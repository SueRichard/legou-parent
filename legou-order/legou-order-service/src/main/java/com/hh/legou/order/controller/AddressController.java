package com.hh.legou.order.controller;

import com.hh.legou.core.controller.BaseController;
import com.hh.legou.order.config.TokenDecode;
import com.hh.legou.order.po.Address;
import com.hh.legou.order.service.IAddressService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

/**
 * @author hh
 * @version 1.0
 * @time 24/12/2023 16:11
 */
@RestController
@RequestMapping("/address")
public class AddressController extends BaseController<IAddressService, Address> {

    @Autowired
    private TokenDecode tokenDecode;

    /**
     * 查询指定用户的全部收货地址
     *
     * @param entity
     * @return
     */
    @ApiOperation(value = "查询", notes = "根据实体条件查询")
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    @Override
    public List<Address> list(Address entity) {
        String username = null;
        try {
            username = tokenDecode.getUserInfo().get("user_name");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        entity.setUsername(username);
        return service.list(entity);
    }
}
