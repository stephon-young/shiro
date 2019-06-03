import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Assert;
import org.junit.Test;

public class LoginLogoutTest {

    private void test(String path, String user, String pwd) {
        Factory<org.apache.shiro.mgt.SecurityManager> factory = new IniSecurityManagerFactory(path);
        //Factory<SecurityManager> factory;
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user, pwd);

        ModularRealmAuthenticator authenticator;

        try {
            subject.login(token);
        } catch (AuthenticationException ex) {
            System.out.println("error: " + ex);
        }

        Assert.assertEquals(true, subject.isAuthenticated());

        subject.logout();
    }

    @Test
    public void testIniRealm() {
        System.out.println("====== < ini begin...");
        String path = "classpath:shiro.ini";
        String user = "zhang";
        String pwd = "123";
        test(path, user, pwd);
        System.out.println("====== > int end...");
    }

    @Test
    public void testCustomRealm() {
        System.out.println("====== < realm begin...");
        String path = "classpath:realm.ini";
        String user = "zhang";
        String pwd = "123";
        test(path, user, pwd);
        System.out.println("====== > realm end...");
    }

    @Test
    public void testCustomRealms() {
        System.out.println("====== < realms begin...");
        String path = "classpath:realms.ini";
        String user = "zhang";
        String pwd = "123";
        test(path, user, pwd);
        System.out.println("====== > realms end...");
    }

    @Test
    public void testJdbcRealm() {
        // 注意，druid的版本需要设定为0.2.23，不能设定为1.x版本，会导致连接出现问题，现在暂时未查明原因
        System.out.println("====== < jdbc begin...");
        String path = "classpath:jdbc.ini";
        String user = "zhang";
        String pwd = "123";
        test(path, user, pwd);
        System.out.println("====== > jdbc end...");
    }
}
