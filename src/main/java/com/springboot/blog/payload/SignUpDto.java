package com.springboot.blog.payload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Api(value = "Blog_app signup model information")
@Data
public class SignUpDto {

    @ApiModelProperty(value = "Blog_app signup name")
    private String name;

    @ApiModelProperty(value = "Blog_app signup username")
    private String username;

    @ApiModelProperty(value = "Blog_app signup email")
    private String email;

    @ApiModelProperty(value = "Blog_app signup password")
    private String password;
}
