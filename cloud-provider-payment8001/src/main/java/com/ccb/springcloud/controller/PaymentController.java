package com.ccb.springcloud.controller;

import com.ccb.springcloud.entities.CommonResult;
import com.ccb.springcloud.entities.Payment;
import com.ccb.springcloud.service.PaymentService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;


@RestController
public class PaymentController {

    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    @Resource
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;


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

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String element : services) {
            logger.info("******查询结果："+ element);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance: instances) {
            logger.info(instance.getServiceId()+ "\t" + instance.getHost() +"\t"+ instance.getPort() +"\t"+ instance.getUri());
        }
        return discoveryClient;
    }

    @GetMapping("/payment/lb")
    public String getPaymentLB(){
        return serverPort;
    }

    @GetMapping("/payment/feign/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    @GetMapping("/payment/zipkin")
    public String getPaymentZipkin(){
        return "PaymentZipkin fall back";
    }
}
