package presentation.mainui;

import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.rmi.RemoteException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;

import presentation.uielements.MyButton;
import businesslogicservice.userblservice.UserControllerService;
/**
 * 修改密码的界面
 * @author luck
 *
 */
public class ChangePassword extends JPanel {
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField_2;
	private UserControllerService userController;

	public ChangePassword(UserControllerService userController) {
		this.userController = userController;
		setBounds(201, 128, 851, 495);
		setOpaque(false);
		setLayout(null);

		JLabel label = new JLabel("修改密码");
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 32));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(271, 55, 275, 77);
		add(label);

		JLabel label_1 = new JLabel("请输入原密码");
		label_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		label_1.setBounds(231, 170, 153, 31);
		add(label_1);

		JLabel label_2 = new JLabel("请输入新密码");
		label_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		label_2.setBounds(231, 228, 153, 31);
		add(label_2);

		JLabel label_3 = new JLabel("请输入新密码");
		label_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		label_3.setBounds(231, 290, 153, 31);
		add(label_3);

		passwordField = new JPasswordField();
		passwordField.setBounds(382, 172, 211, 31);
		add(passwordField);
		passwordField.requestFocus();
		passwordField.addKeyListener(new PasswordEnterListener());

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(382, 231, 211, 31);
		add(passwordField_1);
		passwordField_1.addKeyListener(new Password1EnterListener());

		passwordField_2 = new JPasswordField();
		passwordField_2.setBounds(382, 293, 211, 31);
		add(passwordField_2);
		passwordField_2.addKeyListener(new Password2EnterListener());
		

		MyButton changePasswordBtn = new MyButton("更改");
		changePasswordBtn.setFont(new Font("Microsoft YaHei", Font.PLAIN, 18));
		changePasswordBtn.addActionListener(new ChangePasswordListener());
		changePasswordBtn.setBounds(365, 397, 117, 38);
		add(changePasswordBtn);
	}
	/**
	 * 密码栏监听
	 * 回车获取焦点
	 * @author luck
	 *
	 */
	class PasswordEnterListener implements KeyListener{
		
		public void keyTyped(KeyEvent e) {
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				passwordField_1.requestFocus();
			}
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
	/**
	 * 回车跳到下个Field
	 * @author luck
	 *
	 */
	class Password1EnterListener implements KeyListener{
		
		public void keyTyped(KeyEvent e) {
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				passwordField_2.requestFocus();
			}
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
	/**
	 * 回车登陆
	 * @author luck
	 *
	 */
	class Password2EnterListener implements KeyListener{
		
		public void keyTyped(KeyEvent e) {
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				checkToChangePassword();
			}
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
	
	class ChangePasswordListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			checkToChangePassword();
		}
	}
	/**
	 * 登陆
	 */
	private void checkToChangePassword(){
		if (!String.valueOf(passwordField_1.getPassword()).equals(
				String.valueOf(passwordField_2.getPassword()))) {
			JOptionPane.showMessageDialog(null, "请确认两次密码输入相同");
			return;
		}
		new Thread(){
			public void run(){
				MainFrameUI.loadingPanel.setMessage("正在修改中...");
				MainFrameUI.showWating();
				try {
					if (userController.changePassword(passwordField.getPassword(),
							passwordField_1.getPassword())) {
						MainFrameUI.hideWating();
						JOptionPane.showMessageDialog(null, "修改成功");
					} else {
						MainFrameUI.hideWating();
						JOptionPane.showMessageDialog(null, "修改失败 \n 请确认原密码输入正确");
					}
				} catch (HeadlessException e1) {
					e1.printStackTrace();
				} catch (RemoteException e1) {
					e1.printStackTrace();
					MainFrameUI.showError( );
				}	
			}
		}.start();
		
	}
}
