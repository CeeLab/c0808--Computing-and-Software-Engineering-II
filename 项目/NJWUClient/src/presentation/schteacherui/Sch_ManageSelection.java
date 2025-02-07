package presentation.schteacherui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import po.SystemState;
import presentation.mainui.MainFrameUI;
import presentation.uielements.MyButton;
import utility.CurrentState;
import businesslogicservice.schteacherblservice.SchTeacherBlService;

public class Sch_ManageSelection extends JPanel {
	SchTeacherBlService schTeacher;
	ButtonGroup selectGroup = new ButtonGroup();
	ButtonGroup bySelectGroup = new ButtonGroup();
	JLabel lbMessage;
	JRadioButton rbStartBy;
	JRadioButton rbStopBy;
	JRadioButton rbStartSelection;
	JRadioButton rbStopSelection;

	public Sch_ManageSelection(SchTeacherBlService schTeacher) {
		setLayout(null);
		this.schTeacher = schTeacher;
		rbStartSelection = new JRadioButton("开启选课");
		rbStartSelection.setBounds(244, 175, 140, 23);
		rbStartSelection
				.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		add(rbStartSelection);

		rbStopSelection = new JRadioButton("结束选课");
		rbStopSelection.setBounds(421, 175, 140, 23);
		rbStopSelection.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		add(rbStopSelection);

		rbStartBy = new JRadioButton("开启补选");
		rbStartBy.setBounds(244, 245, 140, 23);
		rbStartBy.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		add(rbStartBy);

		rbStopBy = new JRadioButton("结束补选");
		rbStopBy.setBounds(421, 245, 140, 23);
		rbStopBy.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 17));
		add(rbStopBy);

		MyButton button = new MyButton("确定");
		button.setBounds(261, 379, 93, 30);
		button.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		button.addActionListener(new SubmitListener());
		add(button);

		
		lbMessage = new JLabel(state);
		lbMessage.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 15));
		lbMessage.setBounds(325, 65, 180, 40);
		add(lbMessage);
		refresh();
		MyButton btAllocate = new MyButton("开始分配选课");
		btAllocate.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 14));
		btAllocate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alloSelect();
			}
		});
		btAllocate.setBounds(392, 379, 124, 30);
		add(btAllocate);
		refresh();
		
		JLabel label = new JLabel("管理选课状态");
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(244, 28, 271, 40);
		add(label);
		
		selectGroup.add(rbStartSelection);
		selectGroup.add(rbStopSelection);
		bySelectGroup.add(rbStartBy);
		bySelectGroup.add(rbStopBy);

	}
	String state = null;
	public void refresh() {
	
		if (CurrentState.select == 1) {
			state = "当前已开启选课";
		} else if (CurrentState.bySelect == 1) {
			state = "当前已开启补选";
		} else {
			state = "选课已经结束";
		}
		lbMessage.setText(state);
		if (CurrentState.select == 1) {
			rbStartSelection.setSelected(true);
			rbStopBy.setEnabled(false);
			rbStartBy.setEnabled(false);
		} else {
			rbStopSelection.setSelected(true);
			rbStopBy.setEnabled(true);
			rbStartBy.setEnabled(true);
		}
		if (CurrentState.bySelect == 1) {
			rbStartBy.setSelected(true);
			rbStartSelection.setEnabled(false);
			rbStopSelection.setEnabled(false);
		} else {
			rbStopBy.setSelected(true);
			rbStartSelection.setEnabled(true);
			rbStopSelection.setEnabled(true);
		}
	}

	class SubmitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			int select;
			int bySelect;
			if (rbStartSelection.isSelected()) {
				select = 1;
				bySelect = 0;
			} else if (rbStartBy.isSelected()) {
				select = 0;
				bySelect = 1;
			} else {
				select = 0;
				bySelect = 0;
			}
			SystemState state = new SystemState(select, bySelect, 1);
			try {
				if (schTeacher.setState(state)) {
					JOptionPane.showMessageDialog(null, "更新成功");
					refresh();
				} else {
					JOptionPane.showMessageDialog(null, "更新失败...");
				}

			} catch (RemoteException e1) {
				MainFrameUI.showError();
				e1.printStackTrace();
			}
		}
	}
	
	void alloSelect(){
		try {
			MainFrameUI.loadingPanel.setMessage("正在分配，请稍等....");
			MainFrameUI.showWating();
			schTeacher.alloSelect();
			MainFrameUI.hideWating();
			JOptionPane.showMessageDialog(null, "分配成功");
		} catch (RemoteException e) {
			MainFrameUI.hideWating();
			JOptionPane.showMessageDialog(null, "分配失败");
			e.printStackTrace();
		}
	}
}
