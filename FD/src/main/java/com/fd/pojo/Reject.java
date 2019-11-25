package com.fd.pojo;

import lombok.Data;

/**
 * 战队申请驳回表
 */
@Data
public class Reject {
    private Integer id;
    /**
     * 申请id
     */
    private Integer auditId;
    /**
     * 驳回原因
     */
    private String reason;
}
