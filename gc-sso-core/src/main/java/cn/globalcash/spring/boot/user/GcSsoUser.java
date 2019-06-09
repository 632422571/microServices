package cn.globalcash.spring.boot.user;

import java.io.Serializable;
import java.util.Map;

/**
 * @author: gh
 * @date:2019/6/9
 */
public class GcSsoUser implements Serializable {
    private static final long serialVersionUID = 364194631780986321L;

    // field
    private String userid;
    private String username;
    private Map<String, String> plugininfo;

    private String version;
    private int expireMinite;
    private long expireFreshTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Map<String, String> getPlugininfo() {
        return plugininfo;
    }

    public void setPlugininfo(Map<String, String> plugininfo) {
        this.plugininfo = plugininfo;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getExpireMinite() {
        return expireMinite;
    }

    public void setExpireMinite(int expireMinite) {
        this.expireMinite = expireMinite;
    }

    public long getExpireFreshTime() {
        return expireFreshTime;
    }

    public void setExpireFreshTime(long expireFreshTime) {
        this.expireFreshTime = expireFreshTime;
    }
}

