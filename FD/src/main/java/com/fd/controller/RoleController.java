package com.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fd.pojo.JsonResult;
import com.fd.pojo.Permission;
import com.fd.pojo.Role;
import com.fd.service.RoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@CrossOrigin
@Controller
@Api(value = "/role",tags="角色权限接口")
@RequestMapping("/role")
public class RoleController {
	@Autowired
	private RoleService roleService;
	
	@ResponseBody
	@RequestMapping("/findAllRole.do")
	@ApiOperation(value = "查询所有角色",httpMethod = "GET",response=JsonResult.class)
	public JsonResult findAllRole() {
		JsonResult json=new JsonResult();
		try {
			List<Role> list=roleService.findAllRole();
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/findPermission.do")
	@ApiOperation(value = "查询所有权限",httpMethod = "GET",response=JsonResult.class)
	public JsonResult findPermission() {
		JsonResult json = new JsonResult();
		try {
			List<Permission> list=roleService.findPermission();
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updatePermission.do")
	@ApiOperation(value = "修改角色权限",httpMethod = "GET",response=JsonResult.class)
	public JsonResult updatePermission(Integer roleId,Integer permissionId) {
		JsonResult json=new JsonResult();
		try {
			roleService.updatePermission(roleId,permissionId);
			json.setState(1);
			json.setMessage("修改成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("修改成功");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/deleteRole.do")
	@ApiOperation(value = "删除角色",httpMethod = "GET",response=JsonResult.class)
	public JsonResult deleteRole(Integer roleId) {
		JsonResult json=new JsonResult();
		try {
			roleService.deleteRole(roleId);
			json.setState(1);
			json.setMessage("删除成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/findRoleById.do")
	@ApiOperation(value = "根据id查询角色",httpMethod = "GET",response=JsonResult.class)
	public JsonResult findRoleById(Integer id) {
		JsonResult json=new JsonResult();
		try {
			Role role=roleService.findRoleById(id);
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(role);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("查询失败");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/updateRoleById.do")
	@ApiOperation(value = "修改角色名称",httpMethod = "GET",response=JsonResult.class)
	public JsonResult updateRoleById(String name,Integer id) {
		JsonResult json = new  JsonResult();
		try {
			roleService.updateRoleById(name,id);
			json.setState(1);
			json.setMessage("修改成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("修改失败");
		}
		return json;
	}
	
	@ResponseBody
	@RequestMapping("/addRole.do")
	@ApiOperation(value = "添加角色",httpMethod = "GET",response=JsonResult.class)
	public JsonResult addRole(String name) {
		JsonResult json=new JsonResult();
		try {
			roleService.addRole(name);
			json.setState(1);
			json.setMessage("添加成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("添加失败");
		}
		return json;
	}
}
