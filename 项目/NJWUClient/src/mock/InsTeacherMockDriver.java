package mock;

import java.rmi.RemoteException;
import java.util.Iterator;

import vo.LessonAbstractVO;
import vo.LessonRecordVO;
import vo.LessonUniqueVO;
import vo.StudentVO;
import businesslogicservice.insteacherblservice.InsTeacherBlService;

public class InsTeacherMockDriver {
	InsTeacherBlService insTeacher;

	public InsTeacherMockDriver(InsTeacherBlService insTeacher) {
		this.insTeacher = insTeacher;
	}

	public void space() {
		System.out
				.println("---------------------------------------------------------------");
	}

	public void drive() throws RemoteException {
		System.out.println("下面对院系教务老师的功能进行测试：");
		System.out.println("1.查看院系教学信息——>查看学生信息——>选择年级2");
		Iterator<StudentVO> studentList = insTeacher.showStudent(2);
		while(studentList.hasNext()) {
			System.out.println(studentList.next().simpleInfo());
		}
		space();
		System.out.println("2.查看院系教务信息——>查看院系课程");
		Iterator<LessonUniqueVO> lessonList = insTeacher.showLesson();
		while (lessonList.hasNext()) {
			System.out.println(lessonList.next().normalInfo());
		}

		space();
		Iterator<LessonRecordVO> recordList;
		System.out.println("3.选择课程——>查看课程学生");
		recordList = insTeacher.showStudentofLesson(1);
		while (recordList.hasNext()) {
			LessonRecordVO vo = recordList.next();
			System.out.println(vo.getStu_name()+vo.getScore());
		}
		space();

		System.out.println("4.选择指定教学计划,系统显示已制定教学计划");
		Iterator<LessonAbstractVO> planList = insTeacher.getPlan();
		System.out.println(insTeacher.showPlan());
		space();

		System.out.println("5.院系教务老师修改课程250210的课程信息,重新显示");
		LessonAbstractVO po = planList.next();
		po = new LessonAbstractVO(po.getLes_Id_Ab(), po.getName(), 1, po.getMax_credit(), po.getCompulsory(), po.getType_Id(), po.getTerm_start(), po.getTerm_end(), po.getType(), po.getModule(), po.getModule_Id());
		insTeacher.modifyPlan(po);
		planList = insTeacher.getPlan();
		System.out.println(insTeacher.showPlan());
		space();

		System.out.println("6.院系教务老师删除课程250210,重新显示");
		insTeacher.deleteLesson(250210);
		planList = insTeacher.getPlan();
		System.out.println(insTeacher.showPlan());
		space();

		System.out.println("7.院系教务老师选择添加课程，并输入课程信息");
		LessonAbstractVO vo = new LessonAbstractVO(233333, "新增课程", 3, 3, 1, 5, 3, 3, null, null, 2);
		System.out.println("8.院系教务老师点击确定，系统添加该课程重新显示计划");
		insTeacher.addLesson(vo);
		planList = insTeacher.getPlan();
		System.out.println(insTeacher.showPlan());
		space();
		System.out.println("9.院系教务老师选择修改已发布课程");
		lessonList = insTeacher.showLesson();
		while(lessonList.hasNext()) {
			System.out.println(lessonList.next().normalInfo());
		}
		lessonList = insTeacher.showLesson();
		System.out.println("10.院系教务老师选择课程号,修改上课时间");
		LessonUniqueVO lPo = lessonList.next();
		lPo = new LessonUniqueVO(lPo.getLes_Id(), lPo.getLes_Id_Ab(), lPo.getLessonName(), lPo.getIns_Id(), 6, lPo.getStart(), lPo.getEnd(), lPo.getIntroduction(), lPo.getBooks(), lPo.getOutline(), lPo.getCredit(), lPo.getMax_stu_num(), lPo.getCur_stu_num(), lPo.getState(), lPo.getTea_Id(), lPo.getLocation(),lPo.getTerm());
		insTeacher.modifyLesson(lPo);
		System.out.println("院系教务老师修改完毕，点击确定，重新显示列表");
		lessonList = insTeacher.showLesson();
		while (lessonList.hasNext()) {
			System.out.println(lessonList.next().normalInfo());
		}
		lPo = new LessonUniqueVO(lPo.getLes_Id(), lPo.getLes_Id_Ab(), lPo.getLessonName(), lPo.getIns_Id(), 2, lPo.getStart(), lPo.getEnd(), lPo.getIntroduction(), lPo.getBooks(), lPo.getOutline(), lPo.getCredit(), lPo.getMax_stu_num(), lPo.getCur_stu_num(), lPo.getState(), lPo.getTea_Id(), lPo.getLocation(),lPo.getTerm());
		insTeacher.modifyLesson(lPo);
		space();
		System.out.println("11.院系教学老师修改密码");
		insTeacher.changePassword("25040".toCharArray(), "25040".toCharArray());
		space();
		System.out.println("院系教务老师功能测试完毕");

	}
}
