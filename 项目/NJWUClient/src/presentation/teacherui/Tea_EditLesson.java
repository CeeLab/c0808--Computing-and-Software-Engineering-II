package presentation.teacherui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import po.LessonUniquePO;
import presentation.mainui.MainFrameUI;
import presentation.uielements.MyButton;
import vo.LessonUniqueVO;
import businesslogicservice.teacherblservice.TeacherBlService;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
/**
 * 任课老师编辑课程界面
 * @author luck
 *
 */
public class Tea_EditLesson extends JPanel {
	private TeacherBlService teacher;
	private LessonUniqueVO lesson;
	private JTextField tfId;
	private JTextField tfCredit;
	private JTextField tfTime;
	private JTextField tfNum;
	private JTextField tfLessonName;
	private JTextField tfLocation;
	private JTextField tfBooks;
	private JTextArea taIntroduction;
	private JTextArea taOutline;
	private JTextField[] fields = new JTextField[7];
	private Tea_ShowLesson former;
	private boolean editable = true;
	
	public Tea_EditLesson(Tea_ShowLesson tea_ShowLesson, TeacherBlService teacher,LessonUniqueVO lesson,boolean editable) {
		this.editable = editable;
		this.former = tea_ShowLesson;
		this.teacher = teacher;
		this.lesson = lesson; 
		setBounds(201, 128, 851, 495);
		setOpaque(false);
		setLayout(null);
		
		JLabel lbId = new JLabel("课程号：");
		lbId.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbId.setBounds(108, 20, 49, 31);
		add(lbId);
		
		JLabel lbLessonName = new JLabel("课程名：");
		lbLessonName.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbLessonName.setBounds(342, 20, 49, 31);
		add(lbLessonName);
		
		JLabel lbTime = new JLabel("上课时间：");
		lbTime.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbTime.setBounds(95, 103, 68, 31);
		add(lbTime);
		
		JLabel lbLocation = new JLabel("上课地点：");
		lbLocation.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbLocation.setBounds(95, 138, 68, 31);
		add(lbLocation);
		
		JLabel lbCredit = new JLabel("学分：");
		lbCredit.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbCredit.setBounds(118, 59, 49, 31);
		add(lbCredit);
		
		JLabel lbNum = new JLabel("学生人数：");
		lbNum.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbNum.setBounds(329, 59, 68, 31);
		add(lbNum);
		
		tfId = new JTextField();
		tfId.setEditable(false);
		tfId.setBounds(169, 25, 97, 21);
		tfId.setText(lesson.getLessonId());
		add(tfId);
		
		
		
		tfCredit = new JTextField();
		tfCredit.setEditable(false);
		tfCredit.setBounds(169, 66, 57, 21);
		tfCredit.setText(lesson.getCreditString());
		add(tfCredit);
		
		tfTime = new JTextField();
		tfTime.setEditable(false);
		tfTime.setBounds(169, 107, 144, 21);
		tfTime.setText(lesson.getTime());
		add(tfTime);
		
		tfNum = new JTextField();
		tfNum.setEditable(false);
		tfNum.setBounds(392, 66, 87, 21);
		tfNum.setText(lesson.getStu_num());
		add(tfNum);
		
		tfLessonName = new JTextField();
		tfLessonName.setBounds(392, 24, 177, 21);
		tfLessonName.setText(lesson.getLessonName());
		tfLessonName.setEditable(editable);
		add(tfLessonName);
		
		tfLocation = new JTextField();
		tfLocation.setEditable(false);
		tfLocation.setBounds(169, 143, 144, 21);
		tfLocation.setText(lesson.getLocation());
		add(tfLocation);
		
		JLabel lbIntroduction = new JLabel("课程介绍：");
		lbIntroduction.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbIntroduction.setBounds(95, 174, 68, 31);
		add(lbIntroduction);
		JLabel lbOutline = new JLabel("课程大纲：");
		lbOutline.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbOutline.setBounds(95, 279, 68, 31);
		add(lbOutline);
		
		JLabel lbBooks = new JLabel("课程教材：");
		lbBooks.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		lbBooks.setBounds(97, 396, 68, 31);
		add(lbBooks);
		
		tfBooks = new JTextField();
		tfBooks.setBounds(169, 396, 400, 31);
		tfBooks.setText(lesson.getBooks());
		tfBooks.setEditable(editable);
		add(tfBooks);
		if (editable){
			MyButton btSubmit = new MyButton("提交");
			btSubmit.setBounds(169, 451, 80, 31);
			add(btSubmit);
			btSubmit.addActionListener(new SubmitListener());
			
			
			MyButton btClear = new MyButton("重置");
			btClear.addActionListener(new ClearListener());
			btClear.setBounds(299, 451, 80, 31);
			add(btClear);
					
		}
		MyButton btReturn = new MyButton("返回");
		btReturn.setBounds(419, 447, 80, 31);
		btReturn.addActionListener(new ReturnListener());
		add(btReturn);
		
		fields[0]=tfBooks;
		fields[1]=tfCredit;
		fields[2]=tfId;
		fields[3]=tfLessonName;
		fields[4]=tfNum;
		fields[5]=tfTime;
		fields[6]=tfLocation;
		
		
		taIntroduction = new JTextArea();
		taIntroduction.setLineWrap(true);
		taIntroduction.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		taIntroduction.setBounds(172, 178, 397, 96);
		taIntroduction.setText(lesson.getIntroduction());
		taIntroduction.setEditable(editable);
		add(taIntroduction);
		
		taOutline = new JTextArea();
		taOutline.setLineWrap(true);
		taOutline.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 13));
		taOutline.setBounds(172, 283, 397, 96);
		taOutline.setText(lesson.getOutline());
		taOutline.setEditable(editable);
		add(taOutline);
	}
	/**
	 * 重置
	 * @author luck
	 *
	 */
	class ClearListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			for (JTextField field :fields){
				if (field.isEditable()){
					field.setText("");
				}
			}
			taIntroduction.setText("");
			taOutline.setText("");
		}
		
	}
	/**
	 * 返回
	 * @author luck
	 *
	 */
	class ReturnListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			Tea_EditLesson.this.setVisible(false);
			former.setVisible(true);
		}
		
	}
	/**
	 * 提交
	 * @author luck
	 *
	 */
	class SubmitListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			new Thread(){
				public void run(){
					MainFrameUI.loadingPanel.setMessage("正在处理请求...");
					MainFrameUI.showWating();
					try {
						if (!teacher.editLesInfo(new LessonUniqueVO(lesson.getLes_Id(),lesson.getLes_Id_Ab(), tfLessonName.getText(), lesson.getIns_Id(), lesson.getDay(), lesson.getStart(), lesson.getEnd(), taIntroduction .getText(), tfBooks.getText(), taOutline.getText(), lesson.getCredit(), lesson.getMax_stu_num(),lesson.getCur_stu_num(),lesson.getState(),lesson.getTea_Id(),lesson.getLocation(),lesson.getTerm()))){
							JOptionPane.showMessageDialog(null, "更新失败");
						} else {
							JOptionPane.showMessageDialog(null, "更新成功");
							Tea_EditLesson.this.setVisible(false);	
							former.refresh();
							former.setVisible(true);
						}
						
					} catch (RemoteException e1) {
						MainFrameUI.showError();
						e1.printStackTrace();
					}			
					MainFrameUI.hideWating();
				}
			}.start();
		
		}
	}
	public static void main(String[] args){
//		Tea_EditLesson editor = new Tea_EditLesson(null, null, new LessonUniqueVO(1, "..", 1, 1, 1, 1, "", "", "", 1, 1));
		JFrame frame = new JFrame();
//		frame.getContentPane().add(editor);
		frame.setVisible(true);
	}
}
