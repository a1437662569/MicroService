package com.xxl.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProviderServiceImpl implements ProviderService {
    @Override
    public String getPaymentById(int i) {
        return "777";
        /*String res= "参数i"+i+",时间："+ new Date().toString();
        log.info(res);
        return "参数i"+i+",时间："+ new Date().toString();*/
    }
}
