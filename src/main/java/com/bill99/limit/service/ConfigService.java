package com.bill99.limit.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bill99.limit.dao.TokenConfigDao;
import com.bill99.limit.domain.ContextConfig;
import com.bill99.limit.domain.TokenPoolConfig;
import com.bill99.limit.domain.TokenQueueConfig;
import com.bill99.limit.util.JAXBUtil;

/**
 * @author jun.bao
 * @since 2013年9月9日
 */
@Service
public class ConfigService {

	@Autowired
	private TokenConfigDao tokenConfigDao;

	/**
	 * 获取token池配置信息
	 * 
	 * @param context
	 *            tomcat server.xml中配置的客户端应用对应context节点的name
	 * @return xml格式的string字符串
	 */
	public String getTokenConfig(String context) {
		ContextConfig config = tokenConfigDao.getContextConfig(context);
		if (config != null) {
			return JAXBUtil.bean2Xml(config);
		}
		return null;
	}

	private ContextConfig getDemo() {
		List<TokenQueueConfig> rList = new ArrayList<TokenQueueConfig>();
		for (int i = 0; i < 5; i++) {
			TokenQueueConfig queueConfig = new TokenQueueConfig();
			queueConfig.setHoldTimeout(i + 2);
			queueConfig.setPriority(i + 2);
			queueConfig.setRequestTimeout(i + 2);
			queueConfig.setTokenCount(i + 2);
			queueConfig.setUpdateTime(Calendar.getInstance().getTime());
			queueConfig.setValue(RandomStringUtils.random(6, "xsd324dvxvg"));
			rList.add(queueConfig);

		}

		TokenPoolConfig poolConfig = new TokenPoolConfig();
		poolConfig.setTokenQueueConfigs(rList);

		poolConfig.setRequestUrl("/upload");
		poolConfig.setTotalTokenCount(400);
		poolConfig.setContentType("xml");
		poolConfig.setHoldTimeout(20);
		poolConfig.setRequestKey("name-id-code");
		poolConfig.setRequestTimeout(5);
		poolConfig.setUpdateTime(Calendar.getInstance().getTime());

		List<TokenPoolConfig> list = new ArrayList<TokenPoolConfig>();
		list.add(poolConfig);
		list.add(poolConfig);

		ContextConfig config = new ContextConfig();
		config.setContextName("demo");
		config.setTokenPoolConfigs(list);
		config.setSnapshotSendInterval(20);

		return config;
	}

}
