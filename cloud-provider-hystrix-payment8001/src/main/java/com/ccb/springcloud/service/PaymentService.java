package com.ccb.springcloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    public String paymentInfoOk(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "    paymentInfoOk,id:" + id + "    O(∩_∩)O哈哈~";
    }
    // 服务熔断
    @HystrixCommand(fallbackMethod = "paymentInfoTimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfoTimeout(Integer id){
        int timeNumber = 2000;
        try {
            TimeUnit.MILLISECONDS.sleep(timeNumber);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + "    paymentInfoTimeout,id:" + id + "   O(∩_∩)O哈哈~" + "耗时(秒)" + timeNumber;
    }

    public String paymentInfoTimeoutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "    8001系统繁忙，请稍后再试，id：  " + id + "\t" +"o(╥﹏╥)o";
    }

    // 服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreakFallback" ,commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),   //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),  //请求次数
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds",value = "10000"),    //时间窗口期
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60") //失败率达到多少后跳闸
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("id 不能为负数");
        }
        String serialNumber = UUID.randomUUID().toString();

        return  Thread.currentThread().getName() + "\t" + "调用流水号为" + serialNumber ;
    }

    public String paymentCircuitBreakFallback(@PathVariable("id") Integer id){
        return "id 不能为负数，请稍后再试 😭 ,id:"+id;
    }
}
