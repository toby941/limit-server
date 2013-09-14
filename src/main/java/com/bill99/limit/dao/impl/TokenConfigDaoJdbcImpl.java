package com.bill99.limit.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bill99.limit.dao.TokenConfigDao;
import com.bill99.limit.domain.ContextConfig;
import com.bill99.limit.domain.TokenPoolConfig;
import com.bill99.limit.domain.TokenQueueConfig;

/**
 * @author jun.bao
 * @since 2013年9月12日
 */
@Repository
public class TokenConfigDaoJdbcImpl implements TokenConfigDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static String SQL_GET_CONFIG = "select ID,CONTEXT_NAME,NODE_COUNT,ADD_TIME,UPDATE_TIME,SNAPSHOT_SEND_INTERVAL"
			+ " from LIMIT_CONTEXT_CONFIG where CONTEXT_NAME=?";

	public ContextConfig getContextConfig(final String contextName) {

		Object o = jdbcTemplate.query(SQL_GET_CONFIG,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, contextName);
					}
				}, new ResultSetExtractor() {
					@Override
					public Object extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						if (rs.next()) {
							ContextConfig config = new ContextConfig();
							config.setId(rs.getString("ID"));
							config.setContextName(rs.getString("CONTEXT_NAME"));
							config.setSnapshotSendInterval(rs
									.getInt("SNAPSHOT_SEND_INTERVAL"));
							return config;
						}
						return null;
					}
				});

		if (o != null) {
			ContextConfig config = (ContextConfig) o;
			String contextId = config.getId();
			List<TokenPoolConfig> tokenPoolConfigs = getTokenPoolConfigs(contextId);
			if (tokenPoolConfigs != null && tokenPoolConfigs.size() > 0) {
				for (TokenPoolConfig poolConfig : tokenPoolConfigs) {
					String tokenPoolId = poolConfig.getId();
					List<TokenQueueConfig> tokenQueueConfigs = geTokenQueueConfigs(tokenPoolId);
					poolConfig.setTokenQueueConfigs(tokenQueueConfigs);
				}
				config.setTokenPoolConfigs(tokenPoolConfigs);
			}
			return config;
		}

		return null;
	}

	final private static String SQL_GET_TOKEN_POOL_CONFIG = "select ID,REQUEST_URL,CONTEXT_ID,TOKEN_COUNT,REQ_TIMEOUT,"
			+ "HOLD_TIMEOUT,CONTENT_TYPE,REQUEST_KEY,UPDATE_TIME,TOKEN_REQUEST_URL,DEPLOY_TYPE"
			+ " from LIMIT_TOKEN_POOL_CONFIG where CONTEXT_ID=?";

	@SuppressWarnings("unchecked")
	public List<TokenPoolConfig> getTokenPoolConfigs(final String contextId) {
		return jdbcTemplate.query(SQL_GET_TOKEN_POOL_CONFIG,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, contextId);
					}
				}, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						TokenPoolConfig poolConfig = new TokenPoolConfig();
						poolConfig.setId(rs.getString("ID"));
						poolConfig.setRequestUrl(rs.getString("REQUEST_URL"));
						poolConfig.setContextId(rs.getString("CONTEXT_ID"));
						poolConfig.setTotalTokenCount(rs.getInt("TOKEN_COUNT"));
						poolConfig.setRequestTimeout(rs.getInt("REQ_TIMEOUT"));
						poolConfig.setHoldTimeout(rs.getInt("HOLD_TIMEOUT"));
						poolConfig.setContentType(rs.getString("CONTENT_TYPE"));
						poolConfig.setRequestKey(rs.getString("REQUEST_KEY"));
						Date sqlDate = rs.getDate("UPDATE_TIME");
						java.util.Date updateTime = new java.util.Date(sqlDate
								.getTime());
						poolConfig.setUpdateTime(updateTime);
						poolConfig.setDeployType(rs.getInt("DEPLOY_TYPE"));
						poolConfig.setTokenRequestUrl(rs
								.getString("TOKEN_REQUEST_URL"));
						return poolConfig;
					}
				});
	}

	final private static String SQL_GET_TOKEN_QUEUE_CONFIG = "select ID,UPDATE_TIME,"
			+ "TOKEN_COUNT,VALUE,PRIORITY,TOKEN_POOL_ID from LIMIT_TOKEN_QUEUE where TOKEN_POOL_ID=?";

	@SuppressWarnings("unchecked")
	public List<TokenQueueConfig> geTokenQueueConfigs(final String tokenPoolId) {
		return jdbcTemplate.query(SQL_GET_TOKEN_QUEUE_CONFIG,
				new PreparedStatementSetter() {
					@Override
					public void setValues(PreparedStatement ps)
							throws SQLException {
						ps.setString(1, tokenPoolId);
					}
				}, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
						TokenQueueConfig config = new TokenQueueConfig();
						config.setId(rs.getString("ID"));
						Date sqlDate = rs.getDate("UPDATE_TIME");
						java.util.Date updateTime = new java.util.Date(sqlDate
								.getTime());
						config.setUpdateTime(updateTime);
						config.setTokenCount(rs.getInt("TOKEN_COUNT"));
						config.setValue(rs.getString("VALUE"));
						config.setPriority(rs.getInt("PRIORITY"));
						config.setTokenPoolId(rs.getString("TOKEN_POOL_ID"));
						return config;
					}
				});
	}

}
