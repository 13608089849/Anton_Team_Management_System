import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

@SuppressWarnings("serial")
public class checkList extends JFrame implements ActionListener {
	private JFrame jf;
	public String username;
	private int jf_width = 1150, jf_height = 620, jt_width = 490, jt_height = 125, rowHeight = 20;
	private JPanel jp_null = new JPanel(), jp_top = new JPanel(), jp_button = new JPanel(), jp_title = new JPanel(),
			jp_table, jp_table_top = new JPanel(), jp_table_buttom = new JPanel();
	private JTextField jtfName, jtfDate, jtfTime, jtfChannel, jtfYY, jtfCommander;
	@SuppressWarnings("rawtypes")
	private JComboBox jcb_list;
	private JButton jb_open, jb_delete, jb_upload;
	private TableModel1 model1 = null, model2 = null;
	private TableModel2 model3 = null, model4 = null;
	private JScrollPane jsp_table1, jsp_table2, jsp_table3, jsp_table4;
	private JTable table1 = null, table2 = null, table3 = null, table4 = null;
	private String[] ButtonString = { "  Open  ", "Delete", "Upload" };
	private String[] string_raid_list;
	public String blank = "          ", destFileName;
	private int fileIndex = 0;
	private ImageIcon background_Intercept, background_Destroy;

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		String comm = arg0.getActionCommand();
		if (comm.equals(ButtonString[0])) {
			openData();
		} else if (comm.equals(ButtonString[1])) {
			deleteData();
		} else if (comm.equals(ButtonString[2])) {
			if (!jtfName.getText().equals("")) {
				upload();
			}
		}
	}

	public checkList(String string_username) {
		username = string_username;

		// ����װ��
		loadRaidList();

		// ����װ��
		setLayoutAll();
	}

	public String[] loadRaidList() {
		File file = new File("res/" + username + "/TeamList");
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			file.mkdirs();
		}
		File[] file_raid_list = file.listFiles();
		String tempString = new String();
		string_raid_list = new String[file_raid_list.length + 1];
		string_raid_list[0] = "                         ";
		for (int i = 0; i < file_raid_list.length; i++) {
			if (file_raid_list[i].isFile()) {
				System.out.println("File��" + file_raid_list[i]);
				tempString = file_raid_list[i].toString();
				string_raid_list[i + 1] = tempString.substring(
						tempString.indexOf(file.toString()) + file.toString().length() + 1,
						tempString.lastIndexOf(".md"));
			}
		}
		return string_raid_list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void setLayoutAll() {
		jf = new JFrame("Anton Team Management System ���� CheckList");
		jf.setLayout(new BorderLayout());

		// �ϲ�
		// ����
		jp_null.add(new JLabel(""));
		jp_null.setOpaque(false);

		/**
		 * ������͸������
		 */

		// ��һ��
		// UIManager.put("ComboBox.background", new Color(0,0,0,0));
		jcb_list = new JComboBox(string_raid_list);
		jcb_list.setOpaque(false);
		jcb_list.setFont(new Font("Dialog", 1, 15));
		jcb_list.setForeground(Color.BLUE); // ������ɫ
		// jcb_list.setBackground(new Color(0,0,0,0)); // �����򱳾�
		jcb_list.setUI(new BasicComboBoxUI() {
			public void installUI(JComponent jcb_list) {
				super.installUI(jcb_list);
				listBox.setForeground(Color.BLACK); // �б�������ɫ
				listBox.setSelectionBackground(Color.BLUE); // �б���
				listBox.setSelectionForeground(Color.RED); // �б�ѡ������ɫ
			}

			/**
			 * �÷��������ұߵİ�ť
			 **/
			protected JButton createArrowButton() {
				return super.createArrowButton();
			}
		});
		jb_open = new JButton(ButtonString[0]);
		jb_open.addActionListener(this);
		jb_open.setContentAreaFilled(false);
		jb_open.setFont(new Font("Dialog", 1, 15));
		jb_open.setForeground(Color.ORANGE);
		jb_delete = new JButton(ButtonString[1]);
		jb_delete.addActionListener(this);
		jb_delete.setContentAreaFilled(false);
		jb_delete.setFont(new Font("Dialog", 1, 15));
		jb_delete.setForeground(Color.ORANGE);
		jb_upload = new JButton(ButtonString[2]);
		jb_upload.addActionListener(this);
		jb_upload.setContentAreaFilled(false);
		jb_upload.setFont(new Font("Dialog", 1, 15));
		jb_upload.setForeground(Color.ORANGE);

		jp_button.setLayout(new FlowLayout());
		jp_button.setOpaque(false);
		jp_button.add(jcb_list);
		jp_button.add(jb_open);
		jp_button.add(jb_delete);
		jp_button.add(jb_upload);

		// �ڶ���
		JLabel jlName = new JLabel("Name��");
		jlName.setOpaque(false);
		jlName.setFont(new Font("Dialog", 1, 14));
		jlName.setForeground(new java.awt.Color(255, 255, 00));
		jtfName = new JTextField(12);
		jtfName.setEditable(false);
		jtfName.setOpaque(false);
		jtfName.setFont(new Font("Dialog", 1, 14));
		jtfName.setForeground(new java.awt.Color(255, 255, 00));
		JLabel jlDate = new JLabel("Date��");
		jlDate.setOpaque(false);
		jlDate.setFont(new Font("Dialog", 1, 14));
		jlDate.setForeground(new java.awt.Color(255, 255, 00));
		jtfDate = new JTextField(7);
		jtfDate.setEditable(false);
		jtfDate.setOpaque(false);
		jtfDate.setFont(new Font("Dialog", 1, 14));
		jtfDate.setForeground(new java.awt.Color(255, 255, 00));
		JLabel jlTime = new JLabel("Time��");
		jlTime.setOpaque(false);
		jlTime.setFont(new Font("Dialog", 1, 14));
		jlTime.setForeground(new java.awt.Color(255, 255, 00));
		jtfTime = new JTextField(7);
		jtfTime.setEditable(false);
		jtfTime.setOpaque(false);
		jtfTime.setFont(new Font("Dialog", 1, 14));
		jtfTime.setForeground(new java.awt.Color(255, 255, 00));
		JLabel jlChannel = new JLabel("Channel��");
		jlChannel.setOpaque(false);
		jlChannel.setFont(new Font("Dialog", 1, 14));
		jlChannel.setForeground(new java.awt.Color(255, 255, 00));
		jtfChannel = new JTextField(7);
		jtfChannel.setEditable(false);
		jtfChannel.setOpaque(false);
		jtfChannel.setFont(new Font("Dialog", 1, 14));
		jtfChannel.setForeground(new java.awt.Color(255, 255, 00));
		JLabel jlYY = new JLabel("YY��");
		jlYY.setOpaque(false);
		jlYY.setFont(new Font("Dialog", 1, 14));
		jlYY.setForeground(new java.awt.Color(255, 255, 00));
		jtfYY = new JTextField(7);
		jtfYY.setEditable(false);
		jtfYY.setOpaque(false);
		jtfYY.setFont(new Font("Dialog", 1, 14));
		jtfYY.setForeground(new java.awt.Color(255, 255, 00));
		JLabel jlCommander = new JLabel("Commander��");
		jlCommander.setOpaque(false);
		jlCommander.setFont(new Font("Dialog", 1, 14));
		jlCommander.setForeground(new java.awt.Color(255, 255, 00));
		jtfCommander = new JTextField(12);
		jtfCommander.setEditable(false);
		jtfCommander.setOpaque(false);
		jtfCommander.setFont(new Font("Dialog", 1, 14));
		jtfCommander.setForeground(new java.awt.Color(255, 255, 00));

		jp_title.setOpaque(false);
		jp_title.add(jlName);
		jp_title.add(jtfName);
		jp_title.add(jlDate);
		jp_title.add(jtfDate);
		jp_title.add(jlTime);
		jp_title.add(jtfTime);
		jp_title.add(jlYY);
		jp_title.add(jtfYY);
		jp_title.add(jlChannel);
		jp_title.add(jtfChannel);
		jp_title.add(jlCommander);
		jp_title.add(jtfCommander);

		jp_top.setLayout(new GridLayout(3, 1));
		jp_top.setOpaque(false);
		jp_top.add(jp_null);
		jp_top.add(jp_button);
		jp_top.add(jp_title);

		// �²�
		// �ϱ�
		jp_table_top.setLayout(new FlowLayout());
		jp_table_top.setOpaque(false);
		// ���
		JPanel jp_Intercept1 = new JPanel();
		jp_Intercept1.setLayout(new BorderLayout());
		jp_Intercept1.setOpaque(false);
		background_Intercept = new ImageIcon("res/layout/Intercept.png");// ����ͼƬ
		JLabel jlIntercept1 = new JLabel(background_Intercept);
		jlIntercept1.setBounds(0, 0, background_Intercept.getIconWidth(), background_Intercept.getIconHeight());
		jp_Intercept1.add(jlIntercept1, BorderLayout.NORTH);

		model1 = new TableModel1();
		table1 = new JTable(model1) {
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		table1.setOpaque(false);
		table1.setRowHeight(rowHeight);
		table1.getTableHeader().setReorderingAllowed(false); // ���������ƶ�
		table1.getTableHeader().setResizingAllowed(false); // �����������
		model1.addRow("Team 1", blank, blank, blank, blank);
		model1.addRow("Team 2", blank, blank, blank, blank);
		model1.addRow("Team 3", blank, blank, blank, blank);
		model1.addRow("Team 4", blank, blank, blank, blank);
		model1.addRow("Team 5", blank, blank, blank, blank);
		table1.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table1.setEnabled(false);
		table1.setForeground(new java.awt.Color(0, 255, 255));
		table1.setFont(new Font("Dialog", 1, 15));
		table1.setPreferredScrollableViewportSize(new Dimension(jt_width, jt_height));
		table1.getColumnModel().getColumn(0).setPreferredWidth(40);

		jsp_table1 = new JScrollPane();
		jsp_table1.getViewport().setOpaque(false);// ��JScrollPane����Ϊ͸��
		jsp_table1.setOpaque(false);// ���м��viewport����Ϊ͸��
		jsp_table1.setViewportView(table1);// װ�ر��
		jsp_table1.setColumnHeaderView(table1.getTableHeader());// ����ͷ����HeaderView���֣�
		jsp_table1.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��

		/*
		 * ���������Ϊ͸�������ͬ���������������е������� ���������������Ϊ͸��Ҳû���ã�Ӧ�ý����е�������Ҳ����Ϊ͸��
		 * �������������ͨ��������Ⱦ����͸����ʵ��
		 */
		table1.setOpaque(false);
		DefaultTableCellRenderer render1 = new DefaultTableCellRenderer();
		render1.setOpaque(false); // ����Ⱦ������Ϊ͸��
		// �������Ⱦ�����õ�fileTable�
		// ���������û������ר�Ŷ�column���õ��������Ч
		// �����ĳ��column����ָ������Ⱦ������������column������������render��Ⱦ��
		// ���Ϊ�˱�֤͸����������column����ָ������Ⱦ������ô�ڶ������Ⱦ����ҲӦ������͸��
		table1.setDefaultRenderer(Object.class, render1);

		// ����ͷ��͸��
		// ͷ��ʵ����Ҳ��һ��JTABLE��ֻ��һ�ж��ѡ�
		JTableHeader header1 = table1.getTableHeader();// ��ȡͷ��
		header1.setOpaque(false);// ����ͷ��Ϊ͸��
		header1.getTable().setOpaque(false);// ����ͷ������ı��͸��
		header1.setPreferredSize(new Dimension(jt_width, 22));

		/*
		 * ͷ���ı��Ҳ��ǰ��ı������һ��������Ҫ������ĵ�Ԫ������Ϊ͸�� ���ͬ����Ҫ��ͷ����Ԫ�����͸�������ã����ﻹ������Ⱦ����
		 * �������и�������ǣ�����ͷ����Ⱦ��ֱ��������һ�����ã�����������û�к��� ��ˣ�������Ҫһ��ר�õ�ͷ����Ⱦ�����ֶ�������
		 */
		header1.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer1 = header1.getDefaultRenderer();
		if (headerRenderer1 instanceof JLabel) {
			((JLabel) headerRenderer1).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer1).setOpaque(false);
		}

		jsp_table1.setPreferredSize(new Dimension(jt_width, jt_height));

		jp_Intercept1.add(jsp_table1, BorderLayout.CENTER);

		// �м�
		JLabel jlTeam_center = new JLabel("  Team  ");
		jlTeam_center.setFont(new Font("Dialog", 1, 18));
		jlTeam_center.setForeground(Color.white);

		// �ұ�
		JPanel jp_Destroy1 = new JPanel();
		jp_Destroy1.setLayout(new BorderLayout());
		jp_Destroy1.setOpaque(false);
		background_Destroy = new ImageIcon("res/layout/Destroy.png");// ����ͼƬ
		JLabel jlDestroy1 = new JLabel(background_Destroy);
		jlDestroy1.setBounds(0, 0, background_Destroy.getIconWidth(), background_Destroy.getIconHeight());
		jp_Destroy1.add(jlDestroy1, BorderLayout.NORTH);

		model2 = new TableModel1();
		table2 = new JTable(model2) { // ����jtable�ĵ�Ԫ��Ϊ͸����
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		table2.setOpaque(false);
		table2.setRowHeight(rowHeight);
		table2.getTableHeader().setReorderingAllowed(false); // ���������ƶ�
		table2.getTableHeader().setResizingAllowed(false); // �����������
		model2.addRow("Team 1", blank, blank, blank, blank);
		model2.addRow("Team 2", blank, blank, blank, blank);
		model2.addRow("Team 3", blank, blank, blank, blank);
		model2.addRow("Team 4", blank, blank, blank, blank);
		model2.addRow("Team 5", blank, blank, blank, blank);
		table2.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table2.setEnabled(false);
		table2.setForeground(new java.awt.Color(0, 255, 255));
		table2.setFont(new Font("Dialog", 1, 15));
		table2.setPreferredScrollableViewportSize(new Dimension(jt_width, jt_height));
		table2.getColumnModel().getColumn(0).setPreferredWidth(40);

		jsp_table2 = new JScrollPane();
		jsp_table2.getViewport().setOpaque(false);// ��JScrollPane����Ϊ͸��
		jsp_table2.setOpaque(false);// ���м��viewport����Ϊ͸��
		jsp_table2.setViewportView(table2);// װ�ر��
		jsp_table2.setColumnHeaderView(table2.getTableHeader());// ����ͷ����HeaderView���֣�
		jsp_table2.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��

		/*
		 * ���������Ϊ͸�������ͬ���������������е������� ���������������Ϊ͸��Ҳû���ã�Ӧ�ý����е�������Ҳ����Ϊ͸��
		 * �������������ͨ��������Ⱦ����͸����ʵ��
		 */
		table2.setOpaque(false);
		DefaultTableCellRenderer render2 = new DefaultTableCellRenderer();
		render2.setOpaque(false); // ����Ⱦ������Ϊ͸��
		// �������Ⱦ�����õ�fileTable�
		// ���������û������ר�Ŷ�column���õ��������Ч
		// �����ĳ��column����ָ������Ⱦ������������column������������render��Ⱦ��
		// ���Ϊ�˱�֤͸����������column����ָ������Ⱦ������ô�ڶ������Ⱦ����ҲӦ������͸��
		table2.setDefaultRenderer(Object.class, render2);

		// ����ͷ��͸��
		// ͷ��ʵ����Ҳ��һ��JTABLE��ֻ��һ�ж��ѡ�
		JTableHeader header2 = table2.getTableHeader();// ��ȡͷ��
		header2.setOpaque(false);// ����ͷ��Ϊ͸��
		header2.getTable().setOpaque(false);// ����ͷ������ı��͸��
		header2.setPreferredSize(new Dimension(jt_width, 22));

		/*
		 * ͷ���ı��Ҳ��ǰ��ı������һ��������Ҫ������ĵ�Ԫ������Ϊ͸�� ���ͬ����Ҫ��ͷ����Ԫ�����͸�������ã����ﻹ������Ⱦ����
		 * �������и�������ǣ�����ͷ����Ⱦ��ֱ��������һ�����ã�����������û�к��� ��ˣ�������Ҫһ��ר�õ�ͷ����Ⱦ�����ֶ�������
		 */
		header2.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer2 = header2.getDefaultRenderer();
		if (headerRenderer2 instanceof JLabel) {
			((JLabel) headerRenderer2).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer2).setOpaque(false);
		}

		jsp_table2.setPreferredSize(new Dimension(jt_width, jt_height));

		jp_Destroy1.add(jsp_table2, BorderLayout.CENTER);

		jp_table_top.add(jp_Intercept1);
		jp_table_top.add(jlTeam_center);
		jp_table_top.add(jp_Destroy1);

		// �±�
		jp_table_buttom.setLayout(new FlowLayout());
		jp_table_buttom.setOpaque(false);
		// ���
		JPanel jp_Intercept2 = new JPanel();
		jp_Intercept2.setLayout(new BorderLayout());
		jp_Intercept2.setOpaque(false);
		JLabel jlIntercept2 = new JLabel(background_Intercept);
		jlIntercept2.setBounds(0, 0, background_Intercept.getIconWidth(), background_Intercept.getIconHeight());
		jp_Intercept2.add(jlIntercept2, BorderLayout.NORTH);

		model3 = new TableModel2();
		table3 = new JTable(model3) { // ����jtable�ĵ�Ԫ��Ϊ͸����
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		table3.setOpaque(false);
		table3.setRowHeight(rowHeight);
		table3.getTableHeader().setReorderingAllowed(false); // ���������ƶ�
		table3.getTableHeader().setResizingAllowed(false); // �����������
		model3.addRow("Team 1", blank, blank, blank);
		model3.addRow("Team 2", blank, blank, blank);
		model3.addRow("Team 3", blank, blank, blank);
		model3.addRow("Team 4", blank, blank, blank);
		model3.addRow("Team 5", blank, blank, blank);
		table3.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table3.setEnabled(false);
		table3.setForeground(new java.awt.Color(255, 255, 00));
		table3.setFont(new Font("Dialog", 1, 16));
		table3.setPreferredScrollableViewportSize(new Dimension(jt_width, jt_height));
		table3.getColumnModel().getColumn(0).setPreferredWidth(8);

		jsp_table3 = new JScrollPane();
		jsp_table3.getViewport().setOpaque(false);// ��JScrollPane����Ϊ͸��
		jsp_table3.setOpaque(false);// ���м��viewport����Ϊ͸��
		jsp_table3.setViewportView(table3);// װ�ر��
		jsp_table3.setColumnHeaderView(table3.getTableHeader());// ����ͷ����HeaderView���֣�
		jsp_table3.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��

		/*
		 * ���������Ϊ͸�������ͬ���������������е������� ���������������Ϊ͸��Ҳû���ã�Ӧ�ý����е�������Ҳ����Ϊ͸��
		 * �������������ͨ��������Ⱦ����͸����ʵ��
		 */
		table3.setOpaque(false);
		DefaultTableCellRenderer render3 = new DefaultTableCellRenderer();
		render3.setOpaque(false); // ����Ⱦ������Ϊ͸��
		// �������Ⱦ�����õ�fileTable�
		// ���������û������ר�Ŷ�column���õ��������Ч
		// �����ĳ��column����ָ������Ⱦ������������column������������render��Ⱦ��
		// ���Ϊ�˱�֤͸����������column����ָ������Ⱦ������ô�ڶ������Ⱦ����ҲӦ������͸��
		table3.setDefaultRenderer(Object.class, render3);

		// ����ͷ��͸��
		// ͷ��ʵ����Ҳ��һ��JTABLE��ֻ��һ�ж��ѡ�
		JTableHeader header3 = table3.getTableHeader();// ��ȡͷ��
		header3.setOpaque(false);// ����ͷ��Ϊ͸��
		header3.getTable().setOpaque(false);// ����ͷ������ı��͸��
		header3.setPreferredSize(new Dimension(jt_width, 22));

		/*
		 * ͷ���ı��Ҳ��ǰ��ı������һ��������Ҫ������ĵ�Ԫ������Ϊ͸�� ���ͬ����Ҫ��ͷ����Ԫ�����͸�������ã����ﻹ������Ⱦ����
		 * �������и�������ǣ�����ͷ����Ⱦ��ֱ��������һ�����ã�����������û�к��� ��ˣ�������Ҫһ��ר�õ�ͷ����Ⱦ�����ֶ�������
		 */
		header3.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer3 = header3.getDefaultRenderer();
		if (headerRenderer3 instanceof JLabel) {
			((JLabel) headerRenderer3).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer3).setOpaque(false);
		}

		jsp_table3.setPreferredSize(new Dimension(jt_width, jt_height));

		jp_Intercept2.add(jsp_table3, BorderLayout.CENTER);

		// �м�
		JLabel jlMission_center = new JLabel("Mission");
		jlMission_center.setFont(new Font("Dialog", 1, 18));
		jlMission_center.setForeground(Color.white);

		// �ұ�
		JPanel jp_Destroy2 = new JPanel();
		jp_Destroy2.setLayout(new BorderLayout());
		jp_Destroy2.setOpaque(false);
		JLabel jlDestroy2 = new JLabel(background_Destroy);
		jlDestroy2.setBounds(0, 0, background_Destroy.getIconWidth(), background_Destroy.getIconHeight());
		jp_Destroy2.add(jlDestroy2, BorderLayout.NORTH);

		model4 = new TableModel2();
		table4 = new JTable(model4) { // ����jtable�ĵ�Ԫ��Ϊ͸����
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component c = super.prepareRenderer(renderer, row, column);
				if (c instanceof JComponent) {
					((JComponent) c).setOpaque(false);
				}
				return c;
			}
		};
		table4.setOpaque(false);
		table4.setRowHeight(rowHeight);
		table4.getTableHeader().setReorderingAllowed(false); // ���������ƶ�
		table4.getTableHeader().setResizingAllowed(false); // �����������
		model4.addRow("Team 1", blank, blank, blank);
		model4.addRow("Team 2", blank, blank, blank);
		model4.addRow("Team 3", blank, blank, blank);
		model4.addRow("Team 4", blank, blank, blank);
		model4.addRow("Team 5", blank, blank, blank);
		table4.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table4.setEnabled(false);
		table4.setForeground(new java.awt.Color(255, 255, 00));
		table4.setFont(new Font("Dialog", 1, 16));
		table4.setPreferredScrollableViewportSize(new Dimension(jt_width, jt_height));
		table4.getColumnModel().getColumn(0).setPreferredWidth(8);

		jsp_table4 = new JScrollPane();
		jsp_table4.getViewport().setOpaque(false);// ��JScrollPane����Ϊ͸��
		jsp_table4.setOpaque(false);// ���м��viewport����Ϊ͸��
		jsp_table4.setViewportView(table4);// װ�ر��
		jsp_table4.setColumnHeaderView(table4.getTableHeader());// ����ͷ����HeaderView���֣�
		jsp_table4.getColumnHeader().setOpaque(false);// ��ȡ��ͷ����������Ϊ͸��

		/*
		 * ���������Ϊ͸�������ͬ���������������е������� ���������������Ϊ͸��Ҳû���ã�Ӧ�ý����е�������Ҳ����Ϊ͸��
		 * �������������ͨ��������Ⱦ����͸����ʵ��
		 */
		table4.setOpaque(false);
		DefaultTableCellRenderer render4 = new DefaultTableCellRenderer();
		render4.setOpaque(false); // ����Ⱦ������Ϊ͸��
		// �������Ⱦ�����õ�fileTable�
		// ���������û������ר�Ŷ�column���õ��������Ч
		// �����ĳ��column����ָ������Ⱦ������������column������������render��Ⱦ��
		// ���Ϊ�˱�֤͸����������column����ָ������Ⱦ������ô�ڶ������Ⱦ����ҲӦ������͸��
		table3.setDefaultRenderer(Object.class, render4);

		// ����ͷ��͸��
		// ͷ��ʵ����Ҳ��һ��JTABLE��ֻ��һ�ж��ѡ�
		JTableHeader header4 = table4.getTableHeader();// ��ȡͷ��
		header4.setOpaque(false);// ����ͷ��Ϊ͸��
		header4.getTable().setOpaque(false);// ����ͷ������ı��͸��
		header4.setPreferredSize(new Dimension(jt_width, 22));

		/*
		 * ͷ���ı��Ҳ��ǰ��ı������һ��������Ҫ������ĵ�Ԫ������Ϊ͸�� ���ͬ����Ҫ��ͷ����Ԫ�����͸�������ã����ﻹ������Ⱦ����
		 * �������и�������ǣ�����ͷ����Ⱦ��ֱ��������һ�����ã�����������û�к��� ��ˣ�������Ҫһ��ר�õ�ͷ����Ⱦ�����ֶ�������
		 */
		header4.setDefaultRenderer(new HeaderCellRenderer());
		TableCellRenderer headerRenderer4 = header4.getDefaultRenderer();
		if (headerRenderer4 instanceof JLabel) {
			((JLabel) headerRenderer4).setHorizontalAlignment(JLabel.CENTER);
			((JLabel) headerRenderer4).setOpaque(false);
		}

		jsp_table4.setPreferredSize(new Dimension(jt_width, jt_height));

		jp_Destroy2.add(jsp_table4, BorderLayout.CENTER);

		jp_table_buttom.add(jp_Intercept2);
		jp_table_buttom.add(jlMission_center);
		jp_table_buttom.add(jp_Destroy2);

		jp_table = new JPanel();
		jp_table.setLayout(new GridLayout(2, 1));
		jp_table.setOpaque(false);
		jp_table.add(jp_table_top);
		jp_table.add(jp_table_buttom);

		Image image = new ImageIcon("res/layout/background.png").getImage();
		JPanel_Background jp_all = new JPanel_Background(image);
		jp_all.setLayout(new BorderLayout());
		jp_all.add(jp_top, BorderLayout.NORTH);
		jp_all.add(jp_table, BorderLayout.CENTER);

		jf.add(jp_all, BorderLayout.CENTER);

		jf.setResizable(false);
		jf.setVisible(true);
		jf.setPreferredSize(new Dimension(jf_width, jf_height));
		jf.setLocation(0, 0);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.pack();
	}

	public void openData() {
		String Open_path = "res/" + username + "/TeamList/" + jcb_list.getSelectedItem().toString() + ".md";
		File file = new File(Open_path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			JOptionPane.showMessageDialog(jf, // ���Ϊnull���˿����ʾ�����룬Ϊjf����ʾΪjf������
					"File doesn't exit !", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			openFile(file);
		}
	}

	public void openFile(File file) {
		FileReader fr = null;
		try {
			fr = new FileReader(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader br = new BufferedReader(fr);
		String tempString = new String("");
		String[] tempStringtable;
		try {
			while ((tempString = br.readLine()) != null) {
				if (tempString.indexOf("<Name>") == 0) {
					jtfName.setText(tempString.substring(tempString.indexOf("<Name>") + "<Name>".length(),
							tempString.lastIndexOf("</Name>")));
				} else if (tempString.indexOf("<Date>") == 0) {
					jtfDate.setText(tempString.substring(tempString.indexOf("<Date>") + "<Date>".length(),
							tempString.lastIndexOf("</Date>")));
				} else if (tempString.indexOf("<Time>") == 0) {
					jtfTime.setText(tempString.substring(tempString.indexOf("<Time>") + "<Time>".length(),
							tempString.lastIndexOf("</Time>")));
				} else if (tempString.indexOf("<Channel>") == 0) {
					jtfChannel.setText(tempString.substring(tempString.indexOf("<Channel>") + "<Channel>".length(),
							tempString.lastIndexOf("</Channel>")));
				} else if (tempString.indexOf("<YY>") == 0) {
					jtfYY.setText(tempString.substring(tempString.indexOf("<YY>") + "<YY>".length(),
							tempString.lastIndexOf("</YY>")));
				} else if (tempString.indexOf("<Commander>") == 0) {
					jtfCommander
							.setText(tempString.substring(tempString.indexOf("<Commander>") + "<Commander>".length(),
									tempString.lastIndexOf("</Commander>")));
				} else if (tempString.indexOf("<table1>") == 0) {
					for (int i = 0; i < 5; i++) {
						tempString = br.readLine();
						if (!tempString.equals("</table1>")) {
							tempStringtable = tempString.split("\t");
							for (int j = 0; j < tempStringtable.length; j++) {
								table1.setValueAt(tempStringtable[j], i, j + 1);
							}
						}
					}
				} else if (tempString.indexOf("<table2>") == 0) {
					for (int i = 0; i < 5; i++) {
						tempString = br.readLine();
						if (!tempString.equals("</table2>")) {
							tempStringtable = tempString.split("\t");
							for (int j = 0; j < tempStringtable.length; j++) {
								table2.setValueAt(tempStringtable[j], i, j + 1);
							}
						}
					}
				} else if (tempString.indexOf("<table3>") == 0) {
					for (int i = 0; i < 5; i++) {
						tempString = br.readLine();
						if (!tempString.equals("</table3>")) {
							tempStringtable = tempString.split("\t");
							for (int j = 0; j < tempStringtable.length; j++) {
								table3.setValueAt(tempStringtable[j], i, j + 1);
							}
						}
					}
				} else if (tempString.indexOf("<table4>") == 0) {
					for (int i = 0; i < 5; i++) {
						tempString = br.readLine();
						if (!tempString.equals("</table4>")) {
							tempStringtable = tempString.split("\t");
							for (int j = 0; j < tempStringtable.length; j++) {
								table4.setValueAt(tempStringtable[j], i, j + 1);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			fr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteData() {
		String Delete_path = "res/" + username + "/TeamList/" + jtfName.getText() + "_" + jtfDate.getText() + "_"
				+ jtfTime.getText() + ".md";
		File file = new File(Delete_path);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (!file.exists()) {
			JOptionPane.showMessageDialog(jf, // ���Ϊnull���˿����ʾ�����룬Ϊjf����ʾΪjf������
					"File doesn't exit !", "Error", JOptionPane.ERROR_MESSAGE);
		} else {
			int a = JOptionPane.showConfirmDialog(jf, // ���Ϊnull���˿����ʾ�����룬Ϊjf����ʾΪjf������
					"Are you sure to delete current task ?", "Delete", JOptionPane.OK_CANCEL_OPTION);
			if (a == JOptionPane.OK_OPTION) {
				System.out.println("Delete : " + Delete_path);
				file.delete();
				jf.dispose();
				new checkList(username);
			} else if (a == JOptionPane.CANCEL_OPTION) {

			}
		}
	}

	public void upload() {
		JFileChooser jfc = new JFileChooser();
		jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		jfc.showDialog(new JLabel(), "�ϴ�");
		File srcFile = null;
		srcFile = jfc.getSelectedFile();
		destFileName = username + "/ScreenShot/" + jtfName.getText() + "_" + jtfDate.getText() + "_"
				+ jtfTime.getText();

		File destFold = new File("res/" + username + "/ScreenShot");
		if (!destFold.exists()) {
			destFold.mkdirs();
		}
		try {
			if (srcFile.exists()) {
				File destFile = new File("res/" + destFileName + ".jpg");
				copyFile(srcFile, destFile);
			}
		} catch (NullPointerException e) {

		}
	}

	public void copyFile(File srcFile, File destFile) {
		if (!destFile.exists()) {
			try {
				destFile.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// �����ļ�
			int byteread = 0; // ��ȡ���ֽ���
			InputStream in = null;
			OutputStream out = null;
			try {
				in = new FileInputStream(srcFile);
				out = new FileOutputStream(destFile);
				byte[] buffer = new byte[1024];

				while ((byteread = in.read(buffer)) != -1) {
					out.write(buffer, 0, byteread);
				}
				JOptionPane.showConfirmDialog(jf, "Upload successfully !", "Result", JOptionPane.OK_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				fileIndex = 0;
			} catch (FileNotFoundException e) {
				JOptionPane.showConfirmDialog(jf, "File not found !", "Result", JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showConfirmDialog(jf, "Upload failed !", "Result", JOptionPane.OK_OPTION,
						JOptionPane.ERROR_MESSAGE);
			} finally {
				try {
					if (out != null)
						out.close();
					if (in != null)
						in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (destFile.exists()) {
			fileIndex++;
			destFileName += "(" + fileIndex + ")";
			File destFile2 = new File("res/" + destFileName + ".jpg");
			copyFile(srcFile, destFile2);
		}
	}
}
