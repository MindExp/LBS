package com.administrator.hezhihai.model;

import com.administrator.hezhihai.bean.Guider;

public interface IGuideDAO {
	// ������ɵ��ǵ�½��֤����ô��½����ֻ�����ַ��ؽ��
	public boolean loginGuide(Guider guide) throws Exception ;
	public boolean registerGuide(Guider guide) throws Exception;	//����ֵ1����ע��ɹ���0�����˺��Ѿ�����
}
