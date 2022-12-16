package com.artemgggi.webshop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    public MvcConfig() {
        super();
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**",
//                        "/css/**",
//                        "/resources/**",
//                        "/js/**",
//                        "/images/**",
//                        "/api/**",
//                        "/font-awesome/**")
//
//                .addResourceLocations(
//                        "classpath:/static/css/",
//                        "classpath:/static/js/",
//                        "classpath:/static/images/",
//                        "classpath:/static/api/",
//                        "classpath:/resources/",
//                        "classpath:/static/font-awesome/");
//    }
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("src/main/resources");
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final LocaleChangeInterceptor localeChangeInterceptor =
                new LocaleChangeInterceptor();
        registry.addInterceptor(localeChangeInterceptor);
    }
}
