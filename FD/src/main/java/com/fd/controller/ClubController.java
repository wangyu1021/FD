package com.fd.controller;

import com.alibaba.fastjson.JSON;
import com.fd.annotation.ServerLog;
import com.fd.bo.ClubBo;
import com.fd.bo.ClubConditionBo;
import com.fd.bo.StatusBo;
import com.fd.pojo.Club;
import com.fd.pojo.ClubSite;
import com.fd.pojo.JsonResult;
import com.fd.service.ClubService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.spring.web.json.Json;

import java.util.List;


@Controller
@CrossOrigin
@RequestMapping("/club")
@Api(value = "/club", tags = "俱乐部接口")
public class ClubController {
    @Autowired
    private ClubService clubService;


    @RequestMapping("findAllClub")
    @ResponseBody
    @ApiOperation(value = "查询所有俱乐部", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllClub() {
        JsonResult json = new JsonResult();
        try {
            List<Club> lists = clubService.findAllClub();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(lists);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage("查询失败");
        }
        return json;
    }

    @ServerLog("添加俱乐部")
    @RequestMapping("/addClub")
    @ResponseBody
    @ApiOperation(value = "添加俱乐部", httpMethod = "POST", response = JsonResult.class)
    public JsonResult addClub(@RequestBody ClubBo clubBo){
        JsonResult json=new JsonResult();
        try{
            clubService.addClub(clubBo);
            json.setData(1);
            json.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage("添加失败");
        }
        return  json;
    }



    @RequestMapping("/ ")
    @ResponseBody
    @ApiOperation(value = "根据条件查询俱乐部", httpMethod = "POST", response = JsonResult.class)
    public JsonResult findClubByCondition(@RequestBody ClubConditionBo clubConditionBo){
        JsonResult json=new JsonResult();
        try{
            List<Club> list=clubService.findClubByCondition(clubConditionBo);
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

    @RequestMapping("/findClubById")
    @ResponseBody
    @ApiOperation(value = "根据id查询俱乐部", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findClubById(Integer id ){
        JsonResult json=new JsonResult();
        try{
            Club club=clubService.findClubById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(club);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }


    @ServerLog("修改俱乐部")
    @RequestMapping("/updateClubById")
    @ResponseBody
    @ApiOperation(value = "根据id修改俱乐部", httpMethod = "POST", response = JsonResult.class)
    public JsonResult updateClubById(@RequestBody ClubBo clubBo){
        JsonResult json=new JsonResult();
        try{
            clubService.updateClubById(clubBo);
            json.setState(1);
            json.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @ServerLog("删除俱乐部")
    @RequestMapping("/deleteClubById")
    @ResponseBody
    @ApiOperation(value = "根据id删除俱乐部", httpMethod = "GET", response = JsonResult.class)
    public JsonResult deleteClubById(Integer id){
        JsonResult json=new JsonResult();
        try{
            clubService.deleteClubById(id);
            json.setState(1);
            json.setMessage("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }


    @ServerLog("修改了俱乐部状态")
    @RequestMapping("/updateStatus")
    @ResponseBody
    @ApiOperation(value = "根据id修改俱乐部状态", httpMethod = "POST", response = JsonResult.class)
    public JsonResult updateStatus(@RequestBody StatusBo statusBo){
        JsonResult json=new JsonResult();
        try{
            clubService.updateStatus(statusBo);
            json.setState(1);
            json.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }


    @ServerLog("冻结或解冻俱乐部")
    @RequestMapping("/findStatus")
    @ResponseBody
    @ApiOperation(value = "获取俱乐部状态", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findStatus(String id){
        JsonResult json=new JsonResult();
        try {
            String status=clubService.findStauts(id);
            json.setMessage("查询成功");
            json.setState(1);
            json.setData(status);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/findClubSiteById")
    @ResponseBody
    @ApiOperation(value = "获取俱乐部场地", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findClubSiteById(String id){
        JsonResult json = new JsonResult();
        try{
            List<ClubSite> list=clubService.findClubSiteById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findLastClubNum")
    @ResponseBody
    @ApiOperation(value = "获取过去创建的俱乐部数量", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findLastClubNum(){
        JsonResult json = new JsonResult();
        try{
            Integer num=clubService.findLastClubNum();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(num);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findTodayClubNum")
    @ResponseBody
    @ApiOperation(value = "获取今日创建俱乐部数量", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findTodayClubNum(){
        JsonResult json = new JsonResult();
        try{
            Integer num=clubService.findTodayClubNum();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(num);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

}
