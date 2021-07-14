package com.pj.sso;

import cn.dev33.satoken.exception.NotLoginException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(SsoController.class);

	// 测试：进行登录
	@RequestMapping("doLogin")
	public AjaxJson doLogin(@RequestParam(defaultValue = "10001") String id) {
		logger.info( "SsoController.doLogin 入参为" + "id = [" + id + "]");
		StpUtil.login(id);
		return AjaxJson.getSuccess("登录成功: " + id);
	}

	// 测试：是否登录
	@RequestMapping("isLogin")
	public AjaxJson isLogin() {
        String userId ;
        try {
            userId = (String) StpUtil.getLoginId();
        }catch (NotLoginException nle){
            return AjaxJson.getSuccess("该用户目前未登录 -- "+nle.getMessage());
        }
		logger.info( "SsoController.isLogin 检查id{}是否登录",userId);
		boolean isLogin = StpUtil.isLogin();
		return AjaxJson.getSuccess("是否登录: " + isLogin);
	}

	// 测试: 注销登录
	@RequestMapping("doLogout")
	public AjaxJson logout(){
        String userId ;
        try {
            userId = (String) StpUtil.getLoginId();
        }catch (NotLoginException nle){
            return AjaxJson.getSuccess("该用户目前未登录 -- "+nle.getMessage());
        }
		logger.info( "SsoController.logout 开始对id[{}]进行下线工作",userId);
		//若登录,进行下线操作
		if(StpUtil.isLogin()){
		    logger.info("用户{}已登录,现在开始下线处理",userId);
			StpUtil.logout();
		}
		boolean isLogout = !StpUtil.isLogin();
		logger.info("用户{}是否下线成功{}",userId,isLogout);
		return AjaxJson.getSuccess("是否下线: " + isLogout);
	}
}
