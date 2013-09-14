package com.bill99.limit.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bill99.limit.service.ConfigService;

/**
 * @author jun.bao
 * @since 2013年9月9日
 */
@Controller
public class RequestController {

	@Autowired
	private ConfigService configService;

	/**
	 * 客户端根据自身context name获取token池配置信息请求入口
	 * 
	 * @param contextName
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getconfig")
	public void getTokenConfig(@RequestParam("name") String contextName,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		contextName = StringUtils.trimToEmpty(contextName);
		if (StringUtils.isNotBlank(contextName)) {
			String config = configService.getTokenConfig(contextName);
			if (config != null && config.length() > 0) {
				response.setStatus(HttpServletResponse.SC_OK);
				response.getWriter().println(config);
				return;
			}
		}
		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	}
}
