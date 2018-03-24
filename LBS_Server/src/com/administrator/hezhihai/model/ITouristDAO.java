package com.administrator.hezhihai.model ;
import com.administrator.hezhihai.bean.Diary;
import com.administrator.hezhihai.bean.GuideReqInfo;
import com.administrator.hezhihai.bean.Tourist;
public interface ITouristDAO {
	// ������ɵ��ǵ�½��֤����ô��½����ֻ�����ַ��ؽ��
	public boolean loginTourist(Tourist tourist) throws Exception ;		
	public String searchGuiderGroupInfo(String keyWords) throws Exception;	//����ֵΪ�������ͨ��������ϢJSONArray��ʽ
	public boolean registerTourist(Tourist tourist) throws Exception;	//����ֵ1����ע��ɹ���0�����˺��Ѿ�����
	public boolean addDiary(Diary diary) throws Exception;
	public boolean addGuideReqInfo(GuideReqInfo guideReqInfo) throws Exception;
	public boolean addAppointInfo(int info_id, int t_id) throws Exception;
	public String viewAppointInfo(int t_id) throws Exception;
	public String serviceAppeal(int t_id) throws Exception;
} 