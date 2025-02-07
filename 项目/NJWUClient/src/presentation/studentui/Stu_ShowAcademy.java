package presentation.studentui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import vo.LessonRecordVO;
import vo.ScoreVO;

/**
 * 
 * @author NorviNS
 *  学业审查 查看
 *
 */
public class Stu_ShowAcademy extends Stu_ShowPanel{
	
	private ScoreVO academy;
	public Stu_ShowAcademy(ScoreVO academy){
		this.academy = academy;
		tableHead = new String[]{"","","","","","",""};
		
		JLabel label_1 = new JLabel("<HTML>已获总学分："+academy.getTotalCredit()+"  已获通识类学分："+ academy.getGeneralCredit()+" 审核时间:"+"???"+ "<br>"+
									"必修课修读情况如下：</HTML>");//只要替换？？？部分就好
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
		label_1.setBounds(6, 56, 630, 36);
		add(label_1);
		fillRowData();
		initialTable();
	}
	
	@Override
	public void fillRowData() {
		int index=0;
		Iterator<LessonRecordVO> iterator = academy.getAcademy();
		ArrayList<LessonRecordVO> recordList = new ArrayList<>();
		
		while (iterator.hasNext()){
			recordList.add(iterator.next());
		}
		rowData = new String[recordList.size()][6];
		for (LessonRecordVO vo:recordList){
			rowData[index][0]=vo.getLes_id()+"";
			rowData[index][1]=vo.getLes_name();
			rowData[index][2]=vo.getTea_name();
			rowData[index][3]=vo.getType_name();
			rowData[index][4]=vo.getCredit()+"";
			rowData[index][5]=vo.getScore()+"";
			index++;		
		}
	
	}
	@Override
	public void setThead() {
		tHead = new JLabel(
				"<HTML>" +
				"<table>" +
					"<th"+thCss+">课程号</th>" +
					"<th"+thCss+">课程名</th>" +
					"<th"+thCss+">教 师</th>" +
					"<th"+thCss+">类型</th>" +
					"<th"+thCss+">学分</th>" +
					"<th"+thCss+">成 绩</th>" +
					"<th"+thCss+">备 注</th>" +
				"</table>" +
				"</HTML>");
		tHead.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		tHead.setBounds(5, 82, 759, 96);
		add(tHead);		
	}

	@Override
	public void setTableWidth() {
		tableScrollPane.setBounds(5, 159, 773, 331);
	}
}
