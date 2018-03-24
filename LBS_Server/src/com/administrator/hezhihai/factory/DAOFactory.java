package com.administrator.hezhihai.factory ;

import com.administrator.hezhihai.model.*;
import com.administrator.hezhihai.model.proxy.GuideDAOProxy;
import com.administrator.hezhihai.model.proxy.TouristDAOProxy;

public class DAOFactory {
	public static ITouristDAO getITouristDAOInstance(){
		return new TouristDAOProxy() ;
	}
	public static IGuideDAO getIGuideDAOInstance(){
		return new GuideDAOProxy() ;
	}
}