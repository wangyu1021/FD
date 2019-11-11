package com.fd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fd.pojo.User;
import com.fd.vo.UserRole;

public interface UserDao {
	/**
	 * 查询所有用户信息
	 * @return
	 */
	List<User> findAllUser();
	/**
	 * 根据id查询用户
	 * @param id
	 * @return
	 */
	User findUserById(Integer id);
	/**
	 * 修改用户
	 * @param user
	 */
	void updateUserById(User user);
	/**
	 * 查询用户对应角色
	 * @param id
	 * @return
	 */
	UserRole findRoleByUserId(Integer id);
	/**
	 * 查询是该角色的用户
	 * @param roleId
	 * @return
	 */
	List<UserRole> findRoleByRoleId(Integer roleId);
	/**
	 * 根据电话查询
	 * @param phone
	 * @return
	 */
	List<User> findUserByPhone(String phone);
	/**
	 * 
	 * @param phone
	 * @param roleId
	 * @return
	 */
	List<User> findUserByRP(@Param("phone")String phone,@Param("roleId")Integer roleId);
	/**
	 * 我邀请的用户
	 */
	List<User> findUserByUserId(Integer id);

}
