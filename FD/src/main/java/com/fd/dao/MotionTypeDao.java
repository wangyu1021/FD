package com.fd.dao;

import com.fd.pojo.MotionType;

import java.util.List;

public interface MotionTypeDao {
    List<MotionType> findAllMotionType();

    MotionType findMotionTypeById(Integer id);

    MotionType findMotionTypeByName(String name);
}
