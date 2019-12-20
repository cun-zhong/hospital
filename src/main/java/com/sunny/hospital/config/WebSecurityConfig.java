package com.sunny.hospital.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Author: 孙宇豪
 * @Date: 2019/7/26 17:27
 * @Description: TODO 配置类 configure方法配置从内存中加载角色和权限
 * @Version 1.0
 */
@Configuration
//注解开启Spring Security的功能。
@EnableWebSecurity
/*
 * 开启Spring方法级安全
 *（1）prePostEnabled :决定Spring Security的前注解是否可用 [@PreAuthorize,@PostAuthorize,..]
 *（2）secureEnabled : 决定是否Spring Security的保障注解 [@Secured] 是否可用。
 *（3）jsr250Enabled ：决定 JSR-250 annotations 注解[@RolesAllowed..] 是否可用。
 */
@EnableGlobalMethodSecurity(prePostEnabled=true)//会拦截注解了@PreAuthrize注解的配置.
//继承WebSecurityConfigurerAdapter，并重写它的方法来设置一些web安全的细节。
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    /**
     * （1）如何开启持久化token方式：可以使用and().rememberMe()进行开启记住我，然后指定tokenRepository（），
     * 即指定了token持久化方式。
     * （2）tokenRepository怎么实现：这里我们可以使用Spring Security提供的JdbcTokenRepositoryImpl即可
     * 这里只需要配置一个数据源即可。
     * （3）持久化token的数据保存在哪里：这里的数据是保存在persistent_logins表中。
     * （4）persistent_logins表生成方式：有两种方式可以生成，第一种就是手动方式，根据表结构自己创建表；
     * 第二种方式就是使用JdbcTokenRepositoryImpl配置为自动创建，这种方式虽然会自动生成，
     * 但是存在的一个小问题就是第二次运行程序的就会保存了，因为persistent_logins已经存在了，不知道底层为什么就不能判断，
     * 或者处理下异常呐？所以我的使用方式就是第一次执行的时候，打开配置，生成表之后，注释掉配置。
     * */
    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepositoryImpl =new JdbcTokenRepositoryImpl();
        jdbcTokenRepositoryImpl.setDataSource(dataSource);
        //自动创建数据库表:persistent_logins，使用一次后注释掉，不然会报错
//        jdbcTokenRepositoryImpl.setCreateTableOnStartup(true);
        return jdbcTokenRepositoryImpl;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new MyPasswordEncoder();
    }

    //自定义用户验证
    @Bean
    public UserDetailsService userDetailsService() {
        //获取用户账号密码及权限信息
        return new CustomUserDetailService();
    }

    /**
     * 配置认证方式等
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 加入自定义的安全认证
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /*
     *  有3种方式可以实现动态权限控制
     * （1）扩展access()的SpEL表达式
     * （2）自定义AccessDecisionManager
     * （3）自定义Filter
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        //定义需保护的URL
        http.authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
                .antMatchers("/login").permitAll()//设置白名单 设置所有人都可以访问登录页面
                .antMatchers("/","login").permitAll()
                .antMatchers("/genCaptcha").permitAll()//验证码
                .antMatchers("/res/**/*.{js,html,css}").permitAll()
                .antMatchers("/static/**/*").permitAll()
                /*扩展access()的SpEL表达式 添加access配置*/
//                .anyRequest().access("@authService.canAccess(request,authentication)")
                .anyRequest().authenticated()  // 任何请求,登录后可以访问
                //设置记住我 token持久化
                .and().rememberMe().tokenRepository(tokenRepository()).tokenValiditySeconds(1209600) .userDetailsService(customUserDetailService)

                .and().csrf().disable()
                .formLogin().loginPage("/login")
                .and().logout().logoutSuccessUrl("/index")
                .and().sessionManagement().maximumSessions(1)

        ;


    }



}
