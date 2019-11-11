package com.fd.service;

import java.util.List;

import com.fd.pojo.User;

public interface UserService {

	List<User> findAllUser();

	User findUserById(Integer id);

	void updateUserById(User user);

	List<User> findUserByRP(Integer roleId, String phone);

}
