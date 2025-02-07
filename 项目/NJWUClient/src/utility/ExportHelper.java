package utility;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

import vo.StudentVO;
import vo.TeacherVO;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
/**
 * 协助导出
 * @author luck
 *
 */
public class ExportHelper {
	public static void exportTeacher(OutputStream os,
			ArrayList<TeacherVO> list) throws IOException,
			RowsExceededException, WriteException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("工作簿1", 0);
		Label cell;
		String[] head = { "工号", "院系号", "院系名", "姓名", "性别", "类别", "密码" };
		for (int j = 0; j < head.length; j++) {
			cell = new Label(j, 0, head[j]);
			ws.addCell(cell);
		}
		for (int i = 1; i < list.size(); i++) {
			TeacherVO teacher = list.get(i);
			String[] content = { teacher.getTea_Id() + "",
					teacher.getIns_Id() + "", teacher.getInstitution(),
					teacher.getName(), teacher.getGender(),
					teacher.getType() + "",
					String.valueOf(teacher.getPassword()) };
			for (int k = 0 ; k < content.length; k++){
				cell = new Label(k,i,content[k]);
				ws.addCell(cell);
			}
		}
		wwb.write();
		wwb.close();
		os.close();
	}
	public static void exportStudent(OutputStream os,
			ArrayList<StudentVO> list) throws IOException,
			RowsExceededException, WriteException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("工作簿1", 0);
		Label cell;
		String[] head = { "学号", "院系号", "院系名", "姓名", "性别", "年级", "密码" };
		for (int j = 0; j < head.length; j++) {
			cell = new Label(j, 0, head[j]);
			ws.addCell(cell);
		}
		for (int i = 1; i < list.size(); i++) {
			StudentVO student = list.get(i);
			String[] content = { student.getStu_Id() + "",
					student.getIns_Id() + "", student.getInstitute(),
					student.getName(), student.getGender(),
					student.getGrade() + "",
					String.valueOf(student.getPassword()) };
			for (int k = 0 ; k < content.length; k++){
				cell = new Label(k,i,content[k]);
				ws.addCell(cell);
			}
		}
		wwb.write();
		wwb.close();
		os.close();
	}
	public static void exportRecord(OutputStream os,
			ArrayList<StudentVO> list) throws IOException,
			RowsExceededException, WriteException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet ws = wwb.createSheet("工作簿1", 0);
		Label cell;
		String[] head = { "学号", "院系号", "院系名", "姓名", "性别", "年级" };
		for (int j = 0; j < head.length; j++) {
			cell = new Label(j, 0, head[j]);
			ws.addCell(cell);
		}
		for (int i = 1; i < list.size(); i++) {
			StudentVO student = list.get(i);
			String[] content = { student.getStu_Id() + "",
					student.getIns_Id() + "", student.getInstitute(),
					student.getName(), student.getGender(),
					student.getGrade() + ""};
			for (int k = 0 ; k < content.length; k++){
				cell = new Label(k,i,content[k]);
				ws.addCell(cell);
			}
		}
		wwb.write();
		wwb.close();
		os.close();
	}
}
