package com.dianping.puma.common.config;

public interface ConfigChangeListener {

	void onConfigChange(String oldValue, String newValue);
}
