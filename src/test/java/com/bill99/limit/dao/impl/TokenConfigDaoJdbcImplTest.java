package com.bill99.limit.dao.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bill99.limit.BaseTest;
import com.bill99.limit.dao.TokenConfigDao;
import com.bill99.limit.domain.ContextConfig;

/**
 * @author jun.bao
 * @since 2013年9月13日
 */
public class TokenConfigDaoJdbcImplTest extends BaseTest {

	@Autowired
	private TokenConfigDao dao;

	@Test
	public void testGetContextConfig() {
		ContextConfig config = dao.getContextConfig("demo");
		System.out.println(config);
	}

}
