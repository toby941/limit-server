package com.bill99.limit.util;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import com.bill99.limit.domain.ContextConfig;
import com.bill99.limit.domain.TokenPoolConfig;
import com.bill99.limit.domain.TokenQueueConfig;

/**
 * @author jun.bao
 * @since 2013年9月9日
 */
public class JAXBUtilTest {

	@Test
	public void testXml2Bean() {
		// fail("Not yet implemented");
	}

	@Test
	public static String testBean2Xml(Object bean) {
		String xmlString = null;
		JAXBContext context;
		StringWriter writer;
		if (null == bean)
			return xmlString;
		try {
			// 下面代码将对象转变为xml
			context = JAXBContext.newInstance(bean.getClass());
			Marshaller m = context.createMarshaller();
			writer = new StringWriter();
			m.marshal(bean, writer);
			xmlString = writer.toString();
			System.out.println(xmlString);
			return xmlString;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xmlString;
	}

	public static void main(String[] args) {

		testBean2Xml(getContextConfig());
	}

	public static ContextConfig getContextConfig() {
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
