package presentation.uielements;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
/**
 * 用Table填装ComboBOx解决Combox显示上的bug
 * @author luck
 *
 * @param <T>
 */
public class MyComboBox<T> extends JTable{
	JComboBox<T> box;
	public MyComboBox(JComboBox<T> box,Rectangle bounds,String head){
		setBounds(bounds);
		String data[][] = new String[1][1];
		int width = (int) bounds.getWidth();
		int length = head.length();
		if (length!=1){
			for (int i =0; i<(width-length*16)/4;i++){
				head=head+" ";
			}
		} else {
			head = head+"   ";
		}
	
		head =head+"▼";
		data[0][0] = head;
		String tHead[] = {""};
		setModel(new DefaultTableModel(data, tHead) {
			public boolean isCellEditable(int row, int column) {
				return true;
			}
		});
		getColumnModel().getColumn(0).setPreferredWidth(getWidth()+1);
		getColumnModel().getColumn(0).setMaxWidth(getWidth()+1);
		getColumnModel().getColumn(0).setMinWidth(getWidth()+1);
		setRowHeight(getHeight()+1);
		getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(box));
//		box.setSelectedIndex(0);
		box.setFont(new Font("Microsoft YaHei",Font.PLAIN,12));
		setFont(new Font("Microsoft YaHei",Font.PLAIN,12));
		setBackground(new Color(250,250,250));
	}
	
	public void setCell(T element){
		if (element == null){
			getModel().setValueAt("", 0, 0);
			
		} else 
			getModel().setValueAt(element.toString(), 0, 0);
	}

}
