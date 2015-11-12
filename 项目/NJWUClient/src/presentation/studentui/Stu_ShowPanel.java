package presentation.studentui;

import presentation.uielements.UserShowPanel;
import businesslogicservice.studentblservice.StudentBlService;
/**
 * 查看信息界面的父类
 * @author luck
 *
 */
public abstract class Stu_ShowPanel extends UserShowPanel {
	protected StudentBlService student;
	public Stu_ShowPanel(StudentBlService student) {
		super();
		this.student = student;
	}
	public Stu_ShowPanel(){
		super();
	}
}
