package com.fd.service;

import java.util.List;

import com.fd.pojo.Permission;
import com.fd.pojo.Role;

public interface RoleService {

	List<Role> findAllRole();

	List<Permission> findPermission();

	void updatePermission(Integer roleId, Integer permissionId);

	void deleteRole(Integer roleId);

	Role findRoleById(Integer id);

	void updateRoleById(String name, Integer id);

	void addRole(String name);


}
