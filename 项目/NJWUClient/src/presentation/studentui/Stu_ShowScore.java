package presentation.studentui;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JLabel;

import vo.LessonRecordVO;
import vo.ScoreVO;
/**
 * 
 * @author luck
 *
 */
public class Stu_ShowScore extends Stu_ShowPanel{
	private ScoreVO scores;
	int term=1;
	public Stu_ShowScore(ScoreVO scoreVO){
		this.scores = scoreVO;
		setThead();
		tableHead = new String[]{"","","","","",""};
		JLabel label_1 = new JLabel("学分合计："+scores.getTotalCredit()+" 学分绩："+scores.GPA());//只要替换？？？部分就好
		label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
		label_1.setBounds(456, 464, 296, 25);
		add(label_1);
		fillRowData();
		initialTable();
	}
	public void refresh(int term){
		this.term = term;
		fillRowData();
		table.refresh(rowData, tableHead);
	}
	@Override
	public void setThead() {
		JLabel tHead = new JLabel(
				"<HTML>" +
				"<table>" +
					"<th"+thCss+">课程号</th>" +
					"<th"+thCss+">课程名</th>" +
					"<th"+thCss+">教 师</th>" +
					"<th"+thCss+">类型</th>" +
					"<th"+thCss+">学分</th>" +
					"<th"+thCss+">成 绩</th>" +
				"</table>" +
				"</HTML>");
		tHead.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		tHead.setBounds(6, 44, 827, 96);
		add(tHead);
	}
	@Override
	public void fillRowData() {
		int index=0;
		Iterator<LessonRecordVO> iterator = scores.getScores(term);
		ArrayList<LessonRecordVO> recordList = new ArrayList<>();
		while (iterator.hasNext()){
			LessonRecordVO recordVO = iterator.next();
			recordList.add(recordVO);
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
	public void setTableWidth() {
		tableScrollPane.setBounds(6, 121, 720, 331);
	}
	
}