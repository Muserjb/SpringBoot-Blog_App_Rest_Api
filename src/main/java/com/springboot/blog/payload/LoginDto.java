package com.springboot.blog.payload;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Api(value = "Blog_app login model information")
@Data
public class LoginDto {

    @ApiModelProperty(value = "Blog_app login email or username")
    private String usernameOrEmail;

    @ApiModelProperty(value = "Blog_app login password")
    private String password;
}
