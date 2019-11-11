package com.fd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员登录
 * @author Riyas
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerVo {
	private String loginId;
	private String password;
}
