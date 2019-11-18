package com.fd.controller;

import com.fd.pojo.JsonResult;
import com.fd.pojo.MotionType;
import com.fd.service.MotionTypeService;
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
@RequestMapping("/motionType")
@Api(value = "/motionTyoe" , tags="运动类型接口")
public class MotionTypeController {
    @Resource
    private MotionTypeService motionTypeServicel;

    @RequestMapping("/findAllMotionType")
    @ResponseBody
    @ApiOperation(value = "查询所有运动类型", httpMethod = "GET",response = JsonResult.class)
    public JsonResult findAllMotionType(){
        JsonResult json=new JsonResult();
        try{
            List<MotionType> list=motionTypeServicel.findAllMotionType();
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(list);
        }catch ( Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }

    @RequestMapping("/findMotionTypeById")
    @ResponseBody
    @ApiOperation(value = "根据id查询运动类型", httpMethod = "GET",response = JsonResult.class)
    public JsonResult findMotionTypeById(Integer id){
        JsonResult json=new JsonResult();
        try{
            MotionType motionType=motionTypeServicel.findMotionTypeById(id);
            json.setState(1);
            json.setMessage("查询成功");
            json.setData(motionType);
        }catch ( Exception e){
            e.printStackTrace();
            json.setState(0);
            json.setMessage(e.getMessage());
        }
        return json;
    }
}
