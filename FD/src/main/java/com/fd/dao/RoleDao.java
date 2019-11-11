package com.fd.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fd.pojo.Permission;
import com.fd.pojo.Role;
import com.fd.vo.RolePermissionVo;

public interface RoleDao {
	/**
	 * 查询所有角色
	 * @return
	 */
	List<Role> findAllRole();
	/**
	 * 查询所有权限
	 * @return
	 * 查询的是permission表
	 */
	List<Permission> findPermission();
	/**
	 * 根据角色Id查询角色所对应的权限
	 * @return
	 * 查询的是角色权限关联表
	 * role_permission
	 */
	List<RolePermissionVo> findRolePermissionByRoleId(Integer id);
	/**
	 * 根据两者id查询
	 * @param roleId
	 * @param permissionId
	 * 查询的是角色权限关联表
	 * role_permission
	 */
	Integer findPermissionByRidAndPid(@Param("roleId")Integer roleId, @Param("permissionId")Integer permissionId);
	/**
	 * 删除该角色拥有的权限
	 * @param roleId
	 * @param permissionId
	 * 查询的是角色权限关联表
	 * role_permission
	 */
	void deletePermissionByRoleId(@Param("roleId")Integer roleId, @Param("permissionId")Integer permissionId);
	/**
	 * 增加该角色拥有的权限
	 * @param roleId
	 * @param permissionId
	 * 查询的是角色权限关联表
	 * role_permission
	 */
	void insertPermissionByRoleId(@Param("roleId")Integer roleId, @Param("permissionId")Integer permissionId);
	/**
	 * 根据id查询角色
	 * @param roleId
	 * @return
	 */
	Role findRoleById(Integer roleId);
	/**
	 * 根据id删除角色
	 * @param roleId
	 */
	void deleteRole(Integer roleId);
	/**
	 * 根据角色id删除所有授权
	 * @param roleId
	 */
	void deletePermission(Integer roleId);
	/**
	 * 修改角色名称
	 * @param name
	 * @param id
	 */
	void updateRoleById(@Param("name")String name,@Param("id")Integer id);
	/**
	 * 添加角色
	 * @param role
	 */
	void addRole(Role role);

}
