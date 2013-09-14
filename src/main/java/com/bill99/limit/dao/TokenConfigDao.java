package com.bill99.limit.dao;

import com.bill99.limit.domain.ContextConfig;

/**
 * @author jun.bao
 * @since 2013年9月12日
 */
public interface TokenConfigDao {
	public ContextConfig getContextConfig(final String contextName);
}
