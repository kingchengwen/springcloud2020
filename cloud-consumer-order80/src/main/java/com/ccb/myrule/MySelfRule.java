package com.ccb.myrule;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MySelfRule {
    /**
     * 自定义负载均衡配置方式
     * 1、在主启动类Main方法上添加注解 @RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)；
     *      name 属性指定服务端的服务名
     *      configuration 属性指定自定义的负载均衡类名
     * 2、新建一个自定义负载均衡配置类，该类不能与主启动类在同一个@ComponentScan 自动扫描包下；
     * 3、如果取消自定义负载均衡策略，则注释掉主启动类上的注解即可。
     */

    @Bean
    public IRule myRule(){
        //定义为随机
        return new RandomRule();
    }
}
