package com.github.zj.dreamly.simple.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author 苍海之南
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
