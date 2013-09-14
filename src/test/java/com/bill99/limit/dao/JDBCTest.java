package com.bill99.limit.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

/**
 * @author jun.bao
 * @since 2013年9月12日
 */
public class JDBCTest {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"context/limit-server-service.xml");
		System.out.println(context.getBeanDefinitionCount());
		JdbcTemplate jdbcTemplate = (JdbcTemplate) context
				.getBean("jdbcTemplate");
		org.junit.Assert.assertNotNull(jdbcTemplate);

		String sql = "select id,CONTEXT_NAME,NODE_COUNT from LIMIT_CONTEXT_CONFIG where id=1";
		jdbcTemplate.query(sql, new RowCallbackHandler() {

			@Override
			public void processRow(ResultSet rs) throws SQLException {
				System.out.println(rs.getString("id"));
				System.out.println(rs.getString("CONTEXT_NAME"));
			}
		});
	}
}
