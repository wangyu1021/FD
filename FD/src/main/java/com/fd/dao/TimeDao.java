package com.fd.dao;

import com.fd.pojo.Time;

import java.util.List;

public interface TimeDao {
    List<Time> findAllTime();

    Time findTimeById(Integer id);
}
