package com.fd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fd.pojo.Manager;

public interface ManagerDao {
	/**
	 * 根据账号查询管理员
	 * @param loginId
	 * @return
	 */
	Manager findLoginId(String loginId);
	/**
	 * 根据登录账号修改密码
	 * @param loginId
	 * @param pwd
	 */
	void updatePassword(@Param("loginId")String loginId,@Param("pwd")String pwd);
	/**
	 * 添加管理员信息
	 * @param Manager
	 */
	void addManager(Manager Manager);
	/**
	 * 根据id查询管理员信息
	 * @param id
	 */
	Manager findManagerById(Integer id);
	/**
	 * 根据id删除管理员
	 * @param id
	 */
	void deleteManagerById(Integer id);
	/**
	 * 查询所有管理员
	 * @return
	 */
	List<Manager> findManager();
	/**
	 * 根据id修改管理员
	 * @param Manager
	 * @param id
	 */
	void updateManagerById(@Param("manager")Manager Manager,@Param("id")Integer id);
	/**
	 * 根据工号或者名字查询
	 * @param name
	 * @param jobNumber
	 * @return
	 */
	List<Manager> findManagerByJobNumOrName(@Param("name")String name,@Param("jobNumber")String jobNumber);

}
