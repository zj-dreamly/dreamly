package com.github.zj.dreamly.security.jwt.el;

import com.github.zj.dreamly.security.jwt.jwt.JwtUserOperator;
import com.github.zj.dreamly.security.jwt.jwt.JwtUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author 苍海之南
 */
@Slf4j
@AllArgsConstructor
@SuppressWarnings({"WeakerAccess", "unused"})
public class PreAuthorizeExpressionRoot {
	private final JwtUserOperator jwtUserOperator;

	/**
	 * 匿名即可访问
	 *
	 * @return true
	 */
	public boolean anon() {
		return true;
	}

	/**
	 * 登录才能访问
	 *
	 * @return 如已登录，则返回true
	 */
	public boolean hasLogin() {
		return jwtUserOperator.getUser() != null;
	}

	/**
	 * 拥有指定角色才能访问
	 *
	 * @param role 角色
	 * @return 如果拥有指定角色，则返回true
	 */
	public boolean hasRole(String role) {
		return hasAnyRoles(role);
	}

	/**
	 * 拥有所有指定角色才能访问
	 *
	 * @param roles 角色
	 * @return 如果拥有roles所有角色，则返回true
	 */
	public boolean hasAllRoles(String... roles) {
		JwtUser jwtUser = jwtUserOperator.getUser();
		if (jwtUser == null) {
			return false;
		}

		List<String> userRoles = jwtUser.getRoles();
		if (CollectionUtils.isEmpty(userRoles)) {
			return false;
		}
		List<String> roleList = Arrays.asList(roles);
		return userRoles.containsAll(roleList);
	}

	/**
	 * 拥有指定角色之一即可访问
	 *
	 * @param roles 角色
	 * @return 如果拥有roles元素之一，则返回true
	 */
	public boolean hasAnyRoles(String... roles) {
		JwtUser jwtUser = jwtUserOperator.getUser();
		if (jwtUser == null) {
			return false;
		}

		List<String> userRoles = jwtUser.getRoles();
		List<String> roleList = Arrays.asList(roles);
		if (CollectionUtils.isEmpty(userRoles)) {
			return false;
		}

		boolean checkResult = userRoles.stream()
			.anyMatch(roleList::contains);
		if (!checkResult) {
			log.warn("Role mismatch，userRolesFromToken = {}, roles = {}", userRoles, roleList);
		}
		return checkResult;
	}

	/**
	 * 拥有指定权限即可访问
	 *
	 * @param permission 权限
	 * @return 如果拥有该权限，则返回true
	 */
	public boolean hasPermission(String permission) {
		JwtUser jwtUser = jwtUserOperator.getUser();
		if (jwtUser == null) {
			return false;
		}

		List<String> userPermissions = jwtUser.getPermissions();
		if (CollectionUtils.isEmpty(userPermissions)) {
			return false;
		}

		boolean checkResult = userPermissions.stream()
			.anyMatch(p -> p.equals(permission));
		if (!checkResult) {
			log.warn("Permission mismatch，userPermissionsFromToken = {}, roles = {}", userPermissions, permission);
		}
		return checkResult;
	}
}
