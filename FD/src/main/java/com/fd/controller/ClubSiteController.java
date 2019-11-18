package com.fd.controller;

import com.fd.bo.ClubSiteBo;
import com.fd.pojo.ClubSite;
import com.fd.pojo.JsonResult;
import com.fd.service.ClubSiteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;


@Controller
@RequestMapping("/clubSite")
@CrossOrigin
@Api(value = "/clubSite",tags = "场地接口")
public class ClubSiteController {
    @Resource
    private ClubSiteService clubSiteService;

    @RequestMapping("/findClubSiteByClubId")
    @ResponseBody
    @ApiOperation(value = "查询俱乐部场地", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findClubSiteByClubId(Integer clubId){
        JsonResult json=new JsonResult();
        try{
            List<ClubSite> list=clubSiteService.findClubSiteByClubId(clubId);
            json.setState(1);
            json.setData(list);
            json.setMessage("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }


    @RequestMapping("/findCountByClubId")
    @ResponseBody
    @ApiOperation(value = "查询俱乐部场地数量", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findCountByClubId(Integer clubId){
        JsonResult json=new JsonResult();
        try{
            Integer count=clubSiteService.findCountByClubId(clubId);
            json.setState(1);
            json.setData(count);
            json.setMessage("查询成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/addAddress")
    @ResponseBody
    @ApiOperation(value = "添加场地", httpMethod = "POST", response = JsonResult.class)
    public JsonResult addAddress(@RequestBody ClubSiteBo clubSiteBo){
        JsonResult json=new JsonResult();
        try{
            clubSiteService.addAddress(clubSiteBo);
            json.setState(1);
            json.setMessage("添加成功");
        }catch ( Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }
}
