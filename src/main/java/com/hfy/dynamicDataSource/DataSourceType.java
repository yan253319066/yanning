package com.hfy.dynamicDataSource;

public enum DataSourceType {
	DEFAULT("默认数据源，即主要数据源", "dataSourceDefault"), SOURCE2("测试数据源", "dataSource2");
	// 成员变量
	private String text;
	private String id;

	// 普通方法
	public static String getName(String id) {
		for (DataSourceType s : DataSourceType.values()) {
			if (s.getId() == id) {
				return s.getText();
			}
		}
		return null;
	}

	// 构造方法
	private DataSourceType(String text, String id) {
		this.setText(text);
		this.setId(id);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
