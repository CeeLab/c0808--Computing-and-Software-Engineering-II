package presentation.studentui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import presentation.mainui.MainFrameUI;
import presentation.uielements.MyButton;
import presentation.uielements.MyComboBox;
import presentation.uielements.MyTable;
import presentation.uielements.MyTableScrollerPane;
import presentation.uielements.UserShowPanel;
import utility.CurrentState;
import vo.LessonUniqueVO;
import businesslogicservice.studentblservice.StudentBlService;
/**
 * 选课界面
 * 判断系统状态
 * @author luck
 *
 */
public class Stu_ShowSelect extends Stu_ShowPanel {
	private int chooseType;
	private MyTable chosenTable;
	private int type;
	private int ins_id = 1;
	private JComboBox<String> comboBox;
	String chosenTHead[] = { "", "" };
	ArrayList<LessonUniqueVO> lessons = new ArrayList<>();
	ArrayList<LessonUniqueVO> mySelections = new ArrayList<>();
	String[][] chosenData = new String[4][2];
	int[] lessonId = new int[20];
	int[] chosenId = new int[4];

	public void setIns_id(int ins_id) {
		this.ins_id = ins_id;
	}

	public void addCombBox() {
		JLabel label = new JLabel("院系:");
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		label.setBounds(0, 17, 69, 36);
		this.add(label);
		String institutes[] = CurrentState.getInstitute();
		comboBox = new JComboBox<String>(institutes);// 这个comboBox死活不显示我暂时还没弄懂为毛线不显示（´Д`）……
		comboBox.setVisible(true);
		comboBox.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		MyComboBox<String> myBox = new MyComboBox<>(comboBox, new Rectangle(68,
				20, 120, 33), "选择院系");
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ins_id = CurrentState.getInsId((String) comboBox
						.getSelectedItem());
				new Thread() {
					public void run() {
						MainFrameUI.loadingPanel.setMessage("正在获取信息...");
						MainFrameUI.showWating();
						refresh();
						MainFrameUI.hideWating();
					}

				}.start();

			}
		});
		this.add(myBox);
	}

	public void refresh() {
		getData();
		table.refresh(rowData, tableHead);
		chosenTable.refresh(chosenData, chosenTHead);
		setTableWidth();
	}

	public void getData() {
		try {
			student.setLessonType(type);
			Iterator<LessonUniqueVO> iterator = student.showSelection(ins_id);
			setLessons(iterator);
			if (chooseType == StuMainUI.SELECT) {
				iterator = student.show_mySelection();
			} else {
				iterator = student.show_myLesson().getlessons();
			}
			setMySelection(iterator);
			fillchosen(chosenData);
			fillRowData();
		} catch (RemoteException e) {
			MainFrameUI.showError();
			e.printStackTrace();
		}
	}

	public void setLessons(Iterator<LessonUniqueVO> iterator) {
		lessons.clear();
		while (iterator.hasNext()) {
			lessons.add(iterator.next());
		}
	}

	public void setMySelection(Iterator<LessonUniqueVO> iterator) {
		mySelections.clear();
		while (iterator.hasNext()) {
			LessonUniqueVO lesson = iterator.next();
			for (LessonUniqueVO vo : lessons) {
				if (lesson.getLes_Id() == vo.getLes_Id()) {
					mySelections.add(lesson);
				}
			}
		}
	}

	public Stu_ShowSelect(StudentBlService student, int type, int chooseType) {
		if ((chooseType==StuMainUI.SELECT&&CurrentState.select==1)||(chooseType==StuMainUI.BYSELECT&&CurrentState.bySelect==1)){

			this.chooseType = chooseType;
			this.student = student;
			this.type = type;
			tableHead = new String[] { "", "", "", "", "", "", "", "" };
			if (type == 5) {
				addCombBox();
			}
			getData();
			setThead();
			initialTable();
			// JLabel label_1 = new JLabel("课程号");
			// label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
			// label_1.setBounds(293, 16, 69, 36);
			// add(label_1);
			//
			// searchField = new JTextField();
			// searchField.setBounds(340, 20, 134, 28);
			// add(searchField);
			// searchField.setColumns(10);
			//
			// MyButton button = new MyButton("查询");
			// button.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
			// button.setBounds(486, 21, 80, 29);
			// add(button);
			JLabel label_2 = new JLabel(
					"<HTML>"
							+ "<table>"
							+ "<th style=\"background-color:#d2bbd2;width:210px;height:40px;\">已选择课程</th>"
							+ "</table>" + "</HTML>");
			label_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
			label_2.setHorizontalAlignment(SwingConstants.CENTER);
			label_2.setBounds(602, 62, 221, 47);
			add(label_2);	
		}else {
			int count = getComponentCount();
			for (int i =0; i < count; i++){
				remove(getComponent(i));
			}
			String message = chooseType==(StuMainUI.SELECT)?"选课还未开始":"补选还未开始";
			JLabel label = new JLabel(message);
			label.setForeground(Color.RED);
			label.setFont(new Font("Microsoft YaHei",Font.PLAIN,20));
			label.setBounds(200,10,300,100);
			add(label,0);
		}
	}

	class ChosenTableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if (chosenTable.getSelectedColumn() == 1) {
				if (chosenData[chosenTable.getSelectedRow()][1].equals("退选")) {
					if (chooseType == StuMainUI.SELECT) {
						new Thread() {
							public void run() {
								MainFrameUI.loadingPanel
										.setMessage("正在提交请求...");
								MainFrameUI.showWating();
								try {
									student.cancel(chosenId[chosenTable
											.getSelectedRow()]);
									student.submit();
								} catch (RemoteException e1) {
									MainFrameUI.showError();
									e1.printStackTrace();
								}
								MainFrameUI.loadingPanel
										.setMessage("正在刷新页面...");
								refresh();
								MainFrameUI.hideWating();
							}
						}.start();

					} else {
						new Thread() {
							public void run() {
								MainFrameUI.loadingPanel
										.setMessage("正在处理请求...");
								MainFrameUI.showWating();
								try {
									student.by_cancel(chosenId[chosenTable
											.getSelectedRow()]);
								} catch (RemoteException e1) {
									MainFrameUI.showError();
									e1.printStackTrace();
								}
								MainFrameUI.loadingPanel
										.setMessage("正在刷新页面...");
								refresh();
								MainFrameUI.hideWating();
							}
						}.start();

					}

				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO 自动生成的方法存根

		}

	}

	@Override
	public void setTableWidth() {
		for (int i = 0; i < 8; i++) {
			int width;
			if (i == 1) {
				width = 190;
			} else if (i == 2) {
				width = 45;
			} else if (i == 3) {
				width = 80;
			} else if (i == 4) {
				width = 65;
			} else if (i == 6) {
				width = 30;
			} else if (i == 7) {
				width = 30;
			} else {
				width = 60;
			}
			table.getColumnModel().getColumn(i).setPreferredWidth(width);
			table.getColumnModel().getColumn(i).setMaxWidth(width);
			table.getColumnModel().getColumn(i).setMinWidth(width);
		}
		int width_Long = 185;
		int width_Short = 35;
		chosenTable.getColumnModel().getColumn(0).setPreferredWidth(width_Long);
		chosenTable.getColumnModel().getColumn(0).setMaxWidth(width_Long);
		chosenTable.getColumnModel().getColumn(0).setMinWidth(width_Long);
		chosenTable.getColumnModel().getColumn(1)
				.setPreferredWidth(width_Short);
		chosenTable.getColumnModel().getColumn(1).setMaxWidth(width_Short);
		chosenTable.getColumnModel().getColumn(1).setMinWidth(width_Short);

	}

	class TableListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {

			if (table.getSelectedColumn() == 7) {
				if (rowData[table.getSelectedRow()][7].equals("选择")) {
					System.out.println(mySelections.size());
					if (mySelections.size() >= 4) {
						JOptionPane.showMessageDialog(null, "您的选课已满4节");
						return;
					} else if (chooseType == StuMainUI.SELECT) {

						new Thread() {
							public void run() {
								try {
									MainFrameUI.loadingPanel
											.setMessage("正在提交...");
									MainFrameUI.showWating();
									student.select(lessonId[table
											.getSelectedRow()]);
									student.submit();
									MainFrameUI.loadingPanel
											.setMessage("正在刷新页面...");
									MainFrameUI.showWating();
									refresh();
									MainFrameUI.hideWating();
								} catch (RemoteException e1) {
									MainFrameUI.showError();
									e1.printStackTrace();
								}
							}
						}.start();

					} else {
						new Thread() {
							public void run() {
								try {
									MainFrameUI.loadingPanel
											.setMessage("正在提交...");
									MainFrameUI.showWating();
									if (student.by_select(lessonId[table
											.getSelectedRow()])) {
										MainFrameUI.hideWating();
										JOptionPane.showMessageDialog(null,
												"选课成功");
									} else {
										JOptionPane.showMessageDialog(null,
												"该课程已选满");
									}
								} catch (RemoteException e1) {
									MainFrameUI.showError();
									e1.printStackTrace();
								}
								MainFrameUI.loadingPanel
										.setMessage("正在刷新页面...");
								MainFrameUI.showWating();
								refresh();
								MainFrameUI.hideWating();
							}
						}.start();

					}
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// 这里写个鼠标移过去变蓝色的效果
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// 这里恢复
		}
	}

	private void fillchosen(String[][] chosenData) {
		int index = 0;
		Iterator<LessonUniqueVO> iterator = mySelections.iterator();
		while (iterator.hasNext()) {
			LessonUniqueVO lesson = iterator.next();
			chosenData[index][0] = lesson.getLessonName();
			chosenData[index][1] = "退选";
			chosenId[index] = lesson.getLes_Id();
			index++;
		}
		while (index <= 3) {
			chosenData[index][0] = null;
			chosenData[index][1] = null;
			index++;
		}
	}

	private boolean checkMyselection(int lessonId) {
		Iterator<LessonUniqueVO> iterator = mySelections.iterator();
		while (iterator.hasNext()) {
			LessonUniqueVO lesson = iterator.next();
			if (lesson.getLes_Id() == lessonId)
				return true;
		}
		return false;
	}

	@Override
	public void setThead() {
		String thCss = " style=\"background-color:#d2bbd2;height:40px;";
		JLabel tHead = new JLabel("<HTML>" + "<table>" + "<th" + thCss
				+ "width:45px;\">课程号</th>" + "<th" + thCss
				+ "width:145px;\">课程名</th>" + "<th" + thCss
				+ "width:33px;\">教 师</th>" + "<th" + thCss
				+ "width:61px;\">上课时间</th>" + "<th" + thCss
				+ "width:49px;\">上课地点</th>" + "<th" + thCss
				+ "width:45px;\">人数</th>" + "<th" + thCss
				+ "width:22px;\">学分</th>" + "<th" + thCss
				+ "width:22px;\">  </th>" + "</table>" + "</HTML>");
		tHead.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		tHead.setBounds(6, 45, 568, 82);
		add(tHead);
	}

	@Override
	public void fillRowData() {
		int index = 0;
		Iterator<LessonUniqueVO> iterator = lessons.iterator();
		ArrayList<LessonUniqueVO> list = new ArrayList<>();
		while (iterator.hasNext()) {
			list.add(iterator.next());
		}
		rowData = new String[list.size()][9];
		for (LessonUniqueVO lesson : list) {
			rowData[index][0] = lesson.getLessonId();
			rowData[index][1] = lesson.getLessonName();
			rowData[index][2] = lesson.getTeacherName();
			rowData[index][3] = lesson.getTime();
			rowData[index][4] = lesson.getLocation();
			rowData[index][5] = lesson.getStu_num();
			rowData[index][6] = lesson.getCreditString();
			rowData[index][7] = checkMyselection(lesson.getLes_Id()) ? "√"
					: "选择";
			lessonId[index] = lesson.getLes_Id();
			index++;
		}
	}

	@Override
	public void initialTable() {
		table = new MyTable(rowData, tableHead);
		table.addMouseListener(new TableListener());
		tableScrollPane = new MyTableScrollerPane(table);
		tableScrollPane.setBounds(5, 116, 589, 373);
		add(tableScrollPane);
		chosenTable = new MyTable(chosenData, chosenTHead);
		chosenTable.addMouseListener(new ChosenTableListener());
		chosenTable.setBounds(602, 116, 221, 126);
		setTableWidth();
		add(chosenTable);
	}
}
