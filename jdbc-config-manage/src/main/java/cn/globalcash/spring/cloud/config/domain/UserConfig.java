package cn.globalcash.spring.cloud.config.domain;

import java.io.Serializable;

public class UserConfig implements Serializable{
    private static final long serialVersionUID = 4986414041668202149L;
    private Long id;
    private String name;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
