package com.fd.service;

import java.util.List;

import com.fd.pojo.Manager;

public interface ManagerService {

	String login(String loginId, String password);

	void updatePassword(String loginId, String password, String oldPassword);

	void addManager(Manager Manager);

	void deleteManagerById(Integer id);

	List<Manager> findManager();

	void updateManagerById(Manager Manager, Integer id);

	Manager findManagerById(Integer id);

	List<Manager> findManagerByJobNumOrName(String name, String jobNumber);

}
