package com.github.zj.dreamly.security.jwt.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author itmuch.com
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JwtUser {
    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 角色
     */
    private List<String> roles;

	/**
	 * 权限
	 */
	private List<String> permissions;
}
