package io.gitee.zhousiwei;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * SpringBoot
 *
 * @author Created by 試毅-思伟 on 2018/8/16
 */
@Slf4j
@SpringBootApplication
@ComponentScan(basePackages = {"io.gitee.zhousiwei"})
public class App {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(App.class);
        app.setBannerMode(Mode.CONSOLE);
        app.run(args);
    }

}
