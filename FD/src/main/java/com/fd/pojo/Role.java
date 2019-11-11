package com.fd.pojo;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 角色表
 * @author Riyas
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Role {
	private Integer id;//id
	private String name;//角色名
	private Date createTime;//创建时间
	private List<Integer> pIds;//已授权的权限id
}
