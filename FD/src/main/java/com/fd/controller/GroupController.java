package com.fd.controller;

import com.fd.pojo.Group;
import com.fd.pojo.JsonResult;
import com.fd.service.GroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/group")
@Api(value = "/group" ,tags = "团队接口")
public class GroupController {
    @Autowired
    private GroupService groupService;

    @RequestMapping("findAllGroup.do")
    @ResponseBody
    @ApiOperation(value = "查询所有团队", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllGroup(){
        JsonResult json=new JsonResult();
        try {
            List<Group> list= groupService.findAllGroup();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch (Exception e){
            json.setState(1);
            json.setMessage("查询失败");
        }
        return json;
    }

    @RequestMapping("findGroupByClub.do")
    @ResponseBody
    @ApiOperation(value = "根据俱乐部查询团队", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findGroupByClub(Integer clubId){
        JsonResult json=new JsonResult();
        try {
            List<Group> list=groupService.findGroupByClub(clubId);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage("查询失败");
        }
        return json;
    }


}
