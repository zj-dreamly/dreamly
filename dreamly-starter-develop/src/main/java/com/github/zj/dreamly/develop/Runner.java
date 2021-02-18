package com.github.zj.dreamly.develop;

import com.github.zj.dreamly.develop.support.CodeGenerator;

/**
 * <h2>CodeGenerator</h2>
 *
 * @author: Chill
 * @since: 0.0.1
 **/
public class Runner {

	/**
	 * 代码生成的模块名
	 */
	private static final String CODE_NAME = "halyard";
	/**
	 * author
	 */
	private static final String AUTHOR = "苍海之南";
	/**
	 * 代码生成的包名
	 */
	private static final String PACKAGE_NAME = "com.synco.ormco";
	/**
	 * 需要去掉的表前缀
	 */
	private static final String[] TABLE_PREFIX = {""};
	/**
	 * 需要生成的表名(两者只能取其一)
	 */
	private static final String[] INCLUDE_TABLES = {"video_comment"};

	/**
	 * 需要排除的表名(两者只能取其一)
	 */
	private static final String[] EXCLUDE_TABLES = {};
	/**
	 * 基础业务字段
	 */
	private static final String[] SUPER_ENTITY_COLUMNS = {"id", "create_time", "update_time"};

	public static void main(String[] args) {
		CodeGenerator generator = new CodeGenerator();
		generator.setCodeName(CODE_NAME);
		generator.setAuthor(AUTHOR);
		generator.setPackageName(PACKAGE_NAME);
		generator.setIncludeTables(INCLUDE_TABLES);
		generator.setExcludeTables(EXCLUDE_TABLES);
		generator.setTablePrefix(TABLE_PREFIX);
		generator.run();
	}
}
