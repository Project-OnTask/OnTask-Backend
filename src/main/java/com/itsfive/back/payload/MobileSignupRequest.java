package com.itsfive.back.payload;

import javax.validation.constraints.NotBlank;

public class MobileSignupRequest {
	@NotBlank
    private String fname;

    @NotBlank
    private String mobile;
    
    public String getFName() {
        return fname;
    }

    public void setFName(String fname) {
        this.fname = fname;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
