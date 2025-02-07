package presentation.uielements.tablehead;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 用户的表头
 * @author luck
 *
 */
public class UserHead extends JPanel{
	public UserHead(){
		setLayout(null);
		setOpaque(false);
		String thCss = " style=\"background-color:#d2bbd2;width:60px;height:px;\"";
		String thCss_1 = " style=\"background-color:#d2bbd2;width:200px;height:34px;\"";
		String thCss_2 = " style=\"background-color:#d2bbd2;width:100px;height:34px;\"";
		String thCss_3 = " style=\"background-color:#d2bbd2;width:50px;height:34px;\"";
		JLabel tableHead = new JLabel(
				"<HTML>" +
				"<table>" +
					"<th"+thCss+">学工号</th>" +
					"<th"+thCss+">姓   名</th>" +
					"<th"+thCss+">院系</th>" +
					"<th"+thCss+">用户类型</th>" +
				"</table>" +
				"</HTML>");
		tableHead.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
		tableHead.setBounds(6, 6, 300, 72);
		add(tableHead);
	}
}
