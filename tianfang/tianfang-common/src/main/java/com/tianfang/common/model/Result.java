package com.tianfang.common.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Result<T> implements Serializable{
	
	public Result(T t) {
		this.result = t;
	}

	@Getter
	@Setter
	private T result;
	
}
