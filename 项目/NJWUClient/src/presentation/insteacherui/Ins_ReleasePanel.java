package presentation.insteacherui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.rmi.RemoteException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import presentation.mainui.MainFrameUI;
import presentation.uielements.MyButton;
import presentation.uielements.MyComboBox;
import utility.CurrentState;
import vo.LessonAbstractVO;
import vo.LessonUniqueVO;
import vo.TeacherVO;
import businesslogicservice.insteacherblservice.InsTeacherBlService;
/**
 * 发布课程界面
 * @author 小c
 *
 */
public class Ins_ReleasePanel extends JPanel {
	
	private JComboBox<TeacherVO> jcbTeacher;
	private MyComboBox<TeacherVO> teacherBox;
	private JComboBox<Integer> jcbCredit;
	private MyComboBox<Integer> creditBox;
	private JTextField Location;
	private JTextField maxStudent;
	private MyButton jbtRelease;
	private MyButton jbtCancel;
	private JComboBox<LessonAbstractVO> jcbLessonAb;
	private MyComboBox<LessonAbstractVO> lessonBox;
	private JComboBox<String> jcbInstitute;
	private MyComboBox<String> insBox;
	ArrayList<LessonAbstractVO> lessonList;
	String[] institutes;

	InsTeacherBlService insTeacher;
	private ArrayList<TeacherVO> TeacherOfInsList;
	private JComboBox<String> TimeDay;
	private MyComboBox<String> dayBox;
	
	private JComboBox<Integer> TimeLesStart;
	private MyComboBox<Integer> startBox;
	private JComboBox<Integer> TimeLesEnd;
	private MyComboBox<Integer> endBox;
	private JTextField jfLessonName;
	private JLabel label_5;
	private JLabel label_6;

	private int default_max_stu = 200;
	private String[] dayList;
	private Integer[] lesList;

	public Ins_ReleasePanel(InsTeacherBlService insTeacher) {

		this.insTeacher = insTeacher;
		setLayout(null);
		setInputComponents();
		new Thread(){
			public void run(){
				try {
					pullValuse();
				} catch (RemoteException e) {
					MainFrameUI.showError();
					e.printStackTrace();
				}

				setShow();
				addMyBox();

				setValues();
				addShow();
				addListeners();
			}
		}.start();
	
	}

	/**
	 * 将所有组件添加到面板上
	 */
	private void addShow() {
		addLabel();

		add(jbtRelease);
		add(jbtCancel);


		add(Location);
		add(maxStudent);

		add(jfLessonName);
		add(dayBox);
		add(startBox);
		add(endBox);
		add(insBox);
		add(teacherBox);
		add(creditBox);
		add(lessonBox);
		int n = getComponentCount();
		for (int i = 0; i < n; ++i) {
			this.getComponent(i).setFont(
					new Font("Microsoft YaHei", Font.PLAIN, 12));
		}
	}

	/**
	 * 从服务器获取应有的值
	 * 
	 * @throws RemoteException
	 */
	private void pullValuse() throws RemoteException {
		MainFrameUI.loadingPanel.setMessage("正在获取教学计划...");
		MainFrameUI.showWating();
		Iterator<LessonAbstractVO> iter = insTeacher.getPlan();
		lessonList = new ArrayList<LessonAbstractVO>();
		while (iter.hasNext()) {
			lessonList.add(iter.next());
		}

		institutes = CurrentState.getInstitute();
		MainFrameUI.hideWating();
	}

	/**
	 * 将服务器获取的值赋值给组件
	 */
	private void setValues() {

		jcbLessonAb.setModel(new DefaultComboBoxModel(lessonList.toArray()));

		jcbInstitute.setModel(new DefaultComboBoxModel<String>(institutes));

		jcbLessonAb.setSelectedIndex(-1);
		// refreshLesson((LessonAbstractVO) jcbLessonAb.getSelectedItem());

	}

	private void addListeners() {
		jbtCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		jbtRelease.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread(){
					public void run(){
						MainFrameUI.loadingPanel.setMessage("正在发布课程...");
						release();
						MainFrameUI.hideWating();
					}
				}.start();
				
			}
		});

		jcbLessonAb.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					refreshLesson((LessonAbstractVO) e.getItem());
				}
			}
		});

		jcbInstitute.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					refreshInstitute((String) e.getItem());
				}
			}
		});

		TimeLesStart.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					int start = (int) e.getItem();
					Integer[] lesList = new Integer[11 - start + 1];
					for (int i = 0; i < lesList.length; ++i) {
						lesList[i] = start + i;
					}

					int oldEnd = (int) TimeLesEnd.getSelectedItem();
					TimeLesEnd.setModel(new DefaultComboBoxModel(lesList));

					if (oldEnd >= start) {
						TimeLesEnd.setSelectedIndex(oldEnd - start);
						endBox.setCell((Integer)TimeLesEnd.getSelectedItem());
					} else {
						TimeLesEnd.setSelectedIndex(0);
						endBox.setCell((Integer)TimeLesEnd.getSelectedItem());
					}
				}
			}
		});

	}

	private void refreshLesson(LessonAbstractVO vo) {

		if (vo == null) {
			return;
		}

		int minCredit = vo.getMin_credit();
		int maxCredit = vo.getMax_credit();
		int size = maxCredit - minCredit + 1;
		Integer[] credits = new Integer[size];

		for (int i = 0; i < size; ++i) {
			credits[i] = minCredit + i;
		}
		jcbCredit.setModel(new DefaultComboBoxModel<Integer>(credits));
		creditBox.setCell(credits[0]);
		jfLessonName.setText(vo.getName());
		maxStudent.setText(default_max_stu + "");
	}

	private void refreshInstitute(String selectedIns) {
		int ins_id = CurrentState.getInsId(selectedIns);
		if (ins_id != -1) {
			try {
				TeacherOfInsList = insTeacher.getTeacherOfIns(ins_id);
				jcbTeacher.setModel(new DefaultComboBoxModel(TeacherOfInsList
						.toArray()));
				teacherBox.setCell(TeacherOfInsList.get(0));
			} catch (RemoteException e) {
				MainFrameUI.showError();
				e.printStackTrace();
			}
		} else {
			TeacherOfInsList = new ArrayList<TeacherVO>();
			jcbTeacher.setModel(new DefaultComboBoxModel(TeacherOfInsList
					.toArray()));
		}
	}

	private void release() {
		if (jcbTeacher.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(null, "老师不能为空");
			return;
		} else if (maxStudent.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "最大学生人数不能为空");
			return;
		} else if (Location.getText().equals("")) {
			JOptionPane.showMessageDialog(null, "上课地点不能为空");
			return;
		}
		int lessonUniqueId = 0;
		LessonAbstractVO lesAb = (LessonAbstractVO) jcbLessonAb
				.getSelectedItem();
		int lessonAbId = lesAb.getLes_Id_Ab();
		String lessonName = jfLessonName.getText();
		String introduction = "";
		int Ins_Id = 0;
		int day = TimeDay.getSelectedIndex() + 1;
		int start = (int) TimeLesStart.getSelectedItem();
		int end = (int) TimeLesEnd.getSelectedItem();
		String books = "";
		String outline = "";
		int credit = (int) jcbCredit.getSelectedItem();
		int max_stu_num = Integer.parseInt(maxStudent.getText());
		int cur_stu_num = 0;
		String location = Location.getText();
		int state = 0;
		int tea_id = ((TeacherVO) jcbTeacher.getSelectedItem()).getTea_Id();
		int term = lesAb.getTerm_start();
		LessonUniqueVO vo = new LessonUniqueVO(lessonUniqueId,
				lesAb.getLes_Id_Ab(), lessonName, Ins_Id, day, start, end,
				introduction, books, outline, credit, max_stu_num, cur_stu_num,
				state, tea_id, location,term);
		MainFrameUI.showWating();
		try {
			if (insTeacher.publishLesson(vo)) {
				allocateStudents();
			} else {
				JOptionPane.showMessageDialog(null, "发布失败");
			}
		} catch (RemoteException e) {
			MainFrameUI.showError();
			e.printStackTrace();
		}
	}
	public void allocateStudents(){
		MainFrameUI.loadingPanel.setMessage("正在分配学生");
		new Thread(){
			public void run(){
				try {
					LessonUniqueVO vo = null;
					Iterator<LessonUniqueVO> iterator= insTeacher.showLesson();
					while (iterator.hasNext()){
						vo = iterator.next();
					}
					int grade = (vo.getTerm()+1)/2;
					System.out.println(grade);
					insTeacher.addStudent(vo.getLes_Id(), grade);
					MainFrameUI.hideWating();
					JOptionPane.showMessageDialog(null, "发布成功");

				} catch (RemoteException e) {
					MainFrameUI.showError();
					e.printStackTrace();
				}		
			}
		}.start();
	}
	private void cancel() {
		jcbLessonAb.setSelectedIndex(-1);
		jcbInstitute.setSelectedIndex(0);
		refreshLesson((LessonAbstractVO) jcbLessonAb.getSelectedItem());
		maxStudent.setText("200");
		TimeDay.setSelectedIndex(0);
		dayBox.setCell((String)TimeDay.getSelectedItem());
		TimeLesStart.setSelectedIndex(0);
		startBox.setCell(1);
		TimeLesEnd.setSelectedIndex(0);
		endBox.setCell(1);
		Location.setText("");

	}

	/**
	 * 将所有组件赋值
	 */
	private void setShow() {
		setInputComponents();
		setButton();
	}

	private void setButton() {
		jbtRelease = new MyButton("发布");
		jbtRelease.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		jbtRelease.setBounds(162, 444, 90, 32);

		jbtCancel = new MyButton("取消");
		jbtCancel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		jbtCancel.setBounds(297, 444, 90, 32);

	}

	private void addLabel() {
		JLabel lblxxx = new JLabel("选择课程：");
		lblxxx.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		lblxxx.setBounds(162, 50, 90, 37);
		add(lblxxx);

		JLabel lblessonName = new JLabel("课程名称:");
		lblessonName.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		lblessonName.setBounds(162, 99, 90, 37);
		add(lblessonName);

		JLabel label_1 = new JLabel("任课老师：");
		label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_1.setBounds(162, 159, 90, 31);
		add(label_1);

		JLabel label_2 = new JLabel("课程学分：");
		label_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_2.setBounds(162, 215, 90, 31);
		add(label_2);

		JLabel label_3 = new JLabel("上课时间：");
		label_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_3.setBounds(162, 328, 90, 31);
		add(label_3);

		label_5 = new JLabel("第");
		label_5.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_5.setBounds(365, 336, 22, 15);
		add(label_5);

		JLabel labelTo = new JLabel("到");
		labelTo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		labelTo.setBounds(441, 336, 22, 15);
		add(labelTo);

		label_6 = new JLabel("节");
		label_6.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_6.setBounds(519, 336, 22, 15);
		add(label_6);

		JLabel label_4 = new JLabel("上课地点：");
		label_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_4.setBounds(162, 383, 90, 31);
		add(label_4);

		JLabel label = new JLabel("最大学生人数：");
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label.setBounds(162, 277, 106, 31);
		add(label);

		int n = getComponentCount();
		for (int i = 0; i < n; ++i) {
			this.getComponent(i).setFont(
					new Font("Microsoft YaHei", Font.PLAIN, 12));
		}
	}
	private void addMyBox(){
		startBox = new MyComboBox<>(TimeLesEnd, new Rectangle(382, 328, 59, 31), "1");
		dayBox = new MyComboBox<>(TimeDay, new Rectangle(264, 331, 96, 31), "星期一");
		endBox = new MyComboBox<>(TimeLesEnd, new Rectangle(459, 328, 59, 31), "1");
		teacherBox = new MyComboBox<>(jcbTeacher, new Rectangle(387, 162, 106, 31),"请选择教师");
		insBox = new MyComboBox<>(jcbInstitute, new Rectangle(264, 162, 106, 31),"请选择院系");
		lessonBox = new MyComboBox<>(jcbLessonAb, new Rectangle(264, 59, 229, 27), "请选择课程");
		creditBox = new MyComboBox<>(jcbCredit, new Rectangle(264, 218, 64, 31), "0");
	
	}
	private void setInputComponents() {
	
		jcbLessonAb = new JComboBox<LessonAbstractVO>();

		jfLessonName = new JTextField();
		jfLessonName.setBounds(264, 108, 229, 27);

		jcbInstitute = new JComboBox();

		jcbTeacher = new JComboBox();

		jcbCredit = new JComboBox();

		dayList = new String[] { "星期一", "星期二", "星期三", "星期四", "星期五" };
		lesList = new Integer[11];
		for (int i = 0; i < lesList.length; ++i) {
			lesList[i] = (i + 1);
		}
		TimeDay = new JComboBox(dayList);
		TimeLesStart = new JComboBox(lesList);
		TimeLesEnd = new JComboBox(lesList);

		Location = new JTextField();
		Location.setColumns(10);
		Location.setBounds(264, 383, 229, 31);

		maxStudent = new JFormattedTextField(NumberFormat.getIntegerInstance());
		maxStudent.setColumns(10);
		maxStudent.setBounds(264, 278, 90, 31);

	}
}
