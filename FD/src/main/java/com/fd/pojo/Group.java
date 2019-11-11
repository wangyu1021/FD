package com.fd.pojo;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 团队表
 * @author Riyas
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Group {
	private Integer id;//id
	private String caption;//团长名字
	private String phone;//团长手机
	private Integer num;//团员人数
	private Integer clubId;//所属俱乐部id
	private Integer avtivitiesNum;//发起活动数
	private Double consume;//消费总额
	private Date createTime;//创建时间
}
