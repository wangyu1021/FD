package com.fd.service.impl;

import com.fd.dao.MotionTypeDao;
import com.fd.pojo.MotionType;
import com.fd.service.MotionTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MotionTypeServiceImpl implements MotionTypeService {
    @Resource
    private MotionTypeDao motionTypeDao;

    public List<MotionType> findAllMotionType() {
        return motionTypeDao.findAllMotionType();
    }

    public MotionType findMotionTypeById(Integer id) {
        return  motionTypeDao.findMotionTypeById(id);
    }

    public MotionType findMotionTypeByName(String name) {
        return motionTypeDao.findMotionTypeByName(name);
    }
}
