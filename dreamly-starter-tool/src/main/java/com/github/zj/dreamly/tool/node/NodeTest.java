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

import cn.hutool.json.JSONUtil;
import com.github.zj.dreamly.tool.api.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Chill
 * @since 0.0.1
 */
public class NodeTest {

	public static void main(String[] args) {
		List<ForestNode> list = new ArrayList<>();
		list.add(new ForestNode<>(1L, 0L, 1));
		list.add(new ForestNode<>(2L, 0L, 2));
		list.add(new ForestNode<>(3L, 1L,3));
		list.add(new ForestNode<>(4L, 2L,4));
		list.add(new ForestNode<>(5L, 3L, 5));
		list.add(new ForestNode<>(6L, 4L, 6));
		list.add(new ForestNode<>(7L, 3L, 7));
		list.add(new ForestNode<>(8L, 5L, 8));
		list.add(new ForestNode<>(9L, 6L, 9));
		list.add(new ForestNode<>(10L, 9L, 10));
		List<ForestNode> tns = ForestNodeMerger.merge(list);
		tns.forEach(node ->
			System.out.println(JSONUtil.toJsonStr(node))
		);
	}

}
