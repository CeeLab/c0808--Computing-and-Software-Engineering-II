package presentation.uielements;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
/**
 * 美化了一下Button
 * @author luck
 *
 */
public class MyButton extends JButton{

	Color color = new Color(206,221,236);
	Color backgroundColor = new Color(255,255,255); 
	public MyButton(String string) {
		super(string);
		setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));
		setContentAreaFilled(false);
		addMouseListener(new MouseListener() {
			MyButton button;
			public void mouseReleased(MouseEvent e) {
				button = (MyButton)e.getSource();
				button.setBounds(button.getX(), button.getY()-1, button.getWidth(), button.getHeight());
				button.setBackground(new Color(241,241,241));
				MyButton.this.setSize(MyButton.this.getWidth(), MyButton.this.getHeight()-1);
				backgroundColor = new Color(255,255,255);	
				MyButton.this.repaint();

			}
			public void mousePressed(MouseEvent e) {
				button = (MyButton)e.getSource();
				button.setBounds(button.getX(), button.getY()+1, button.getWidth(), button.getHeight());
				button.setBackground(new Color(203,203,203));
				MyButton.this.setSize(MyButton.this.getWidth(), MyButton.this.getHeight()+1);
				backgroundColor = new Color(246,246,246);
				MyButton.this.repaint();
			}
			public void mouseExited(MouseEvent e) {
			}
			public void mouseEntered(MouseEvent e) {
			}
			public void mouseClicked(MouseEvent e) {
			}
		});
//		setBorderPainted(false);
	}

	public void paintBorder(Graphics g) {
		g.setColor(color);
		g.drawRoundRect(0, 0, getSize().width - 1, getSize().height - 1, 10, 10);
	}

	public void paintComponent(Graphics g) {
		   g.setColor(backgroundColor);
		  g.fillRoundRect(0, 0, getSize().width - 1, getSize().height - 1,10, 10);
		  super.paintComponent(g);
		 }
	
	public static void main(String[] argv) {
		JFrame f = new JFrame();
		f.setSize(400, 300);
		MyButton btn = new MyButton("button");
		f.getContentPane().add(btn, BorderLayout.NORTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}