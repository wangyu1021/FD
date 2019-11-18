package com.fd.service;

import com.fd.pojo.Group;

import java.util.List;

public interface GroupService {
    List<Group> findAllGroup();

    List<Group> findGroupByClub(Integer clubId);
}
