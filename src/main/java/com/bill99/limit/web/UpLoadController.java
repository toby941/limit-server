package com.bill99.limit.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 客户端上传快照和告警信息入口
 * 
 * @author jun.bao
 * @since 2013年9月6日
 */
@Controller
public class UpLoadController {

	@RequestMapping("/upload/alarm")
	public void receiveAlarm(@RequestParam("c") String contextName,
			@RequestParam("p") String poolName, @RequestParam("t") String time,
			@RequestParam("i") String info, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.getWriter().println("alarm upload done");
	}

	@RequestMapping("/upload/snapshot")
	public void index(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.getWriter().println("index upload done");
	}
}
