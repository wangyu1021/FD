package com.fd.service;

import com.fd.pojo.MotionType;

import java.util.List;

public interface MotionTypeService {
    List<MotionType> findAllMotionType();

    MotionType findMotionTypeById(Integer id);

    MotionType findMotionTypeByName(String name);
}

