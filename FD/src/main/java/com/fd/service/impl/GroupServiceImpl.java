package com.fd.service.impl;

import com.fd.dao.GroupDao;
import com.fd.pojo.Group;
import com.fd.service.ClubService;
import com.fd.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private GroupDao groupDao;

    @Resource
    private ClubService clubService;

    public List<Group> findAllGroup() {
        List<Group> list = groupDao.findAllGroup();
        return list;
    }

    public List<Group> findGroupByClub(Integer clubId) {
        List<Group> list=groupDao.findGroupByClub(clubId);
        return list;
    }


}
