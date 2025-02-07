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
 * 编辑课程信息
 * @author luck
 *
 */
public class Ins_EditCourse extends JPanel {
	private JComboBox<TeacherVO> jcbTeacher;
	private MyComboBox<TeacherVO> teacherBox;
	private JComboBox<Integer> jcbCredit;
	private MyComboBox<Integer> creditBox;
	private JTextField Location;
	private JTextField maxStudent;
	private MyButton jbtSave;
	private MyButton jbtCancel;
	private JComboBox<LessonUniqueVO> jcbLesson;
	private MyComboBox<LessonUniqueVO> lessonBox;
	private JComboBox<String> jcbInstitute;
	private MyComboBox<String> insBox;
	/**
	 * 存放课程列表
	 */
	ArrayList<LessonUniqueVO> lessonList;
	/**
	 * 存放院系列表
	 */
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

	
	public Ins_EditCourse(InsTeacherBlService insTeacher) {

		this.insTeacher = insTeacher;
		setLayout(null);
		setInputComponents();
		new Thread() {
			public void run() {
				MainFrameUI.loadingPanel.setMessage("正在加载课程...");
				MainFrameUI.showWating();
				try {
					pullValuse();
				} catch (RemoteException e) {
					MainFrameUI.showError();
					e.printStackTrace();
				}
				setShow();
				setMyBox();
				setValues();
				addShow();
				addListeners();
				
				cancel();
				MainFrameUI.hideWating();
			}
		}.start();
	}
	/**
	 * 初始化MyComboBox
	 */
	public void setMyBox(){
		startBox = new MyComboBox<>(TimeLesEnd, new Rectangle(353, 283, 60, 31), "1");
		dayBox = new MyComboBox<>(TimeDay, new Rectangle(227, 284, 100, 31), "星期一");
		endBox = new MyComboBox<>(TimeLesEnd, new Rectangle(436, 285, 60, 31), "1");
		teacherBox = new MyComboBox<>(jcbTeacher, new Rectangle(438,164,106,31),"请选择教师");
		insBox = new MyComboBox<>(jcbInstitute, new Rectangle(227, 164,185,31),"请选择院系");
		lessonBox = new MyComboBox<>(jcbLesson, new Rectangle(227,36,229,27), "请选择课程");
		creditBox = new MyComboBox<>(jcbCredit, new Rectangle(227, 227, 60, 31), "0");
	
	}
	/**
	 * 将所有组件添加到面板上
	 */
	private void addShow() {
		addLabel();
		add(jbtSave);
		add(jbtCancel);

		add(dayBox);
		add(startBox);
		add(endBox);
		add(insBox);
		add(teacherBox);
		add(creditBox);
		add(lessonBox);
		add(Location);
		add(maxStudent);
		add(jfLessonName);
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
		Iterator<LessonUniqueVO> iter = insTeacher.showLesson();
		lessonList = new ArrayList<LessonUniqueVO>();
		int i = 0;
		while (iter.hasNext()) {
			lessonList.add(iter.next());
		}
		institutes = CurrentState.getInstitute();
	}

	/**
	 * 将服务器获取的值赋值给组件
	 */
	private void setValues() {

		jcbLesson.setModel(new DefaultComboBoxModel(lessonList.toArray()));

		jcbInstitute.setModel(new DefaultComboBoxModel<String>(institutes));

		LessonUniqueVO vo = (LessonUniqueVO) jcbLesson.getSelectedItem();

		refreshLesson(vo);

	}

	private void addListeners() {
		jbtCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		jbtSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Thread() {
					public void run() {
						MainFrameUI.loadingPanel.setMessage("正在保存...");
						MainFrameUI.showWating();
						save();
						MainFrameUI.hideWating();
					}
				}.start();

			}
		});

		jcbLesson.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					refreshLesson(lessonList.get( jcbLesson.getSelectedIndex()));
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
						endBox.setCell(oldEnd-start);
					} else {
						TimeLesEnd.setSelectedIndex(0);
						endBox.setCell(1);
					}
				}
			}
		});

	}
	/**
	 * 刷新课程
	 * @param vo
	 */
	private void refreshLesson(LessonUniqueVO vo) {

		if (vo == null) {
			return;
		}

		int tea_id = vo.getTea_Id();
		TeacherVO teacherVo = null;
		LessonAbstractVO ab = null;
		try {
			boolean isFound = false;
			Iterator<LessonAbstractVO> iter = insTeacher.getPlan();
			while (iter.hasNext()) {
				ab = iter.next();
				if (ab.getLes_Id_Ab() == vo.getLes_Id_Ab()) {
					isFound = true;
					break;
				}
			}

			teacherVo = insTeacher.getTeacher(tea_id);

			if (!isFound) {
				JOptionPane.showMessageDialog(null, "抽象课程未找到");
				return;
			}

		} catch (RemoteException e) {
			MainFrameUI.showError();
			e.printStackTrace();
		}

		if (teacherVo != null) {
			for (int i = 0; i < institutes.length; ++i) {
				if (vo.getIns_Id() == CurrentState.getInsId(institutes[i])) {
					jcbInstitute.setSelectedIndex(i);
					insBox.setCell(institutes[i]);
					refreshInstitute(institutes[i]);
				}
			}

			for (int i = 0; i < TeacherOfInsList.size(); ++i) {
				if (TeacherOfInsList.get(i).getTea_Id() == tea_id) {
					jcbTeacher.setSelectedIndex(i);
					teacherBox.setCell((TeacherVO)jcbTeacher.getSelectedItem());
				}
			}
		}

		int minCredit = ab.getMin_credit();
		int maxCredit = ab.getMax_credit();
		int size = maxCredit - minCredit + 1;
		Integer[] credits = new Integer[size];

		for (int i = 0; i < size; ++i) {
			credits[i] = minCredit + i;
		}

		TimeDay.setSelectedIndex(vo.getDay() - 1);
		dayBox.setCell((String)TimeDay.getSelectedItem());
		TimeLesStart.setSelectedIndex(vo.getStart() - 1);
		startBox.setCell((Integer)TimeLesStart.getSelectedItem());
		TimeLesEnd.setSelectedIndex(vo.getEnd() - vo.getStart());
		endBox.setCell((Integer)TimeLesEnd.getSelectedItem());
		jcbCredit.setModel(new DefaultComboBoxModel<Integer>(credits));
		jcbCredit.setSelectedIndex(vo.getCredit() - minCredit);
		creditBox.setCell(vo.getCredit());
		jfLessonName.setText(vo.getLessonName());
		maxStudent.setText(String.valueOf(vo.getMax_stu_num()));
		Location.setText(vo.getLocation());
	}

	/**
	 * 根据院系刷新信息
	 * @param selectedIns
	 */
	private void refreshInstitute(String selectedIns) {
		int ins_id = CurrentState.getInsId(selectedIns);
		if (ins_id != -1) {
			try {
				TeacherOfInsList = insTeacher.getTeacherOfIns(ins_id);
				jcbTeacher.setModel(new DefaultComboBoxModel(TeacherOfInsList
						.toArray()));
				teacherBox.setCell((TeacherVO)jcbTeacher.getSelectedItem());
			} catch (RemoteException e) {
				MainFrameUI.showError();
				e.printStackTrace();
			}
		} else {
			TeacherOfInsList = new ArrayList<TeacherVO>();
			jcbTeacher.setModel(new DefaultComboBoxModel(TeacherOfInsList
					.toArray()));
			teacherBox.setCell((TeacherVO)jcbTeacher.getSelectedItem());
		}
	}
	/**
	 * 保存修改
	 */
	private void save() {
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

		LessonUniqueVO lesUn = (LessonUniqueVO) jcbLesson.getSelectedItem();

		int lessonUniqueId = lesUn.getLes_Id();
		
		int lessonAbId = lesUn.getLes_Id_Ab();
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

		LessonUniqueVO vo = new LessonUniqueVO(lessonUniqueId, lessonAbId,
				lessonName, Ins_Id, day, start, end, introduction, books,
				outline, credit, max_stu_num, cur_stu_num, state, tea_id,
				location,lesUn.getTerm());
		try {
			if (insTeacher.modifyLesson(vo)) {
				JOptionPane.showMessageDialog(null, "修改成功");
				int savedIndex = jcbLesson.getSelectedIndex();
				lessonList.set(jcbLesson.getSelectedIndex(), vo);
				jcbLesson.setModel(new DefaultComboBoxModel(lessonList.toArray()));
				jcbLesson.setSelectedIndex(savedIndex);
			} else {
				JOptionPane.showMessageDialog(null, "修改失败");

			}

		} catch (RemoteException e) {
			MainFrameUI.showError();
			e.printStackTrace();
		}
	}
	/**
	 * 重置
	 */
	private void cancel() {
		jcbLesson.setSelectedIndex(-1);
		lessonBox.setCell(null);
		jcbInstitute.setSelectedIndex(0);
		insBox.setCell((String)jcbInstitute.getSelectedItem());
		refreshLesson((LessonUniqueVO) jcbLesson.getSelectedItem());
		maxStudent.setText("200");
		TimeDay.setSelectedIndex(0);
		dayBox.setCell((String)TimeDay.getSelectedItem());
		TimeLesStart.setSelectedIndex(0);
		startBox.setCell((Integer)TimeLesStart.getSelectedItem());
		TimeLesEnd.setSelectedIndex(0);
		endBox.setCell((Integer)TimeLesEnd.getSelectedItem());
		Location.setText("");
		jfLessonName.setText("");

	}

	/**
	 * 将所有组件赋值
	 */
	private void setShow() {

		setInputComponents();

		setButton();
	}
	/**
	 * 加Button
	 */
	private void setButton() {

		jbtSave = new MyButton("保存");
		jbtSave.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		jbtSave.setBounds(162, 413, 90, 32);

		jbtCancel = new MyButton("取消");
		jbtCancel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		jbtCancel.setBounds(292, 413, 90, 32);

	}
	/**
	 * 加Label
	 */
	private void addLabel() {
		JLabel lblxxx = new JLabel("选择课程：");
		lblxxx.setBounds(162, 29, 96, 37);
		lblxxx.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(lblxxx);

		JLabel lblessonName = new JLabel("课程名称:");
		lblessonName.setBounds(162, 96, 68, 37);
		lblessonName.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(lblessonName);

		JLabel label_1 = new JLabel("任课老师：");
		label_1.setBounds(162, 163, 82, 31);
		label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_1);

		JLabel label_2 = new JLabel("课程学分：");
		label_2.setBounds(162, 226, 82, 31);
		label_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_2);

		JLabel label_3 = new JLabel("上课时间：");
		label_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		label_3.setBounds(162, 283, 82, 31);
		label_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_3);

		label_5 = new JLabel("第");
		label_5.setBounds(333, 291, 30, 15);
		label_5.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_5);

		JLabel labelTo = new JLabel("到");
		labelTo.setBounds(414, 291, 22, 15);
		labelTo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(labelTo);

		label_6 = new JLabel("节");
		label_6.setBounds(498, 291, 54, 15);
		label_6.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_6);

		JLabel label_4 = new JLabel("上课地点：");
		label_4.setBounds(162, 345, 82, 31);
		label_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label_4);

		JLabel label = new JLabel("最大学生人数：");
		label.setBounds(333, 226, 128, 31);
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
		add(label);

		int n = getComponentCount();
		for (int i = 0; i < n; ++i) {
			this.getComponent(i).setFont(
					new Font("Microsoft YaHei", Font.PLAIN, 12));
		}
	}
	/**
	 * 加TextField
	 */
	private void setInputComponents() {

		jcbLesson = new JComboBox<LessonUniqueVO>();

		jfLessonName = new JTextField();
		jfLessonName.setBounds(227, 103, 229, 27);

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
		Location.setBounds(232, 346, 229, 31);

		maxStudent = new JFormattedTextField(NumberFormat.getIntegerInstance());
		maxStudent.setColumns(10);
		maxStudent.setBounds(438, 227, 64, 31);

	}
}
