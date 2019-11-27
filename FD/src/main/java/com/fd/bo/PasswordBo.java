package com.fd.bo;

import lombok.Data;

@Data
public class PasswordBo {
    private String loginId;
    private String newPassword;
    private String oldPassword;
}
