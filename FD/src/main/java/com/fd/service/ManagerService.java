package com.fd.service;

import java.util.List;

import com.fd.bo.PasswordBo;
import com.fd.pojo.Manager;

public interface ManagerService {

	String login(String loginId, String password);

	void updatePassword(PasswordBo passwordBo);

	void addManager(Manager Manager);

	void deleteManagerById(Integer id);

	List<Manager> findManager();

	void updateManagerById(Manager Manager, Integer id);

	Manager findManagerById(Integer id);

	List<Manager> findManagerByJobNumOrName(String name, String jobNumber);


	List<Manager> findAdmin();
}
