package com.fd.dao;

import com.fd.bo.ConfigBo;
import com.fd.pojo.Config;
import com.fd.pojo.Operation;

import java.util.List;

public interface AdminDao {
    void insertOperation(Operation operation);

    List<Operation> findAllOperation();

    List<Config> findAllConfig();

    Config findConfigById(String id);

    void updateConfigById(ConfigBo configBo);

    void deleteConfigById(String id);

    void addConfig(Config config);
}
