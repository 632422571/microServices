package cn.globalcash.spring.cloud.domain;


import java.io.Serializable;

/**
 * @author gh
 * @date 2019/1/29 14:09
 */
public class User implements Serializable {
    private static final long serialVersionUID = -6599593059266413510L;
    private Long id;

    private String name;

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
}
