package com.github.zj.dreamly.simple.security.spec;

import com.github.zj.dreamly.simple.security.enums.HttpMethod;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 苍海之南
 */
@Data
public class SpecRegistry {
	private List<Spec> specList = new ArrayList<>();

	public SpecRegistry add(HttpMethod httpMethod, String path, String expression) {
		specList.add(new Spec(httpMethod, path, expression));
		return this;
	}
}

