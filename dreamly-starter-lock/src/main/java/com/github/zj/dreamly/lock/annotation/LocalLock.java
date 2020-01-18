package com.github.zj.dreamly.lock.annotation;

import java.lang.annotation.*;

/**
 * <h2>LocalLock</h2>
 *
 * @author: 苍海之南
 * @since: 2020-01-18 11:15
 **/
@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LocalLock {
	/**
	 * key
	 */
	String key() default "";
}
