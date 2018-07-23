package org.spring.springboot.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SessionController {
	//测试分布式session 使用redis缓存
	@RequestMapping("/uid")
	public String uid(HttpServletRequest request) {
		UUID uid = (UUID) request.getSession().getAttribute("uid");
		if (uid == null) {
			uid = UUID.randomUUID();
		}
		request.getSession().setAttribute("uid", uid);
		return request.getSession().getId();
	}
}
