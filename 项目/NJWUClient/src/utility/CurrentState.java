package utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import po.InstitutePO;
import po.SystemState;
/**
 * 系统状态
 * @author luck
 *
 */
public class CurrentState {
	public static int select;
	public static int bySelect;
	public static int plan;

	
	public static void setState(SystemState state) {
		select = state.getSelect();
		bySelect = state.getByselect();
		plan = state.getPlan();
	}

	public static String[] institutes = new String[100];

	public static void getInstitutes(ArrayList<InstitutePO> institutes) {
		for (String eachString : CurrentState.institutes) {
			eachString = null;
		}
		for (InstitutePO institute : institutes) {
			CurrentState.institutes[institute.getIns_id()] = institute
					.getIns_name();
		}
	}

	public static String[] getInstitute() {
		int size = 0;
		for (String eachString : CurrentState.institutes) {
			if (eachString != null) {
				size++;
			}
		}
		String[] institutes = new String[size + 1];
		int i = 1;
		institutes[0] = "请选择院系";
		for (String eachString : CurrentState.institutes) {
			if (eachString != null) {
				institutes[i++] = eachString;
			}
		}
		return institutes;
	}

	public static int getInsId(String ins_name) {
		for (int i = 0; i < institutes.length; i++) {
			if (institutes[i] != null && institutes[i].equals(ins_name))
				return i;
		}
		return -1;
	}

	public static int getTypeId(String typeString) {
		if (typeString.equals(Constant.UserTypeString.TEACHER_STRING)) {
			return Constant.UserType.TEACHER;
		} else if (typeString.equals(Constant.UserTypeString.INS_TEACHER_STRING)) {
			return Constant.UserType.INS_TEACHER;
		} else if (typeString.equals(Constant.UserTypeString.SCH_TEACHER_STRING)) {
			return Constant.UserType.SCH_TEACHER;
		} else {
			return Constant.UserType.STUDENT;
		}
	}

	public static String getTypeString(int type) {
		switch (type) {
		case Constant.UserType.INS_TEACHER:
			return Constant.UserTypeString.INS_TEACHER_STRING;
		case Constant.UserType.SCH_TEACHER:
			return Constant.UserTypeString.SCH_TEACHER_STRING;
		case Constant.UserType.TEACHER:
			return Constant.UserTypeString.TEACHER_STRING;
		case Constant.UserType.STUDENT:
			return Constant.UserTypeString.STUDENT_STRING;
		default:
			return "错误的用户类型";

		}
	}
	
	public static void clog(Object ob){
		System.out.println(ob);
	}

}
