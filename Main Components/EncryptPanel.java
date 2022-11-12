package Main;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.*;

public class EncryptPanel extends JPanel{
	JPanel top, mid, bot, subPanel;
	
	public EncryptPanel() {
		setLayout(new BorderLayout());
		
		top = new TopPanel();
		
		mid = new MiddlePanel();
		
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(mid, BorderLayout.SOUTH);
		subPanel.add(top, BorderLayout.CENTER);
		add(subPanel);
		
		bot = new BotPanel();
		add(bot, BorderLayout.SOUTH);
	}
	
	class TopPanel extends JPanel{
		public TopPanel() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			Border topBD = BorderFactory.createLineBorder(Color.blue);
			setBorder(BorderFactory.createTitledBorder(topBD, "Them mon hoc"));
			
			//MSSV
			JPanel mssvPanel = new JPanel();
			mssvPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JLabel mssvLabel = new JLabel("MSSV");
			JTextField mssvTxtField = new JTextField(30);
			mssvPanel.add(mssvLabel);
			mssvPanel.add(mssvTxtField);
			
			add(mssvPanel);
			
			//Name
			JPanel namePanel = new JPanel();
			namePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JLabel nameLabel = new JLabel("Name");
			JTextField nameTxtField = new JTextField(30);
			nameTxtField.setEditable(false);
			namePanel.add(nameLabel);
			namePanel.add(nameTxtField);
			
			add(namePanel);
			
			//Subject ID
			JPanel sjIDPanel = new JPanel();
			sjIDPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JLabel sjIDLabel = new JLabel("Subject ID");
			JTextField sjIDTxtField = new JTextField(30);
			sjIDPanel.add(sjIDLabel);
			sjIDPanel.add(sjIDTxtField);
			
			add(sjIDPanel);
			
			//Subject name
			JPanel sjNamePanel = new JPanel();
			sjNamePanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
			
			JLabel sjNameLabel = new JLabel("Subject Name");
			JTextField sjNameTxtField = new JTextField(30);
			sjNamePanel.add(sjNameLabel);
			sjNamePanel.add(sjNameTxtField);
			
			add(sjNamePanel);
		}
	}
	
	class MiddlePanel extends JPanel{
		public MiddlePanel() {
			setLayout(new FlowLayout());
			Border midBD = BorderFactory.createLineBorder(Color.BLUE);
			setBorder(BorderFactory.createTitledBorder(midBD, "Thao tac"));
			
			//Add subject
			Button addSJ = new Button("Them mon hoc");
			add(addSJ);
			
			//Find student
			Button findST = new Button("Tim Sinh Vien");
			add(findST);
		}
	}
	
	class BotPanel extends JPanel{
		public BotPanel() {
			setLayout(new BorderLayout());
			Border botBD = BorderFactory.createLineBorder(Color.gray);
			setBorder(botBD);
			
			JTextArea txtArea = new JTextArea(10, 800);
			txtArea.append("STT\t\tTen mon hoc\t\t\t\tDiem\n");
			txtArea.setText(txtArea.getText() + "STT\t\tTen mon hoc\t\t\t\tDiem\n");
			add(txtArea);
			
			JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
					, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			add(scrollPane, BorderLayout.SOUTH);
		}
	}
}