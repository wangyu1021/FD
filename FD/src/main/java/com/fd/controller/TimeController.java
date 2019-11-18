package com.fd.controller;

import com.fd.pojo.JsonResult;
import com.fd.pojo.Time;
import com.fd.service.TimeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@CrossOrigin
@RequestMapping("/time")
@Api(value = "/time", tags = "时间段接口")
public class TimeController {
    @Resource
    private TimeService timeService;

    @RequestMapping("/findAllTime")
    @ResponseBody
    @ApiOperation(value = "查询所有时间段", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findAllTime() {
        JsonResult json = new JsonResult();
        try {
            List<Time> list = timeService.findAllTime();
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

    @RequestMapping("/findTimeById")
    @ResponseBody
    @ApiOperation(value = "根据id查询时间段", httpMethod = "GET", response = JsonResult.class)
    public JsonResult findTimeById(Integer id) {
        JsonResult json = new JsonResult();
        try {
            Time time=timeService.findTimeById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(time);
        } catch (Exception e) {
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }
}
