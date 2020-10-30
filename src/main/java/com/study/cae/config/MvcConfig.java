//package com.community.cae.config;
//
//import com.community.cae.filter.LoginHandlerInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class MvcConfig implements WebMvcConfigurer {
//
//    /**
//     *
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
//        //本地
////        registry.addResourceHandler("/upload/**").addResourceLocations("file:///D:\\myfile\\upload\\");
////        registry.addResourceHandler("/upload/ueditor/**").addResourceLocations("file:///D:\\myfile\\upload\\ueditor\\");
//      //线上
//        registry.addResourceHandler("/upload/**").addResourceLocations("file:////home/upload/");
//        registry.addResourceHandler("/upload/ueditor/**").addResourceLocations("file:////home/upload/ueditor/");
//    }
//
//    @Bean()
//    public LoginHandlerInterceptor loginHandlerInterceptor() {
//        return new LoginHandlerInterceptor();
//    }
//
//    /**
//     * 配置拦截器
//     *
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginHandlerInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/manage/login","/api/**","/cfra")
//                .excludePathPatterns("/static/**","/upload/**","/ueditor/**");
//    }
//
//}
