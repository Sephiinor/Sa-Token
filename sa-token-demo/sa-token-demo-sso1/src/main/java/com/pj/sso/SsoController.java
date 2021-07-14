package com.pj.sso;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pj.util.AjaxJson;

import cn.dev33.satoken.stp.StpUtil;

/**
 * 测试: 同域单点登录
 * @author kong
 */
@RestController
@RequestMapping("/sso/")
public class SsoController {

	// 测试：进行登录
	@RequestMapping("doLogin")
	public AjaxJson doLogin(@RequestParam(defaultValue = "10001") String id) {
		System.out.println("---------------- 进行登录 ");
		StpUtil.login(id);
		return AjaxJson.getSuccess("登录成功: " + id);
	}

	// 测试：是否登录
	@RequestMapping("isLogin")
	public AjaxJson isLogin() {
		System.out.println("---------------- 是否登录 ");
		boolean isLogin = StpUtil.isLogin();
		return AjaxJson.getSuccess("是否登录: " + isLogin);
	}

	// 测试: 注销登录
	@RequestMapping("doLogout")
	public AjaxJson logout(){
		System.out.println("---------------- 准备下线 ");
		boolean isLogin = StpUtil.isLogin();
		if(isLogin){
			StpUtil.logout();
		}
		boolean isLogout = !StpUtil.isLogin();
		return AjaxJson.getSuccess("是否下线: " + isLogout);
	}
}
