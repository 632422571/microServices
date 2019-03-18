package cn.globalcash.spring.cloud.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

//可以动态修改值,不需要重启--还没搞清楚
@ConfigurationProperties(prefix = "com.xxx")
public class UserInfo {
    public String userName ;
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
