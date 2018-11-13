package com.xudaning.controller;

import com.xudaning.domain.User;
import com.xudaning.service.IAccountService;
import com.xudaning.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value = "/account")
public class AccountController {

    @Autowired
    private IAccountService accountService;

    @RequestMapping(value = "/accountMoney", method = RequestMethod.GET)
    @ResponseBody
    public void accountMoney(String transferUser, String receiveUser, Integer salary) {
        accountService.accountMoney(transferUser, receiveUser, salary);
    }
}
