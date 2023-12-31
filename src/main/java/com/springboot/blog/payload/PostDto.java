package com.springboot.blog.payload;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;


@Api(value = "Post Model Information")
@Data
public class PostDto {
    private long id;

    // title should not be null  or empty
    // title should have at least 2 characters
    @ApiModelProperty(value = "Blog Post title")
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least 2 characters")
    private String title;

    // post description should be not null or empty
    // post description should have at least 10 characters
    @ApiModelProperty(value = "Blog Post description")
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least 10 characters")
    private String description;

    // post content should not be null or empty
    @ApiModelProperty(value = "Blog Post content")
    @NotEmpty
    private String content;


    @ApiModelProperty(value = "Blog Post comments")
    private Set<CommentDto> comments;
}
