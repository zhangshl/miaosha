package com.simple.enums;

/**
 * @author zhangshl
 */

public enum ResultEnum {
	FAIL(0, "失败"),
	SUCCESS(1, "成功")
	;

	private Integer code;
	private String desc;

	ResultEnum(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public Integer getCode() {
		return code;
	}
	public String getDesc() {
		return desc;
	}

}
