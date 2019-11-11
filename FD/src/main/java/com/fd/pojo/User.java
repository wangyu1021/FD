package com.fd.pojo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 * @author Riyas
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	private Integer id;//id
	private String nickName;//昵称
	private String role;//角色
	private String name;//姓名
	private String phone;//手机号
	private Integer userId;//邀请人
	private Integer superior;//所属团长
	private Integer integral;//积分
	private Integer growthValue;//成长值
	private Double pushMoney;//提成收益
	private Double availableIncome;//可用收益
	private Integer numberTeam;//参与战队数
	private Integer captain;//是否为队长
	private Integer createTeam;//创建战队数
	private String club;//所属俱乐部
	private List<User> users;
}
