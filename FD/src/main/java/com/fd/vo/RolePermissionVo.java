package com.fd.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色权限
 * @author Riyas
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePermissionVo {
	private Integer roleId;
	private Integer permissionId;
}
