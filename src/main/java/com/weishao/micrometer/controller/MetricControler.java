package com.weishao.micrometer.controller;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.weishao.micrometer.service.TestService;

/**
 * Promethues性能集成接口地址：http://127.0.0.1:8009/actuator/prometheus
 * 
 * @author tang
 *
 */
@RestController
public class MetricControler {

	@Autowired
	private TestService testService;

	@PostConstruct
	public void init() {
		testService.doTest();
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public void getMeg(HttpServletResponse response) throws IOException {
		testService.doTest();
	}

}
