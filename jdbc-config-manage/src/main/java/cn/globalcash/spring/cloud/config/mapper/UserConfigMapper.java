package cn.globalcash.spring.cloud.config.mapper;

import cn.globalcash.spring.cloud.config.domain.UserConfig;

import java.util.List;

public interface UserConfigMapper {
    public List<UserConfig> getUserConfig();

    public int updateUserConfig(UserConfig userConfig);
}
