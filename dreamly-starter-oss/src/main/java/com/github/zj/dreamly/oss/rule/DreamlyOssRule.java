package com.github.zj.dreamly.oss.rule;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.github.zj.dreamly.tool.util.ToolUtils;
import lombok.AllArgsConstructor;

/**
 * 默认存储桶生成规则
 *
 * @author Chill
 */
@AllArgsConstructor
public class DreamlyOssRule implements OssRule {

	@Override
	public String bucketName(String bucketName) {
		return bucketName;
	}

	@Override
	public String fileName(String originalFilename) {
		return "upload" + StrUtil.SLASH + DateUtil.today() + StrUtil.SLASH + IdUtil.fastSimpleUUID() + StrUtil.DOT + ToolUtils.getFileExtension(originalFilename);
	}

}
