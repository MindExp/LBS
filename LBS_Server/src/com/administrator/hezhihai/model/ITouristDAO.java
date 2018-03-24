package com.administrator.hezhihai.model ;
import com.administrator.hezhihai.bean.Diary;
import com.administrator.hezhihai.bean.GuideReqInfo;
import com.administrator.hezhihai.bean.Tourist;
public interface ITouristDAO {
	// 现在完成的是登陆验证，那么登陆操作只有两种返回结果
	public boolean loginTourist(Tourist tourist) throws Exception ;		
	public String searchGuiderGroupInfo(String keyWords) throws Exception;	//返回值为景点审核通过带团信息JSONArray格式
	public boolean registerTourist(Tourist tourist) throws Exception;	//返回值1代表注册成功，0代表账号已经存在
	public boolean addDiary(Diary diary) throws Exception;
	public boolean addGuideReqInfo(GuideReqInfo guideReqInfo) throws Exception;
	public boolean addAppointInfo(int info_id, int t_id) throws Exception;
	public String viewAppointInfo(int t_id) throws Exception;
	public String serviceAppeal(int t_id) throws Exception;
} 