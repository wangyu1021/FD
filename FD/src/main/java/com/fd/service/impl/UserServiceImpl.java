package com.fd.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dao.UserDao;
import com.fd.pojo.Role;
import com.fd.pojo.User;
import com.fd.service.RoleService;
import com.fd.service.UserService;
import com.fd.vo.UserRole;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;

	public List<User> findAllUser() {
		List<User> list = userDao.findAllUser();
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			user=findUserRole(user);
			user.setUsers(util(user.getId()));
		}
		return list;
	}


	public User findUserById(Integer id) {
		User user = userDao.findUserById(id);
		user=findUserRole(user);
		user.setUsers(util(user.getId()));
		return user;
	}

	public void updateUserById(User user) {
		userDao.updateUserById(user);
	}

	/**
	 * 模糊查询用户
	 */
	public List<User> findUserByRP(Integer roleId, String phone) {
		List<UserRole> ur = userDao.findRoleByRoleId(roleId);
		List<User> users = new ArrayList<User>();
		if (phone == null) {
			for (int i = 0; i < ur.size(); i++) {
				User user = userDao.findUserById(ur.get(i).getUserId());
				user=findUserRole(user);
				users.add(user);
			}
		}else if (roleId ==null) {
			users=userDao.findUserByPhone(phone);
			for(int i=0;i<users.size();i++) {
				User user=users.get(i);
				user=findUserRole(user);
			}
		}else {
			users=userDao.findUserByRP(phone,roleId);
			for(int i=0;i<users.size();i++) {
				User user=users.get(i);
				user=findUserRole(user);
			}
		}
		return users;
	}
	
	/**
	 * 一个工具方法
	 * 获取该角色的角色名称
	 * @param user
	 * @return
	 */
	public User findUserRole(User user) {
		List<Role> roles = roleService.findAllRole();
		UserRole userRole = userDao.findRoleByUserId(user.getId());
		for (int j = 0; j < roles.size(); j++) {
			if (userRole.getRoleId() == roles.get(j).getId()) {
				user.setRole(roles.get(j).getName());
				break;
			}
		}
		return user;
	}
	/**
	 * 获取我邀请的人
	 * @param id
	 * @return
	 */
	public List<User> util(Integer id){
		List<User> users = userDao.findUserByUserId(id);
		for(int j=0;j<users.size();j++) {
			User ur=users.get(j);
			ur=findUserRole(ur);
		}
		return users;
	}

}
