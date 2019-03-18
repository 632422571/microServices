package cn.globalcash.spring.cloud.provider.mapper;

import cn.globalcash.spring.cloud.domain.User;

import java.util.List;

public interface UserMapper {
    public List<User> getData();
}
