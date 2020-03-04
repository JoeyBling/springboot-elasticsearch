package io.gitee.zhousiwei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

/**
 * SpringBoot
 *
 * @author Created by 試毅-思伟 on 2018/8/16
 */
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication(scanBasePackages = {App.SCAN_BASE_PACKAGES})
@Slf4j
public class App extends SpringBootServletInitializer {

    /**
     * 扫描包名
     */
    public static final String SCAN_BASE_PACKAGES = "io.gitee.zhousiwei";

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);
    }


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);

        // This will set to use COOKIE only
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
        // This will prevent any JS on the page from accessing the
        // cookie - it will only be used/accessed by the HTTP transport
        // mechanism in use
        SessionCookieConfig sessionCookieConfig = servletContext.getSessionCookieConfig();
        log.debug(String.format("sessionCookieConfig.isHttpOnly()===>%s", sessionCookieConfig.isHttpOnly()));
        sessionCookieConfig.setHttpOnly(true);
    }

}


