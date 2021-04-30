package com.github.zj.dreamly.tool.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.TreeMap;

/**
 * @author bitlib
 * @date 2019-09-10 12:56
 * @description Bitlib 加解密工具类
 **/
public class RsaUtils {

	/**
	 * 获取签名
	 *
	 * @param paramsTreeMap TreeMap数据结构的参数集
	 * @param privateKey    私钥
	 * @return 签名数据
	 */
	public static String getHexSign(Map<String, String> paramsTreeMap, String privateKey) {
		String verifySignResult = null;

		if (CollectionUtil.isEmpty(paramsTreeMap) || StrUtil.isEmpty(privateKey)) {
			return verifySignResult;
		}

		Map<String, String> treeMap = new TreeMap<>(paramsTreeMap);

		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String paramsStr = stringBuilder.toString();
		paramsStr = paramsStr.substring(0, paramsStr.length() - 1);

		try {
			verifySignResult = Rsa.signHex(paramsStr, privateKey, SignAlgorithm.SHA256withRSA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifySignResult;
	}

	/**
	 * 校验签名
	 *
	 * @param paramsTreeMap TreeMap数据结构的参数集
	 * @param publicKey     公钥
	 * @param sign          签名
	 * @return true: 校验成功 false: 校验失败
	 */
	public static boolean verifyHexSign(Map<String, String> paramsTreeMap, String publicKey, String sign) {
		boolean verifyResult = false;

		if (CollectionUtil.isEmpty(paramsTreeMap) || StringUtils.isEmpty(publicKey) || StringUtils.isEmpty(sign)) {
			return verifyResult;
		}

		Map<String, String> treeMap = new TreeMap<>(paramsTreeMap);

		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> entry : treeMap.entrySet()) {
			stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
		}
		String paramsStr = stringBuilder.toString();
		paramsStr = paramsStr.substring(0, paramsStr.length() - 1);
		try {
			verifyResult = Rsa.verifyHex(paramsStr, publicKey, sign, SignAlgorithm.SHA256withRSA);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return verifyResult;
	}

	/**
	 * 公钥加密
	 *
	 * @param sourceData   需要加密的数据
	 * @param publicKeyStr 公钥
	 * @return 加密后的数据
	 */
	public static String encryptHexData(String sourceData, String publicKeyStr) {
		try {
			return Rsa.encryptHex(sourceData, publicKeyStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 私钥解密
	 *
	 * @param encodeData    需要解密的数据
	 * @param privateKeyStr 私钥
	 * @return 解密后的数据
	 */
	public static String decryptHexData(String encodeData, String privateKeyStr) {
		try {
			return Rsa.decryptHex(encodeData, privateKeyStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
