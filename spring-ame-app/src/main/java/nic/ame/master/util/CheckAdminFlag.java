package nic.ame.master.util;

import nic.ame.constant.CommonConstant;

public class CheckAdminFlag {

	
	public static int checkAdminFlagStatus(String RoleName) {
		
		if(RoleName.equals(CommonConstant.ROLE_NAME_CAPF_AD)||
				RoleName.equals(CommonConstant.ROLE_NAME_FORCE_AD)||
				RoleName.equals(CommonConstant.ROLE_NAME_UNIT_AD)||
				RoleName.equals(CommonConstant.ROLE_NAME_BDA_AD)) {
			return CommonConstant.ROLE_ADMIN_FLAG_YES;
		}
		return CommonConstant.ROLE_ADMIN_FLAG_NO;
	}
	
}
