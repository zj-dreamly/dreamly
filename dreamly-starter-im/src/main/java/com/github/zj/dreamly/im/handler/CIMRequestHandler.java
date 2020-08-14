/*
 * Copyright 2013-2019 Xia Jun(3979434@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ***************************************************************************************
 *                                                                                     *
 *                        Website : http://www.farsunset.com                           *
 *                                                                                     *
 ***************************************************************************************
 */
package com.github.zj.dreamly.im.handler;

import com.github.zj.dreamly.im.model.CIMSession;
import com.github.zj.dreamly.im.model.SentBody;

/**
 * 请求处理接口,所有的请求实现必须实现此接口
 */

public interface CIMRequestHandler {

	/**
	 * 处理收到客户端从长链接发送的数据
	 */
	void process(CIMSession session, SentBody message);
}
