package cn.xiao.zufang.config;

import cn.xiao.zufang.security.AuthProvider;
import cn.xiao.zufang.security.LoginAuthFailHandler;
import cn.xiao.zufang.security.LoginUrlEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@EnableWebSecurity
@EnableGlobalMethodSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * heep权限控制
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //资源访问权限
        http.authorizeRequests()

                .antMatchers("admin/login").permitAll()//管理员登录入口
                .antMatchers("static/**").permitAll()
                .antMatchers("user/login").permitAll()
                .antMatchers("admin/**").hasRole("ADMIN")
                .antMatchers("user/**").hasAnyRole("USER","ADMIN")
                .antMatchers("api/user/**").hasAnyRole("ADMIN","USER")//用户访问接口页面
                .and()
                .formLogin()
                .loginProcessingUrl("/login")//配置角色登录处理入口
                .failureHandler(authFailHandler())
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/logout/page")
                .deleteCookies("JSESSION")
                .invalidateHttpSession(true)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(loginUrlEntryPoint())
                .accessDeniedPage("/403");


        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();

    }
    @Autowired
    public void configGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {//只能有一个authentication实例
        authenticationManagerBuilder.authenticationProvider(authProvider()).eraseCredentials(true);
    }
//    @Bean
//    private AuthProvider authProvider(){
//        return new AuthProvider();
//    }
    @Bean
    public AuthProvider authProvider() {
    return new AuthProvider();
}

    @Bean
    public LoginUrlEntryPoint loginUrlEntryPoint(){
        return new LoginUrlEntryPoint("/user/login");
    }

    @Bean
    public LoginAuthFailHandler authFailHandler(){
        return new LoginAuthFailHandler(loginUrlEntryPoint());
    }

}
