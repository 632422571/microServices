package cn.globalcash.spring.cloud;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"cn.globalcash.spring.cloud.config.mapper"})
public class ZkJDBCConfigManageApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZkJDBCConfigManageApplication.class,args);
    }
}
