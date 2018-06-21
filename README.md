1.springboot后台搭建

使用idea的Maven构建。pom文件中的各种依赖应该和项目一致否则会报错。不要给自己制造学习的障碍。
构建完成，首先要建立一个配置包config存放配置类JPAconfig。集成springboot jpa。
JPAconfig类：在类中要配置数据源，实体类的管理管理工厂。和管理实务的bean
package cn.xiao.zufang.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(basePackages = "cn.xiao.zufang.repository") //能够扫描我们的仓库类
@EnableTransactionManagement  //使之允许事务管理
public class JPAConfig {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        HibernateJpaVendorAdapter japVendor = new HibernateJpaVendorAdapter();
        japVendor.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.setJpaVendorAdapter(japVendor);
        entityManagerFactory.setPackagesToScan("cn.xiao.zufang.entity");
        return entityManagerFactory;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }
}

然后在application.properties中添加属性

#datasource
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/zufang
spring.datasource.username=root
spring.datasource.password=admin

#jpa
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=validate//仅仅验证
logging.level.org.hibernate.sql=debug //设置sql级别为debug
spring.session.store-type=hash_map //session的存储类型为
security.basic.enabled=false //关闭http的基本验证
至此我们启动springboot就可以看到项目正常运行了。

但是我们不能因为项目跑通就认为一切都正确了。还应该执行单元测试。
测试之前建立表的结构：
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `name` varchar(32) DEFAULT NULL COMMENT '用户名',
  `email` varchar(32) DEFAULT NULL COMMENT '电子邮箱',
  `phone_number` varchar(15) NOT NULL COMMENT '电话号码',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `status` int(2) unsigned NOT NULL DEFAULT '0' COMMENT '用户状态 0-正常 1-封禁',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '用户账号创建时间',
  `last_login_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '上次登录时间',
  `last_update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '上次更新记录时间',
  `avatar` varchar(255) DEFAULT NULL COMMENT '头像',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_on_phone` (`phone_number`) USING BTREE COMMENT '用户手机号',
  UNIQUE KEY `index_on_username` (`name`) USING BTREE COMMENT '用户名索引',
  UNIQUE KEY `index_on_email` (`email`) USING BTREE COMMENT '电子邮箱索引'
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COMMENT='用户基本信息表';

-- ----------------------------
--  Records of `user`
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES ('1', 'waliwali', 'wali@wali.com', '15111111111', '6fd1aad88b038aeecd9adeccc92b0bd1', '1', '2017-08-25 15:18:20', '2017-08-25 12:00:00', '2017-11-26 10:29:02', 'http://7xo6gy.com1.z0.glb.clouddn.com/99ff568bd61c744bf31185aeddf13580.png'), ('2', 'admin', 'admin@imooc.com', '1388888888', '55b3d0936a3fb63168d57a6bda0ddbbf', '1', '2017-08-27 09:07:05', '2017-08-27 09:07:07', '2017-10-21 15:03:57', 'http://7xo6gy.com1.z0.glb.clouddn.com/99ff568bd61c744bf31185aeddf13580.png'), ('5', '138****8888', null, '13888888888', null, '0', '2017-11-25 17:56:45', '2017-11-25 17:56:45', '2017-11-25 17:56:45', null), ('8', '151****9677', null, '15110059677', null, '0', '2017-11-25 18:58:18', '2017-11-25 18:58:18', '2017-11-25 18:58:18', null);
COMMIT;
SET FOREIGN_KEY_CHECKS = 1;
现在建立实体类entity
package cn.xiao.zufang.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "user")//user类映射到表user中
public class User {
    @Id //jpa注解表示id为主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private  String password;
    private int status;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "last_login_time")
    private Date lastLoginTime;
    @Column(name = "last_update_time")
    private Date lastUpdateTime;

    private String avatar;

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
然后建立测试类，在我们的test目录中已经存在applicationTest.java。在每个测试类中的需要配置的注解相同。我们可以直接继承系统生成的测试类。
package cn.xiao.zufang.entity;

import cn.xiao.zufang.repository.UserRepository;
import cn.xiao.zufang.ApplicationTests;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRepositoryTest extends ApplicationTests {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindOne() {
        User user = userRepository.findOne(1L);
        Assert.assertEquals("waliwali", user.getName());
    }
}
至此我们的测试就完成了。
注意这一步出现的bug。
1.	无法注入UserRepository，经过排查是因为类目录和注解的目录不同导致的。


上面的测试用的是mysql数据库进行测试的。在开发中我们不想直接操作本地数据，那么这时候我们就可以使用H2内存数据库。
现在我们集成内存数据库。
1.	首先我们进行配置分离。在resource目录中新建文件：开发配置文件application-dev.properties，测试配置文件application-dev.properties
2.	然后在application.properties下添加spring.profiles.active=dev。然后我们就把通用的设置放在application中；把个性化的设置放在application-dev或test中。这里我们把herbinate的相关配置放在dev中。在test中添加内存模式的配置
#内存模式
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:test
设置到这里还需要在测试父类上添加一个注解@ActiveProfiles("test")
现在因为还没在h2中建立表结构所以执行会报错，现在我们再在H2中建立表结构（采用引用建立好的SQL语句）
spring.datasource.schema=classpath:db/schema.sql
spring.datasource.data=classpath:db/data.sql
至此，我们的h2集成就完成了。



前端集成。
首先查看依赖
然后新建一个配置类WebMvcConfig，配置mvc相关
package cn.xiao.zufang.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.Thymeleaf;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    //实现的这个接口能帮助我们设置spring的上下文
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.applicationContext=applicationContext;
    }
    //对模板资源进行解析(模板资源解析器
    @Bean
    public SpringResourceTemplateResolver templateResolver(){
        SpringResourceTemplateResolver templateResolver=new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        return templateResolver;
    }
    //thymeleaf标准方言解析器
    @Bean
    public SpringTemplateEngine templateEngine(){
        SpringTemplateEngine templateEngine=new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        //设置支持spring el表达式
        templateEngine.setEnableSpringELCompiler(true);
        return templateEngine;
    }
    //配置视图解析器
    @Bean
    public ThymeleafViewResolver viewResolver(){
    ThymeleafViewResolver viewResolver=new ThymeleafViewResolver();
    viewResolver.setTemplateEngine(templateEngine());
    return viewResolver;
    }
}

因为在开发过程中，模板内容是经常变化的，所以我们还要配置dev关闭thymeleaf缓存
#thymeleaf
spring.thymeleaf.cache=false
同时还要在通用设置中设置thymeleaf模式为html，因为默认的html5已经被放弃
#thymeleaf
spring.thymeleaf.mode=HTML

至此，thymeleaf就集成完成了。
新建一个首页，再templeat目录下新建一个html页面。
为了能够访问到首页，需要新建一个controller包下放controller类
@Controller
public class HomeController {
    @GetMapping("/index")
    public String index(){
        return "index"
    }
}
启动后并不能访问index页面，因为我们是自定义的thymeleaf所以还要在thymeleaf的模板资源解析器上添加注解  @ConfigurationProperties(prefix = "spring.thymeleaf")

