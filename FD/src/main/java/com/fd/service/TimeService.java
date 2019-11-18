package com.fd.service;

import com.fd.pojo.Time;

import java.util.List;

public interface TimeService {
    List<Time> findAllTime();

    Time findTimeById(Integer id);
}
