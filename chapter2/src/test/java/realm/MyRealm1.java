package realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

public class MyRealm1 implements Realm {

    private static final String user = "zhang";
    private static final String password = "123";

    public MyRealm1() {
        System.out.println("hello, MyRealm1 instance, getName=" + getName());
    }

    @Override
    public String getName() {
        return "realm-" + user ;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //用户名转换与否都可以
        String username = (String)token.getPrincipal();
        //密码的获取必须用下面的转换
        String pwd = new String((char [])token.getCredentials());

        if (!user.equals(token.getPrincipal()))
            throw new UnknownAccountException();

        //if (!password.equals(token.getCredentials()))
        if (!password.equals(pwd))
            throw new IncorrectCredentialsException();

        return new SimpleAuthenticationInfo(username, pwd, getName());
    }
}
