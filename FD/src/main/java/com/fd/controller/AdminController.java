package com.fd.controller;


import com.fd.annotation.ServerLog;
import com.fd.bo.ConfigBo;
import com.fd.pojo.Config;
import com.fd.pojo.JsonResult;
import com.fd.pojo.Operation;
import com.fd.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@Controller
@RequestMapping("/admin")
@Api(value = "/admin", tags = "系统操作")
public class AdminController {
    @Resource
    private AdminService adminService;

    @RequestMapping("/findAllOperation")
    @ResponseBody
    @ApiOperation(value = "查询系统操作日志", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllOperation() {
        JsonResult json = new JsonResult();
        try {
            List<Operation> list = adminService.findAllOperation();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findAllConfig")
    @ResponseBody
    @ApiOperation(value = "查询所有系统配置", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllConfig() {
        JsonResult json = new JsonResult();
        try {
            List<Config> list = adminService.findAllConfig();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findConfigById")
    @ResponseBody
    @ApiOperation(value = "根据id查询系统配置",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findConfigById(String id) {
        JsonResult json = new JsonResult();
        try {
            Config config = adminService.findConfigById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(config);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @ServerLog("修改系统配置")
    @RequestMapping("/updateConfigById")
    @ResponseBody
    @ApiOperation(value = "根据id修改系统配置",httpMethod = "POST",response = JsonResult.class)
    public JsonResult updateConfigById(@RequestBody ConfigBo configBo) {
        JsonResult json = new JsonResult();
        try {
            adminService.updateConfigById(configBo);
            json.setState(1);
            json.setMessage("修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @ServerLog("删除系统配置")
    @RequestMapping("/deleteConfigById")
    @ResponseBody
    @ApiOperation(value = "根据id删除系统配置",httpMethod = "GET",response = JsonResult.class)
    public JsonResult deleteConfigById(String id) {
        JsonResult json = new JsonResult();
        try {
            adminService.deleteConfigById(id);
            json.setState(1);
            json.setMessage("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @ServerLog("添加系统配置")
    @RequestMapping("/addConfig")
    @ResponseBody
    @ApiOperation(value = "添加系统配置",httpMethod = "POST",response = JsonResult.class)
    public JsonResult addConfig(@RequestBody ConfigBo configBo) {
        JsonResult json = new JsonResult();
        try {
            adminService.addConfig(configBo);
            json.setState(1);
            json.setMessage("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }



}
