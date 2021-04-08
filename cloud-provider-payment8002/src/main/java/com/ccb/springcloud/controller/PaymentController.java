package com.ccb.springcloud.controller;

import com.ccb.springcloud.entities.CommonResult;
import com.ccb.springcloud.entities.Payment;
import com.ccb.springcloud.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping("/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        logger.info("******插入结果："+ result);
        if (result > 0){
            return new CommonResult(200,"插入数据库成功! serverPort: " + serverPort,result);
        }else {
            return new CommonResult(400,"插入数据库失败!",null);
        }
    }

    @GetMapping("/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        logger.info("******查询结果："+ result);
        if (result != null){
            return new CommonResult(200,"查询数据库成功! serverPort: " + serverPort,result);
        }else {
            return new CommonResult(400,"查询数据库失败!，查询Id"+ id,null);
        }
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }
}
