package presentation.studentui;

import java.util.ArrayList;
import java.util.Iterator;

import presentation.uielements.tablehead.PlanTableHead;
import vo.LessonAbstractVO;
import vo.PlanVO;
/**
 * 查看教学计划
 * @author luck
 *
 */
public class Stu_ShowPlan extends Stu_ShowPanel {
	PlanVO plan;
	
	@Override
	public void fillRowData() {
		Iterator<LessonAbstractVO> iter = plan.getLessons();
		ArrayList<LessonAbstractVO> list = new ArrayList<>();
		while (iter.hasNext()) {
			LessonAbstractVO lessonAbstractVO = iter.next();
			list.add(lessonAbstractVO);
		}
		rowData = new String[list.size()][18];// 每行每列的数据设置在这里！！！！
		int index = 0;
		for (LessonAbstractVO item : list) {
			rowData[index][0] = item.getModule();
			rowData[index][1] = item.getType();
			rowData[index][2] = item.getCompulsoryString();
			rowData[index][3] = item.getLes_Id_Ab() + "";
			rowData[index][4] = item.getName();
			rowData[index][5] = item.getCredit();
			String termString = item.getTerm();
			String[] terms = termString.split(";");
			for (String eachTerm : terms) {
				int term = Integer.parseInt(eachTerm);
				if (term > 2 && term < 5)
					term++;
				if (term >= 5 && term < 7)
					term = term + 2;
				if (term >= 7 && term < 9)
					term = term + 3;
				if (term >= 9)
					term = 3 * (term - 8);
				rowData[index][5 + term] = "√";
			}
			index++;
		}
	}

	public Stu_ShowPlan(PlanVO plan) {
		this.plan = plan;
		tableHead = new String[] { "", "", "", "", "", "", "", "", "", "", "",
				"", "", "", "", "", "" };
		fillRowData();
		initialTable();
	}

	@Override
	public void setThead() {
		PlanTableHead tHead = new PlanTableHead();
		tHead.setBounds(6, 6, 839, 96);
		add(tHead);
	}

	@Override
	public void setTableWidth() {
		tableScrollPane.setBounds(12, 100, 830, 390);
		for (int k = 0; k < 17; k++) {
			int tableWidth;
			if (k == 0) {
				tableWidth = 93;
			} else if (k == 1) {
				tableWidth = 94;
			} else if (k == 2) {
				tableWidth = 35;
			} else if (k == 3) {
				tableWidth = 75;
			} else if (k == 4) {
				tableWidth = 175;
			} else if (k == 5) {
				tableWidth = 35;
			} else {
				tableWidth = 27;
			}
			table.getColumnModel().getColumn(k).setPreferredWidth(tableWidth);
			table.getColumnModel().getColumn(k).setMaxWidth(tableWidth);
			table.getColumnModel().getColumn(k).setMinWidth(tableWidth);
		}
	}
}
