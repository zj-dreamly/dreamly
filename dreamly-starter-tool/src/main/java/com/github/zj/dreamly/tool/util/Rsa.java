package com.github.zj.dreamly.tool.util;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class Rsa {

	public static final String ENTER = "\\n";

	/**
	 * 获取密钥对
	 *
	 * @return 密钥对
	 */
	public static KeyPair getKeyPair() throws Exception {
		KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		generator.initialize(1024);
		return generator.generateKeyPair();
	}

	/**
	 * 获取私钥
	 *
	 * @param privateKey 私钥字符串
	 * @return 私钥
	 */
	private static PrivateKey getPrivateKey(String privateKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.getDecoder().decode(privateKey.getBytes());
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decodedKey);
		return keyFactory.generatePrivate(keySpec);
	}

	/**
	 * 获取公钥
	 *
	 * @param publicKey 公钥字符串
	 * @return 公钥
	 */
	private static PublicKey getPublicKey(String publicKey) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] decodedKey = Base64.getDecoder().decode(publicKey.getBytes());
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(decodedKey);

		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 根据对应算法加密
	 *
	 * @param data      待加密数据
	 * @param publicKey 公钥
	 * @param algorithm 算法
	 */
	public static String encryptHexWithAlgorithm(String data, String publicKey, String algorithm) throws Exception {
		if (publicKey == null || StringUtils.isEmpty(data)) {
			throw new Exception("加密公钥为空, 请设置");
		}
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		PublicKey rsaPublicKey = getPublicKey(publicKey);
		Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
		byte[] plainTextData = data.getBytes();
		byte[] output = cipher.doFinal(plainTextData);
		return byte2hex(output);

	}

	/**
	 * RSA公钥加密
	 *
	 * @param str       加密字符串
	 * @param publicKey 公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encrypt(String str, String publicKey) throws Exception {
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		//base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * RSA公钥加密
	 *
	 * @param str       加密字符串
	 * @param publicKey 公钥
	 * @return 密文
	 * @throws Exception 加密过程中的异常信息
	 */
	public static String encryptHex(String str, String publicKey) throws Exception {
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		//base64编码的公钥
		byte[] decoded = Base64.getDecoder().decode(publicKey);
		RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
		//RSA加密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, pubKey);
		return byte2hex(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str        加密字符串
	 * @param privateKey 私钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static String decrypt(String str, String privateKey) throws Exception {
		privateKey = privateKey.replaceAll(ENTER, StringUtils.EMPTY);
		//64位解码加密后的字符串
		byte[] inputByte = Base64.getDecoder().decode(str);
		//base64编码的私钥
		byte[] decoded = Base64.getDecoder().decode(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return new String(cipher.doFinal(inputByte));
	}

	/**
	 * RSA私钥解密
	 *
	 * @param str        加密字符串
	 * @param privateKey 私钥
	 * @return 明文
	 * @throws Exception 解密过程中的异常信息
	 */
	public static String decryptHex(String str, String privateKey) throws Exception {
		privateKey = privateKey.replaceAll(ENTER, StringUtils.EMPTY);
		//64位解码加密后的字符串
		byte[] inputByte = hexStr2byte(str);
		//base64编码的私钥
		byte[] decoded = Base64.getDecoder().decode(privateKey);
		RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
		//RSA解密
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, priKey);
		return new String(cipher.doFinal(inputByte));
	}

	/**
	 * 签名
	 *
	 * @param data       待签名数据
	 * @param privateKey 私钥
	 * @return 签名
	 */
	public static String sign(String data, String privateKey, SignAlgorithm signAlgorithm) throws Exception {
		privateKey = privateKey.replaceAll(ENTER, StringUtils.EMPTY);
		byte[] signData = sign(data.getBytes(), privateKey, signAlgorithm);
		return Base64.getEncoder().encodeToString(signData);
	}

	public static String signHex(String data, String privateKey, SignAlgorithm signAlgorithm) throws Exception {
		privateKey = privateKey.replaceAll(ENTER, StringUtils.EMPTY);
		byte[] signData = sign(data.getBytes(), privateKey, signAlgorithm);
		return byte2hex(signData);
	}

	public static byte[] sign(byte[] data, String privateKey, SignAlgorithm signAlgorithm) throws Exception {
		privateKey = privateKey.replaceAll(ENTER, StringUtils.EMPTY);
		PrivateKey priKey = getPrivateKey(privateKey);
		Signature signature = Signature.getInstance(signAlgorithm.getAlgorithm());
		signature.initSign(priKey);
		signature.update(data);
		return signature.sign();
	}

	/**
	 * 验签
	 *
	 * @param srcData   原始字符串
	 * @param publicKey 公钥
	 * @param sign      签名
	 * @return 是否验签通过
	 */
	public static boolean verify(String srcData, String publicKey, String sign, SignAlgorithm signAlgorithm) throws Exception {
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		return verify(srcData.getBytes(), publicKey, Base64.getDecoder().decode(sign), signAlgorithm);
	}

	public static boolean verifyHex(String srcData, String publicKey, String sign, SignAlgorithm signAlgorithm) throws Exception {
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		return verify(srcData.getBytes(), publicKey, hexStr2byte(sign), signAlgorithm);
	}

	public static boolean verify(byte[] srcData, String publicKey, byte[] sign, SignAlgorithm signAlgorithm) throws Exception {
		publicKey = publicKey.replaceAll(ENTER, StringUtils.EMPTY);
		PublicKey pubKey = getPublicKey(publicKey);
		Signature signature = Signature.getInstance(signAlgorithm.getAlgorithm());
		signature.initVerify(pubKey);
		signature.update(srcData);
		return signature.verify(sign);
	}

	/**
	 * Description：将二进制转换成16进制字符串
	 *
	 * @return String
	 * @author name：
	 */
	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();
		String stmp;
		for (byte value : b) {
			stmp = (Integer.toHexString(value & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * Description：将十六进制的字符串转换成字节数据
	 *
	 * @return byte[]
	 * @author name：
	 */
	private static byte[] hexStr2byte(String strhex) {
		if (strhex == null) {
			return null;
		}
		int l = strhex.length();
		if (l % 2 == 1) {
			return null;
		}
		byte[] b = new byte[l / 2];
		for (int i = 0; i != l / 2; i++) {
			b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
		}
		return b;
	}

	public static void main(String[] args) {
		try {

			String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC0PZZAbTqhPhmp9oILXQHyyWxxFIU6g+hu3YpMBozC75ZZcecZL+sw6p/BonHkP9qDPX2Z7rEIgCIpevbi+CA3enIFghN7WdTHf+ALSiYIk87WXUGfa2y55nQUzInDwJG4cADZ4moYXG3gfBtAqibbI3OMz5MqZa7gbdhtv1kw6wIDAQAB";
			String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALQ9lkBtOqE+Gan2\n" +
				"ggtdAfLJbHEUhTqD6G7dikwGjMLvlllx5xkv6zDqn8GiceQ/2oM9fZnusQiAIil6\n" +
				"9uL4IDd6cgWCE3tZ1Md/4AtKJgiTztZdQZ9rbLnmdBTMicPAkbhwANniahhcbeB8\n" +
				"G0CqJtsjc4zPkyplruBt2G2/WTDrAgMBAAECgYEArEdZwIcnTUwAV9bJgncKD7i7\n" +
				"sHJ+zembV6zmLbjs/r7nJOOckxScZ4s73GebGSJ3iI5T6bie+pMPFDr2lQe6MfHI\n" +
				"bhend4IhQA+q4Gh38zp0BPmepiPjXqQwezvuFBBJ1cCr4SYD2hqx0OVnyC3sA7LR\n" +
				"eKyuAnKCzh0qcR6aSQECQQDjWlFzrW2d6LzNkuLzo5I9+8ZqWGc/yh6x2R67ZznU\n" +
				"PE53hoM3smAig9qrlQtjoHfRgP53wMAug+RN+wDcSFtBAkEAyvOTyYXgUONLoXGm\n" +
				"0PZlOgNrLIscjNwxsDUfY96C5pR4x1+Yh18kyJLScp3v8QWb5Kfgoe1492bgkFQ9\n" +
				"x3udKwJAA9RxqtExF4fkJlJjIFeRDxo+rWvv0VNGURinO+DxSHH7oGfTrgyDMhGm\n" +
				"jV1lY7hATHcv0jSdCCuQnP+tdAiEAQJBAJwVDBm2TjenNukooOSgOmWNb4VIT2K9\n" +
				"jbE4ibWi0QVINkMO8B1cPMvMrvDbKkcwyx3lRksCeT+77QTS5Nhf5xUCQDJLuaZP\n" +
				"STnoq/9SN9ux+o7Dnrc8SzMYTwCJj+/qCWOMjiRGs5TknWRB0tDjG5ioEtutvZ3o\n" +
				"FeRhepsyYaII2h0=";

			String data = "app_key=7e289a2484f4368dbafbd1e5c7d06903&recipient=+8618123972798&sign_name=短信&status_callback_url=http://requestbin.fullcontact.com/scvavdsc&template_id=110233&template_params=14,李月,2019/12/12,12:18";
			String sign = Rsa.signHex(data, privateKey, SignAlgorithm.SHA256withRSA);
			System.out.println(sign);
			boolean flag = Rsa.verifyHex(data, publicKey, sign, SignAlgorithm.SHA256withRSA);
			System.out.println(flag);

			String encryptHex = Rsa.encryptHex(String.valueOf(18627144531L), publicKey);
			System.out.println(encryptHex);
			System.out.println(Rsa.decryptHex(encryptHex, privateKey));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("加解密异常");
		}
	}
}
