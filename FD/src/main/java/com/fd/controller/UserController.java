package com.fd.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fd.pojo.JsonResult;
import com.fd.pojo.User;
import com.fd.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RequestMapping("/user")
@Controller
@Api(value = "/user",tags="用户接口")
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping("findAllUser.do")
	@ResponseBody
	@ApiOperation(value = "查询所有用户",httpMethod = "GET",response=JsonResult.class)
	public JsonResult findAllUser() {
		JsonResult json=new JsonResult();
		try {
			List<User> list=userService.findAllUser();
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("查询失败");
		}
		return json;
	}
	
	@RequestMapping("findUserById.do")
	@ResponseBody
	@ApiOperation(value = "id查询用户",httpMethod = "GET",response=JsonResult.class)
	public JsonResult findUserById(Integer id) {
		JsonResult json=new JsonResult();
		try {
			User user=userService.findUserById(id);
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(user);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("查询失败");
		}
		return json;
	}
	
	@RequestMapping("updateUserById.do")
	@ResponseBody
	@ApiOperation(value = "id修改用户",httpMethod = "POST",response=JsonResult.class)
	public JsonResult updateUserById(@RequestBody User user) {
		JsonResult json=new JsonResult();
		try {
			userService.updateUserById(user);
			json.setState(1);
			json.setMessage("修改成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("修改失败");
		}
		return json;
	}
	
	@RequestMapping("findUserByRP.do")
	@ResponseBody
	@ApiOperation(value = "模糊查询用户",httpMethod = "POST",response=JsonResult.class)
	public JsonResult findUserByRP(Integer roleId,String phone) {
		JsonResult json=new JsonResult();
		try {
			List<User> list=userService.findUserByRP(roleId,phone);
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage("查询失败");
		}
		return json;
	}
}
