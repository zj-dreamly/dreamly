package com.github.zj.dreamly.simple.security.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 17829
 */
@Data
@Builder
@ApiModel(description = "认证信息")
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public class AuthInfo {
	@ApiModelProperty(value = "用户id")
	private Long id;
	@ApiModelProperty(value = "令牌")
	private String accessToken;
	@ApiModelProperty(value = "刷新token")
	private String refreshToken;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "过期时间")
	private long expiresIn;
	@ApiModelProperty(value = "认证类型")
	private String tokenType;
}
