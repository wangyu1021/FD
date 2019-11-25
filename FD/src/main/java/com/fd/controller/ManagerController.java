package com.fd.controller;

import java.util.List;

import com.fd.annotation.ServerLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fd.pojo.Manager;
import com.fd.pojo.JsonResult;
import com.fd.service.ManagerService;
import com.fd.vo.ManagerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RequestMapping("/manager")
@Controller
@Api(value = "/manager", tags = "店长接口")
public class ManagerController {
	@Autowired
	private ManagerService managerService;

	@ServerLog("登录了该系统")
	@RequestMapping("login.do")
	@ResponseBody
	@ApiOperation(value = "登录",notes="账号为admin，密码为admin", httpMethod = "POST", response = JsonResult.class)
	public JsonResult login(@RequestBody ManagerVo Manager) {
		JsonResult json=new JsonResult();
		try {
			String loginId=Manager.getLoginId();
			String password=Manager.getPassword();
			String token=managerService.login(loginId,password);
			json.setState(1);
			json.setMessage("登录成功");
			json.setData(token);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("updatePassword.do")
	@ResponseBody
	@ApiOperation(value = "修改密码", httpMethod = "POST", response = JsonResult.class)
	public JsonResult updatePassword(String loginId,String newPassword,String oldPassword) {
		JsonResult json=new JsonResult();
		try {
			managerService.updatePassword(loginId,newPassword,oldPassword);
			json.setState(1);
			json.setMessage("修改成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}

	@ServerLog("添加店长")
	@RequestMapping("addManager.do")
	@ResponseBody
	@ApiOperation(value = "添加店长", httpMethod = "POST", response = JsonResult.class)
	public JsonResult addManager(@RequestBody Manager Manager) {
		JsonResult json=new JsonResult();
		try {
			managerService.addManager(Manager);
			json.setState(1);
			json.setMessage("添加成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}

	@ServerLog("删除店长")
	@RequestMapping("deleteManagerById.do")
	@ResponseBody
	@ApiOperation(value = "删除店长", httpMethod = "GET", response = JsonResult.class)
	public JsonResult deleteManagerById(Integer id) {
		JsonResult json =new JsonResult();
		try {
			managerService.deleteManagerById(id);
			json.setState(1);
			json.setMessage("删除成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("findManager.do")
	@ResponseBody
	@ApiOperation(value = "查询所有店长", httpMethod = "GET", response = JsonResult.class)
	public JsonResult findManager() {
		JsonResult json=new JsonResult();
		try {
			List<Manager> list=managerService.findManager();
			json.setState(1);
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}


	@RequestMapping("updateManager.do")
	@ResponseBody
	@ApiOperation(value = "修改店长信息",notes="密码不可以从在这里传过来", httpMethod = "POST", response = JsonResult.class)
	public JsonResult updateManagerById(@RequestBody Manager Manager) {
		JsonResult json=new JsonResult();
		try {
			managerService.updateManagerById(Manager,Manager.getId());
			json.setState(1);
			json.setMessage("修改成功");
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("findManagerById.do")
	@ResponseBody
	@ApiOperation(value = "根据id获取店长信息", httpMethod = "GET", response = JsonResult.class)
	public JsonResult findManagerById(Integer id) {
		JsonResult json =new JsonResult();
		try {
			Manager manager=managerService.findManagerById(id);
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(manager);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
	
	@RequestMapping("findManagerByJobNumOrName.do")
	@ResponseBody
	@ApiOperation(value = "店长信息模糊查询", httpMethod = "GET", response = JsonResult.class)
	public JsonResult findManagerByJobNumOrName(String name,String jobNumber) {
		JsonResult json=new JsonResult();
		try {
			System.out.println(name);
			System.out.println(jobNumber);
			List<Manager> list=managerService.findManagerByJobNumOrName(name,jobNumber);
			json.setState(1);
			json.setMessage("查询成功");
			json.setData(list);
		} catch (Exception e) {
			json.setState(0);
			json.setMessage(e.getMessage());
		}
		return json;
	}
}
