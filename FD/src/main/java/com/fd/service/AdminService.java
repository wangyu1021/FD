package com.fd.service;

import com.fd.bo.ConfigBo;
import com.fd.pojo.Config;
import com.fd.pojo.Operation;

import java.util.List;

public interface AdminService {

    void addOperation(Operation operation);

    List<Operation> findAllOperation();

    List<Config> findAllConfig();

    Config findConfigById(String id);

    void updateConfigById(ConfigBo configBo);

    void deleteConfigById(String id);

    void addConfig(ConfigBo configBo);
}
