package cn.xiao.zufang.security;

import cn.xiao.zufang.entity.User;
import cn.xiao.zufang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import sun.awt.windows.ThemeReader;

/**
 * 自定义认证实现
 */
public class AuthProvider implements AuthenticationProvider {
    @Autowired
    private IUserService iUserService;
    private final Md5PasswordEncoder passwordEncoder=new Md5PasswordEncoder();

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username=authentication.getName();
        String userInputPassword= (String) authentication.getCredentials();
            User user=iUserService.findUserByName(username);
        if (user==null){
            throw new AuthenticationCredentialsNotFoundException("authError");
        }
       // user.getPassword().equals(userInputPassword);//由于密码使用md5
        if (this.passwordEncoder.isPasswordValid(user.getPassword(),userInputPassword,user.getId())){
            return new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
        }
        throw new BadCredentialsException("authError");
    }

    @Override
    public boolean supports(Class<?> aClass) {//支持所有认证方法
        return true;
    }
}
