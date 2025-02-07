package presentation.mainui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import presentation.adminui.AdminMainUI;
import presentation.insteacherui.InsMainUI;
import presentation.schteacherui.SchMainUI;
import presentation.srcBag.SrcBag;
import presentation.studentui.StuMainUI;
import presentation.teacherui.TeaMainUI;
import presentation.uielements.GrayPanel;
import presentation.uielements.LoadingPanel;
import presentation.uielements.MyButton;
import sun.awt.geom.AreaOp.AddOp;
import sun.font.GraphicComponent;
import sun.org.mozilla.javascript.internal.ast.LabeledStatement;
import utility.Constant;
import businesslogic.insteacherbl.InsTeacher;
import businesslogic.userbl.UserController;
import businesslogicservice.userblservice.BroadCastReceiverService;
import businesslogicservice.userblservice.UserControllerService;

import java.awt.Color;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
/**
 * 主界面
 * @author luck
 *
 */
public class MainFrameUI extends JFrame{
	public static LoadingPanel loadingPanel = new LoadingPanel("正在登录....");
	public static GrayPanel grayPanel = new GrayPanel();
	JPanel panel;
	JLabel bgLabel;
	private JPasswordField passwordField;
	private JTextField textField;
	private static UserControllerService userController = new UserController();
	static JPanel loginUI = new JPanel();
	public static UserMainUI userMainUI;
	MyButton confirmButton;
	MyButton cancelButton;
	JLabel label_Remind;
	/**
	 * 显示错误的Label
	 */
	private JLabel label_error;
	/**
	 * 通知
	 */
	BroadCastReceiverService broadCast;
	public MainFrameUI() {
		int X, Y;
		int WIDTH = 1100;
		int HEIGHT = 670;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		X = this.getToolkit().getScreenSize().width;
		Y = this.getToolkit().getScreenSize().height;
		setBounds((X - WIDTH) / 2, (Y - HEIGHT) / 2 - 27, WIDTH, HEIGHT);

		panel = new JPanel();
		panel.setBounds(0, 0, WIDTH, HEIGHT);
		getContentPane().add(panel);
		
		ImageIcon bg = new ImageIcon(SrcBag.loginBgSrc);
		panel.setLayout(null);
		ImageIcon loginIcon = new ImageIcon(SrcBag.loginSrc);
		ImageIcon noticeIcon = new ImageIcon(SrcBag.notice);

		loginUI.setBounds(0, 10, 1100, 670);
		loginUI.setLayout(null);
		loginUI.setOpaque(false);
		
		JLabel notice = new JLabel();
		notice.setIcon(noticeIcon);
		notice.setBounds(0,112,WIDTH,30);
		notice.setLayout(null);
		loginUI.add(notice,0);


		JLabel label = new JLabel("系统登录");
		label.setFont(new Font("Microsoft YaHei", Font.PLAIN, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(419, 270, 297, 37);
		loginUI.add(label);

		JLabel loginLabel = new JLabel();
		loginLabel.setBounds(247, 185, 663, 413);
		loginLabel.setIcon(loginIcon);
		loginLabel.setVisible(true);
		
		JLabel label_3 = new JLabel();
		label_3.setBounds(421, 397, 300, 37);
		ImageIcon bg_password = new ImageIcon(SrcBag.password);
		label_3.setIcon(bg_password);
		loginUI.add(label_3,0);
		
		JLabel label_4 = new JLabel();
		label_4.setBounds(421, 342, 300, 37);
		ImageIcon bg_userIcon = new ImageIcon(SrcBag.user);
		label_4.setIcon(bg_userIcon);
		loginUI.add(label_4,0);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(471, 397, 240, 37);
		passwordField.setOpaque(false);
		passwordField.setBorder(null);
		loginUI.add(passwordField,0);
		passwordField.addKeyListener(new PasswordEnterListener());
		
		textField = new JTextField();
		textField.setBounds(471, 342, 240, 37);
		textField.setOpaque(false);
		textField.setBorder(null);
		loginUI.add(textField,0);
		textField.setColumns(10);
		textField.addKeyListener(new TextEnterListener());
		textField.requestFocus();
		
		confirmButton = new MyButton("确认");
		confirmButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
		confirmButton.setBounds(438, 466, 113, 40);
		confirmButton.addActionListener(new ConfirmListener());
		loginUI.add(confirmButton);
		
		cancelButton = new MyButton("重置");
		cancelButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
		cancelButton.setBounds(589, 466, 113, 40);
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				passwordField.setText("");
				textField.setText("");
				textField.requestFocus();
			}
		});
		loginUI.add(cancelButton);
		loginUI.add(loginLabel);

		JLabel exitLabel = new JLabel();
		exitLabel.setBounds(26, 594, 78, 56);
		ImageIcon exitIcon = new ImageIcon(SrcBag.exitSrc);
		exitLabel.setIcon(exitIcon);
		exitLabel.addMouseListener(new ExitListener());
		panel.add(exitLabel);

		panel.add(loginUI);
		
		label_error = new JLabel("用户名或密码错误");
		label_error.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		label_error.setForeground(Color.RED);
		label_error.setBounds(727, 408, 96, 15);
		label_error.setVisible(false);
		loginUI.add(label_error,0);
		
		label_Remind = new JLabel("用户ID为纯数字");
		label_Remind.setForeground(Color.RED);
		label_Remind.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		label_Remind.setBounds(727, 347, 113, 28);
		label_Remind.setVisible(false);
		loginUI.add(label_Remind,0);
		// panel.add(userMainUI);
		// userMainUI.setVisible(false);

		bgLabel = new JLabel();
		bgLabel.setBounds(0, 0, WIDTH, HEIGHT);
		bgLabel.setIcon(bg);
		panel.add(bgLabel);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
		getContentPane().add(grayPanel,0);
		loadingPanel.setBounds(450,300,loadingPanel.getWidth(),loadingPanel.getHeight());
		getContentPane().add(loadingPanel,0);		
		loadingPanel.setVisible(false);
		grayPanel.setVisible(false);
		/**
		 * 通知
		 */
		try {
			broadCast = userController.getBroadCast();
			String[] messages = broadCast.receiveBroadCast();
			String s = "通知: ";
			String detail = "<Html>";
			int count = 0;
			
			for (String string:messages){
				
				count++;
				s += count + "." + string + "    ";
				detail = detail+count+"."+string+"<br>";
			}
			detail = detail+"<br></Html>";
			final JLabel noticeNews = new JLabel(s);
			final JLabel detailLabel = new JLabel(detail);
			noticeNews.setBounds(WIDTH,112,WIDTH,30);
			noticeNews.setLayout(null);
			loginUI.add(noticeNews,0);
			final Thread thread = new Thread(){
				public void run(){
					final int WIDTH = 1100;
					int i=0;
					while(true){
						noticeNews.setLocation(WIDTH,112);
						noticeNews.repaint();
						for (i=0;i<2*WIDTH/10;i=i+1){
							try {
								sleep(200);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							noticeNews.setLocation(WIDTH-i*10,112);
							noticeNews.repaint();
						}
					}
				}
			};
			if (count == 0) {
				notice.setVisible(false);
			}else{
				thread.start();
				detailLabel.setVisible(false);
				add(detailLabel,0);
				
				notice.addMouseListener(new MouseAdapter() {

					@Override
					public void mouseExited(MouseEvent e) {
						detailLabel.setVisible(false);
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						int x = e.getX();
						int y = e.getY();
						detailLabel.setBounds(x, y+50,1000,200);
						detailLabel.setVisible(true);
					}
				
				});
			}
		} catch (Exception e1) {
			notice.setVisible(false);
		} 
		
	}
	/**
	 * 缓冲
	 */
	public static void showWating(){
		loadingPanel.setVisible(true);
		grayPanel.setVisible(true);	
	}
	public static void hideWating(){
		loadingPanel.setVisible(false);
		grayPanel.setVisible(false);	
	}
	public static void disableAll(){
		loginUI.setVisible(false);
		showWating();
	}
	public static void enableAll(){
		loginUI.setVisible(true);
		hideWating();
	}
	/**
	 * id栏监听
	 * @author luck
	 *
	 */
	class TextEnterListener implements KeyListener{
		
		public void keyTyped(KeyEvent e) {
			char input = e.getKeyChar();
			boolean ignore = input == (char) KeyEvent.VK_ESCAPE
					|| input == (char) KeyEvent.VK_BACK_SPACE
					|| input == (char) KeyEvent.VK_ENTER
					|| input == (char) KeyEvent.VK_DELETE;
			if (ignore) {
			} else if (input >= KeyEvent.VK_0 && input <= KeyEvent.VK_9) {
				label_Remind.setVisible(false);
			} else {
				label_Remind.setVisible(true);
				e.consume(); // 关键，屏蔽掉非法输入
			}
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				passwordField.requestFocus();
			}
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
	/**
	 * 密码栏监听
	 * @author luck
	 *
	 */
	class PasswordEnterListener implements KeyListener{
		
		public void keyTyped(KeyEvent e) {
		}
		
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER){
				disableAll();
				new Thread(){
					@Override
					public void run(){
						checkToLogin();
					}
				}.start();
			}
		}

		public void keyReleased(KeyEvent e) {	
		}
		
	}
	/**
	 * 退出监听
	 * @author luck
	 *
	 */
	class ExitListener implements MouseListener {
		public void mouseClicked(MouseEvent e) {
			System.exit(0);
		}

		public void mousePressed(MouseEvent e) {
		}

		public void mouseReleased(MouseEvent e) {
		}

		public void mouseEntered(MouseEvent e) {
		}

		public void mouseExited(MouseEvent e) {
		}
	}
	/**
	 * 隐藏登陆界面
	 */
	public void hideAll() {
		loginUI.setVisible(false);
	}
	/**
	 * 展示主界面
	 * @param panel
	 */
	public static void showMain(JPanel panel) {
		if (panel != null)
			panel.setVisible(false);
		panel=null;
		loginUI.setVisible(true);
	}
	/**
	 * 网络出现故障后调用的方法
	 */
	public static void showError() {
		userController.reConnect();
		JOptionPane.showMessageDialog(null, "网络连接出现故障");
		showMain(userMainUI);
	}
	/**
	 * 展示某个Panel
	 * @param panelToShow
	 */
	public void showPanel(JPanel panelToShow) {
		panel.add(panelToShow, 1);
		panel.repaint();
		panelToShow.setVisible(true);
	}
	/**
	 * 登陆的监听
	 * @author luck
	 *
	 */
	class ConfirmListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			disableAll();
			new Thread(){
				@Override
				public void run(){
					checkToLogin();
				}
			}.start();
			
		}
	}
	/**
	 * 登陆
	 */
	private void checkToLogin(){
		loadingPanel.setMessage("正在登录中...");
		String userID = textField.getText();
		int id;
		try {
			id = Integer.parseInt(userID);
		} catch (Exception e2) {
			enableAll();
			JOptionPane.showMessageDialog(null, "用户id必须为纯数字");
			return;
		}
		char[] password = passwordField.getPassword();
		int userType = Constant.UserType.NO_LOGIN;
		try {
			userType = userController.login(id, password);
		} catch (Exception e1) {
			showError();
			e1.printStackTrace();
		}
		switch (userType) {
		case Constant.UserType.ADMIN:
			userMainUI = new AdminMainUI(userController);
			break;
		case Constant.UserType.STUDENT:
			userMainUI = new StuMainUI(userController);
			break;
		case Constant.UserType.TEACHER:
			userMainUI = new TeaMainUI(userController);
			break;
		case Constant.UserType.INS_TEACHER:
			userMainUI = new InsMainUI(userController);
			break;
		case Constant.UserType.SCH_TEACHER:
			userMainUI = new SchMainUI(userController);
			break;
		case Constant.UserType.NO_LOGIN:
			enableAll();
			return;
		default:
			enableAll();
			label_error.setVisible(true);
			return;
		}
		label_error.setVisible(false);
		hideAll();
		hideWating();
		showPanel(userMainUI);
	}
}
