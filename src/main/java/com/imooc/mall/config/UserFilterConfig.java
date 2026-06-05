package com.imooc.mall.config;

import com.imooc.mall.filter.UserFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * UserFilter 配置类
 *
 * 功能：
 * 1. 注册UserFilter到Spring容器
 * 2. 配置拦截的URL模式
 * 3. 设置过滤器执行顺序
 */
@Configuration
public class UserFilterConfig {

    /**
     * 注册UserFilter
     *
     * 配置说明：
     * - order设置为1，表示优先级较高（越小越优先）
     * - 拦截以下URL：
     *   /cart/*     购物车相关操作（需要认证）
     *   /order/*    订单相关操作（需要认证）
     *   /user/*     用户相关操作（某些需要认证）
     *
     * 白名单路径（在UserFilter中配置，无需认证）：
     * - /login          登录
     * - /register       注册
     * - /product/*      产品查询（公开）
     * - /category/*     分类查询（公开）
     * - /swagger        API文档
     *
     * @return FilterRegistrationBean UserFilter的注册Bean
     */
    @Bean
    public FilterRegistrationBean<UserFilter> userFilterConfig() {
        FilterRegistrationBean<UserFilter> filterRegistration = new FilterRegistrationBean<>();

        // 设置过滤器实例
        filterRegistration.setFilter(new UserFilter());

        // 设置URL匹配模式（需要认证的路径）
        filterRegistration.addUrlPatterns("/cart/*");      // 购物车
        filterRegistration.addUrlPatterns("/order/*");     // 订单
        filterRegistration.addUrlPatterns("/user/update"); // 用户更新（除了登录、注册）
        filterRegistration.addUrlPatterns("/product/admin/*");  // 产品管理
        filterRegistration.addUrlPatterns("/order/admin/*");    // 订单管理

        // 设置过滤器顺序（越小优先级越高）
        // 1表示首先执行，用于在其他过滤器之前进行认证
        filterRegistration.setOrder(1);

        return filterRegistration;
    }
}
