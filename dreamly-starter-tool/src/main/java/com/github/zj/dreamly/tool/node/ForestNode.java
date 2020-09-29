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
package com.github.zj.dreamly.tool.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 森林节点类
 *
 * @author Chill
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("all")
public class ForestNode<T> extends BaseNode {

	/**
	 * 节点对象
	 */
	private T content;

	public ForestNode(Long id, Long parentId, T content) {
		this.id = id;
		this.parentId = parentId;
		this.content = content;
	}

}
