package com.fd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fd.dao.RoleDao;
import com.fd.pojo.Permission;
import com.fd.pojo.Role;
import com.fd.service.RoleService;
import com.fd.vo.RolePermissionVo;

@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	private RoleDao roleDao;
	
	public List<Role> findAllRole() {
		List<Role> list=roleDao.findAllRole();
		for(int i=0;i<list.size();i++) {
			List<Integer> ids=new ArrayList<Integer>();
			Role role = list.get(i);
			List<RolePermissionVo> rp = roleDao.findRolePermissionByRoleId(role.getId());
			for(int j=0;j<rp.size();j++) {
				RolePermissionVo vo = rp.get(j);
				Integer pid = vo.getPermissionId();
				ids.add(pid);
				role.setPIds(ids);
			}
		}
		return list;
	}

	public List<Permission> findPermission() {
		return roleDao.findPermission();
	}

	public List<RolePermissionVo> findRolePermissionByRoleId(Integer id) {
		return roleDao.findRolePermissionByRoleId(id);
	}

	public void updatePermission(Integer roleId, Integer permissionId) {
		Integer num = roleDao.findPermissionByRidAndPid(roleId,permissionId);
		if(num!=0) {
			roleDao.deletePermissionByRoleId(roleId,permissionId);
		}else {
			roleDao.insertPermissionByRoleId(roleId,permissionId);
		}
	}

	public void deleteRole(Integer roleId) {
		Role role=roleDao.findRoleById(roleId);
		if(role==null) {
			throw new RuntimeException("此id不存在");
		}else {
			roleDao.deleteRole(roleId);
			roleDao.deletePermission(roleId);
		}
	}

	public Role findRoleById(Integer id) {
		Role role=roleDao.findRoleById(id);
		if(role==null){
			throw new RuntimeException("此id不存在");
		}
		List<Integer> ids=new ArrayList<Integer>();
		List<RolePermissionVo> rp = roleDao.findRolePermissionByRoleId(role.getId());
		for(int j=0;j<rp.size();j++) {
			RolePermissionVo vo = rp.get(j);
			Integer pid = vo.getPermissionId();
			ids.add(pid);
			role.setPIds(ids);
		}
		return role;
	}

	public void updateRoleById(String name, Integer id) {
		roleDao.updateRoleById(name,id);
		
	}

	public void addRole(String name) {
		Role role=new Role();
		role.setName(name);
		role.setCreateTime(new Date());
		roleDao.addRole(role);
	}


}
