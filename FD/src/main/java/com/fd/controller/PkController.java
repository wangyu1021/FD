package com.fd.controller;

import com.fd.annotation.ServerLog;
import com.fd.bo.PkBo;
import com.fd.bo.PkConditionBo;
import com.fd.bo.StateBo;
import com.fd.pojo.JsonResult;
import com.fd.pojo.Pk;
import com.fd.pojo.PkAudit;
import com.fd.pojo.PkReject;
import com.fd.service.PkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/pk")
@Api(value = "/pk",tags = "战队pk接口")
public class PkController {
    @Resource
    private PkService pkService;

    @RequestMapping("/findAllPk")
    @ResponseBody
    @ApiOperation(value = "获取所有pk",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findAllPk(){
        JsonResult json= new JsonResult();
        try{
            List<Pk> list=pkService.findAllPk();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/findAllPkAudit")
    @ResponseBody
    @ApiOperation(value = "获取待审核pk",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findAllPkAudit(){
        JsonResult json= new JsonResult();
        try{
            List<PkAudit> list=pkService.findAllPkAudit();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/findPkById")
    @ResponseBody
    @ApiOperation(value = "根据id获取pk",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findPkById(String id){
        JsonResult json= new JsonResult();
        try{
            Pk pk=pkService.findPkById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(pk);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/updatePkStatus")
    @ResponseBody
    @ApiOperation(value = "根据id修改pk状态",httpMethod = "POST",response = JsonResult.class)
    public JsonResult updatePkStatus(@RequestBody  StateBo stateBo){
        JsonResult json= new JsonResult();
        try{
            pkService.updatePkStatus(stateBo);
            json.setState(1);
            json.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/updatePkAuditStatus")
    @ResponseBody
    @ApiOperation(value = "根据id修改pk审核",notes = "0为未开始，1为已结束",httpMethod = "POST",response = JsonResult.class)
    public JsonResult updatePkAuditStatus(@RequestBody  StateBo stateBo){
        JsonResult json= new JsonResult();
        try{
            pkService.updatePkAuditStatus(stateBo);
            json.setState(1);
            json.setMessage("修改成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/addVictoryTeam")
    @ResponseBody
    @ApiOperation(value = "上传获胜队伍",httpMethod = "GET",response = JsonResult.class)
    public JsonResult addVictoryTeam(String id,String victoryTeam){
        JsonResult json= new JsonResult();
        try{
            pkService.addVictoryTeam(id,victoryTeam);
            json.setState(1);
            json.setMessage("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @ServerLog("拒绝了一次PK")
    @RequestMapping("/addReason")
    @ResponseBody
    @ApiOperation(value = "添加驳回原因",httpMethod = "GET",response = JsonResult.class)
    public JsonResult addReason(String auditId,String reason){
        JsonResult json= new JsonResult();
        try{
            pkService.addReason(auditId,reason);
            json.setState(1);
            json.setMessage("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/addPk")
    @ResponseBody
    @ApiOperation(value = "添加PK",httpMethod = "POST",response = JsonResult.class)
    public JsonResult addPk(@RequestBody PkBo pkBo){
        JsonResult json= new JsonResult();
        try{
            pkService.addPk(pkBo);
            json.setState(1);
            json.setMessage("上传成功");
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/findPkByCondition")
    @ResponseBody
    @ApiOperation(value = "根据条件查询pk",httpMethod = "POST",response = JsonResult.class)
    public JsonResult findPkByCondition(@RequestBody PkConditionBo conditionBo){
        JsonResult json=new JsonResult();
        try{
            List<Pk> list=pkService.findPkByCondition(conditionBo);
            json.setMessage("查询成功");
            json.setState(1);
            json.setData(list);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return  json;
    }

    @RequestMapping("/findRejectByAuditId")
    @ResponseBody
    @ApiOperation(value = "根据审核id查询拒绝原因",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findRejectByAuditId(String auditId){
        JsonResult json=new JsonResult();
        try{
            PkReject reject=pkService.findRejectByAuditId(auditId);
            json.setMessage("查询成功");
            json.setState(1);
            json.setData(reject);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findTodayPk")
    @ResponseBody
    @ApiOperation(value = "查询今日pk总数量",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findTodayPk(){
        JsonResult json=new JsonResult();
        try{
            Integer num = pkService.findTodayPk();
            json.setMessage("查询成功");
            json.setState(1);
            json.setData(num);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findLastDayPk")
    @ResponseBody
    @ApiOperation(value = "查询昨天往后的pk总数量",httpMethod = "GET",response = JsonResult.class)
    public JsonResult findLastDayPk(){
        JsonResult json=new JsonResult();
        try{
            Integer num = pkService.findLastDayPk();
            json.setMessage("查询成功");
            json.setState(1);
            json.setData(num);
        }catch (Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

}
