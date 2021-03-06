/**
 * Copyright (c) 2018-2028, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE 3.0;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.zj.dreamly.oss;

import cn.hutool.core.util.StrUtil;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PolicyConditions;
import com.aliyun.oss.model.PutObjectResult;
import com.github.zj.dreamly.oss.model.DreamlyFile;
import com.github.zj.dreamly.oss.model.OssFile;
import com.github.zj.dreamly.oss.properties.OssProperties;
import com.github.zj.dreamly.oss.rule.OssRule;
import com.github.zj.dreamly.tool.util.JsonUtils;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * AliossTemplate
 *
 * @author Chill
 */
@AllArgsConstructor
public class AliossTemplate {

	private final OSSClient ossClient;

	private final OssProperties ossProperties;

	private final OssRule ossRule;

	@SneakyThrows
	public void makeBucket(String bucketName) {
		if (!bucketExists(bucketName)) {
			ossClient.createBucket(getBucketName(bucketName));
		}
	}

	@SneakyThrows
	public void removeBucket(String bucketName) {
		ossClient.deleteBucket(getBucketName(bucketName));
	}

	@SneakyThrows
	public boolean bucketExists(String bucketName) {
		return ossClient.doesBucketExist(getBucketName(bucketName));
	}

	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), fileName);
	}

	@SneakyThrows
	public void copyFile(String bucketName, String fileName, String destBucketName, String destFileName) {
		ossClient.copyObject(getBucketName(bucketName), fileName, getBucketName(destBucketName), destFileName);
	}

	@SneakyThrows
	public OssFile statFile(String fileName) {
		return statFile(ossProperties.getBucketName(), fileName);
	}

	@SneakyThrows
	public OssFile statFile(String bucketName, String fileName) {
		ObjectMetadata stat = ossClient.getObjectMetadata(getBucketName(bucketName), fileName);
		OssFile ossFile = new OssFile();
		ossFile.setName(fileName);
		ossFile.setLink(fileLink(ossFile.getName()));
		ossFile.setHash(stat.getContentMD5());
		ossFile.setLength(stat.getContentLength());
		ossFile.setPutTime(stat.getLastModified());
		ossFile.setContentType(stat.getContentType());
		return ossFile;
	}

	@SneakyThrows
	public String filePath(String fileName) {
		return getOssHost().concat(StrUtil.SLASH).concat(fileName);
	}

	@SneakyThrows
	public String filePath(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StrUtil.SLASH).concat(fileName);
	}

	@SneakyThrows
	public String fileLink(String fileName) {
		return getOssHost().concat(StrUtil.SLASH).concat(fileName);
	}

	@SneakyThrows
	public String fileLink(String bucketName, String fileName) {
		return getOssHost(bucketName).concat(StrUtil.SLASH).concat(fileName);
	}

	/**
	 * 文件对象
	 *
	 * @param file 上传文件类
	 */

	@SneakyThrows
	public DreamlyFile putFile(MultipartFile file) {
		return putFile(ossProperties.getBucketName(), file.getOriginalFilename(), file);
	}

	/**
	 * @param fileName 上传文件名
	 * @param file     上传文件类
	 */

	@SneakyThrows
	public DreamlyFile putFile(String fileName, MultipartFile file) {
		return putFile(ossProperties.getBucketName(), fileName, file);
	}

	@SneakyThrows
	public DreamlyFile putFile(String bucketName, String fileName, MultipartFile file) {
		return putFile(bucketName, fileName, file.getInputStream());
	}

	@SneakyThrows
	public DreamlyFile putFile(String fileName, InputStream stream) {
		return putFile(ossProperties.getBucketName(), fileName, stream);
	}

	@SneakyThrows
	public DreamlyFile putFile(String bucketName, String fileName, InputStream stream) {
		return put(bucketName, stream, fileName, false);
	}

	@SneakyThrows
	public DreamlyFile put(String bucketName, InputStream stream, String key, boolean cover) {
		makeBucket(bucketName);
		String originalName = key;
		key = getFileName(key);
		// 覆盖上传
		if (cover) {
			ossClient.putObject(getBucketName(bucketName), key, stream);
		} else {
			PutObjectResult response = ossClient.putObject(getBucketName(bucketName), key, stream);
			int retry = 0;
			int retryCount = 5;
			while (StringUtils.isEmpty(response.getETag()) && retry < retryCount) {
				response = ossClient.putObject(getBucketName(bucketName), key, stream);
				retry++;
			}
		}
		DreamlyFile file = new DreamlyFile();
		file.setOriginalName(originalName);
		file.setName(key);
		file.setLink(fileLink(bucketName, key));
		return file;
	}

	@SneakyThrows
	public void removeFile(String fileName) {
		ossClient.deleteObject(getBucketName(), fileName);
	}

	@SneakyThrows
	public void removeFile(String bucketName, String fileName) {
		ossClient.deleteObject(getBucketName(bucketName), fileName);
	}

	@SneakyThrows
	public void removeFiles(List<String> fileNames) {
		fileNames.forEach(this::removeFile);
	}

	@SneakyThrows
	public void removeFiles(String bucketName, List<String> fileNames) {
		fileNames.forEach(fileName -> removeFile(getBucketName(bucketName), fileName));
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @return String
	 */
	private String getBucketName() {
		return getBucketName(ossProperties.getBucketName());
	}

	/**
	 * 根据规则生成存储桶名称规则
	 *
	 * @param bucketName 存储桶名称
	 * @return String
	 */
	private String getBucketName(String bucketName) {
		return ossRule.bucketName(bucketName);
	}

	/**
	 * 根据规则生成文件名称规则
	 *
	 * @param originalFilename 原始文件名
	 * @return string
	 */
	private String getFileName(String originalFilename) {
		return ossRule.fileName(originalFilename);
	}

	public String getUploadToken() {
		return getUploadToken(ossProperties.getBucketName());
	}

	/**
	 * <p>
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName) {
		// 默认过期时间2小时
		return getUploadToken(bucketName, ossProperties.getArgs().get("expireTime", 3600L));
	}

	/**
	 * <p>
	 * 获取上传凭证，普通上传
	 */
	public String getUploadToken(String bucketName, long expireTime) {
		String baseDir = "upload";

		long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
		Date expiration = new Date(expireEndTime);

		PolicyConditions policyConds = new PolicyConditions();
		// 默认大小限制100M
		policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0,
			ossProperties.getArgs().get("contentLengthRange", 104857600));
		policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, baseDir);

		String postPolicy = ossClient.generatePostPolicy(expiration, policyConds);
		byte[] binaryData = postPolicy.getBytes(StandardCharsets.UTF_8);
		String encodedPolicy = BinaryUtil.toBase64String(binaryData);
		String postSignature = ossClient.calculatePostSignature(postPolicy);

		Map<String, String> respMap = new LinkedHashMap<>(16);
		respMap.put("accessid", ossProperties.getAccessKey());
		respMap.put("policy", encodedPolicy);
		respMap.put("signature", postSignature);
		respMap.put("dir", baseDir);
		respMap.put("host", getOssHost(bucketName));
		respMap.put("expire", String.valueOf(expireEndTime / 1000));
		return JsonUtils.toJsonString(respMap);
	}

	public String getOssHost(String bucketName) {
		String prefix = ossProperties.getEndpoint().contains("https://") ? "https://" : "http://";
		return prefix + getBucketName(bucketName) + StrUtil.DOT + ossProperties.getEndpoint().replaceFirst(prefix, StrUtil.EMPTY);
	}

	public String getOssHost() {
		return getOssHost(ossProperties.getBucketName());
	}

}
