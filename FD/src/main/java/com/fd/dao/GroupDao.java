package com.fd.dao;

import com.fd.pojo.Group;

import java.util.List;

public interface GroupDao {
    /**
     * 查询所有团队
     * @return
     */
    List<Group> findAllGroup();

    /**
     * 根据俱乐部查询团队
     * @param clubId
     * @return
     */
    List<Group> findGroupByClub(Integer clubId);
}
