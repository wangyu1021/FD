package com.fd.controller;

import com.fd.annotation.ServerLog;
import com.fd.bo.TeamBo;
import com.fd.bo.TeamConditionBo;
import com.fd.pojo.JsonResult;
import com.fd.pojo.Reject;
import com.fd.pojo.Team;
import com.fd.pojo.TeamAudit;
import com.fd.service.TeamService;
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
@RequestMapping("/team")
@Api(value = "/team", tags = "战队接口")
public class TeamController {
    @Resource
    private TeamService teamService;

    @RequestMapping("/findAllTeam")
    @ResponseBody
    @ApiOperation(value = "查询所有战队", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllTeam() {
        JsonResult json = new JsonResult();
        try {
            List<Team> list = teamService.findAllTeam();
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

    @RequestMapping("/findAllTeamAudit")
    @ResponseBody
    @ApiOperation(value = "查询所有战队申请", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllTeamAudit() {
        JsonResult json = new JsonResult();
        try {
            List<TeamAudit> list = teamService.findAllTeamAudit();
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

    @RequestMapping("/findRejectByAuditId")
    @ResponseBody
    @ApiOperation(value = "查询申请失败的原因", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findRejectByAuditId(Integer auditId) {
        JsonResult json = new JsonResult();
        try {
            Reject reject = teamService.findRejectByAuditId(auditId);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(reject);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findTeamByCondition")
    @ResponseBody
    @ApiOperation(value = "根据条件查询战队", httpMethod = "POST", response = JsonResult.class)
    public JsonResult findTeamByCondition(@RequestBody TeamConditionBo teamConditionBo) {
        JsonResult jsonResult = new JsonResult();
        try {
            List<Team> list = teamService.findTeamByCondition(teamConditionBo);
            jsonResult.setState(1);
            jsonResult.setMessage("查询成功");
            jsonResult.setData(list);
        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setState(0);
            jsonResult.setMessage(e.getMessage());
        }
        return jsonResult;
    }


    @RequestMapping("/findTeamById")
    @ResponseBody
    @ApiOperation(value = "根据id查询战队", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findTeamById(String id) {
        JsonResult json = new JsonResult();
        try {
            Team team = teamService.findTeamById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(team);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/updateStatus")
    @ResponseBody
    @ApiOperation(value = "根据id修改战队状态", httpMethod = "GET", response = JsonResult.class)
    public JsonResult updateStatus(String id,Integer status){
        JsonResult json=new JsonResult();
        try{
            teamService.updateStatus(id,status);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/addTeam")
    @ResponseBody
    @ApiOperation(value = "添加战队", httpMethod = "POST", response = JsonResult.class)
    public JsonResult addTeam(@RequestBody TeamBo teamBo){
        JsonResult json=new JsonResult();
        try{
            teamService.addTeam(teamBo);
        }catch ( Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/updateTeamAuditStatus")
    @ResponseBody
    @ApiOperation(value = "审核战队",notes = "0为待审核，1为通过，2为未通过",httpMethod = "POST", response = JsonResult.class)
    public  JsonResult updateTeamAuditStatus(Integer id,Integer status){
        JsonResult json=new JsonResult();
        try{
            teamService.updateTeamAuditStatus(id,status);
            json.setState(1);
            json.setMessage("审核成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @ServerLog("拒绝了一个战队申请")
    @RequestMapping("/adddReject")
    @ResponseBody
    @ApiOperation(value = "审核不通过，添加驳回原因",httpMethod = "GET", response = JsonResult.class)
    public JsonResult adddReject(Integer auditId,String reason){
        JsonResult json=new JsonResult();
        try{
            teamService.addReject(auditId,reason);
            json.setState(1);
            json.setMessage("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findLastNum")
    @ResponseBody
    @ApiOperation(value = "查询过去战队数量",httpMethod = "GET", response = JsonResult.class)
    public  JsonResult findLastNum(){
        JsonResult json=new JsonResult();
        try{
            Integer num=teamService.findLastNum();
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

    @RequestMapping("/findTodayNum")
    @ResponseBody
    @ApiOperation(value = "查询今日战队数量",httpMethod = "GET", response = JsonResult.class)
    public  JsonResult findTodayNum(){
        JsonResult json=new JsonResult();
        try{
            Integer num=teamService.findTodayNum();
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
