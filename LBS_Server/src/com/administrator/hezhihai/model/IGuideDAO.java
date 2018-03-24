package com.administrator.hezhihai.model;

import com.administrator.hezhihai.bean.Guider;

public interface IGuideDAO {
	// 现在完成的是登陆验证，那么登陆操作只有两种返回结果
	public boolean loginGuide(Guider guide) throws Exception ;
	public boolean registerGuide(Guider guide) throws Exception;	//返回值1代表注册成功，0代表账号已经存在
}
