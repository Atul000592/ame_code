package nic.ame.master.util;

public class RoleCodeNameUtil {

	
	
	public static String getRoleNameByRoleCode(String roleCode) {
		if(roleCode.equals("capfad")) {
			return "CAPF Admin";
		}
		if(roleCode.equals("fad")) {
			return "FORCE Admin";
		}
		if(roleCode.equals("bda")) {
			return "Board Detaling Authority";
		}
		if(roleCode.equals("dh")) {
			return "Dealing Hand";
		}
		if(roleCode.equals("uad")) {
			return "Unit Admin";
		}
		if(roleCode.equals("bm")) { 
			return "Board Member";
		}
		if(roleCode.equals("ama")) {
			return "AMA";
		}
		if(roleCode.equals("dc")) {
			return "Doctor";
		}
		
		if(roleCode.equals("dhm")) {
			return "Dealing Hand Medical";
		}
		if(roleCode.equals("cho")) {
			return "Controlling Officer";
		}
		return null;
	}
}
