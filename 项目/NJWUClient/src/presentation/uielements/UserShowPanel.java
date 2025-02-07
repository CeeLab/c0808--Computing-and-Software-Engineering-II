package presentation.uielements;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
/**
 * 展示界面的模板
 * @author luck
 *
 */
public abstract class UserShowPanel extends JPanel {
	protected JLabel tHead;
	protected String tableHead[];
	protected String[][] rowData;
	protected MyTable table;
	protected JTextField searchField;
	protected JScrollPane tableScrollPane;
	protected String thCss = " style=\"background-color:#d2bbd2;width:89px;height:40px;\"";

	public UserShowPanel() {
		initial();
	}

	public void initial() {
		setBounds(201, 128, 851, 495);
		setOpaque(false);
		setLayout(null);
		setThead();
	}

	public abstract void setThead();

	public abstract void fillRowData();

	public abstract void setTableWidth();

	public void initialTable() {
		table = new MyTable(rowData, tableHead);
		tableScrollPane = new MyTableScrollerPane(table);
		setTableWidth();
		add(tableScrollPane);
		repaint();
	}

}
