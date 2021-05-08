package com.xxl.controller;

import com.xxl.model.UserBcoinWithdrawModel;
import com.xxl.service.UserBcoinWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
public class ProviderController {

    @Autowired
    UserBcoinWithdrawService service;

    @GetMapping("paycore/test")
    public Object test1() throws Exception {

        List<UserBcoinWithdrawModel> devData = service.getDevData();
        /*List<UserBcoinWithdrawModel> testData = service.getTestData();

        devData.addAll(testData);*/
        return new Date().toString() ;
    }



}
