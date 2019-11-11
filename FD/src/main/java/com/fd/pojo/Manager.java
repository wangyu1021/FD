package com.fd.pojo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 店长表
 * @author Riyas
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager {
	@ApiModelProperty(hidden = true)
	private Integer id;//自动生成的编号
	private String jobNumber;//工号
	private String name;//姓名
	private String phone;//手机号
	private String nickName;//昵称
	@ApiModelProperty(hidden = true)
	private String loginId;//登录账号，工号即为登录账号
	private String password;//密码
	private String address;//地址
	private Integer clubId;//所属俱乐部
	@ApiModelProperty(hidden = true)
	private Date createTime;//创建时间
}
