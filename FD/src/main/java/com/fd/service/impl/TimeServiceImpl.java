package com.fd.service.impl;

import com.fd.dao.TimeDao;
import com.fd.pojo.Time;
import com.fd.service.TimeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TimeServiceImpl implements TimeService {
    @Resource
    private TimeDao timeDao;

    public List<Time> findAllTime() {
        return timeDao.findAllTime();
    }

    public Time findTimeById(Integer id) {
        return timeDao.findTimeById(id);
    }
}
