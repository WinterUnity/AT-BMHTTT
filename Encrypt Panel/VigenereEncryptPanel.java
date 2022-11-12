package EncryptPanel;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class VigenereEncryptPanel extends JPanel {
	JPanel top, mid, bot, subPanel;

	public VigenereEncryptPanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5,1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Vigenere"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Plain Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptPanel.add(ptLabel);
		ptPanel.add(ptTextField);

		top.add(ptPanel);

		// Key
		JPanel keyPanel = new JPanel();
		keyPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyLabel = new JLabel("Key");
		JTextField keyTextField = new JTextField(30);
		keyTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyPanel.add(keyLabel);
		keyPanel.add(keyTextField);

		top.add(keyPanel);

		/*
		 * Mid Layout
		 */
		mid = new JPanel();
		mid.setLayout(new FlowLayout());
		Border midBD = BorderFactory.createLineBorder(Color.BLUE);
		mid.setBorder(BorderFactory.createTitledBorder(midBD, "Thao tác"));

		// Choose file
		Button chooseFile = new Button("Chọn File");
		mid.add(chooseFile);

		// Create key
		Button createKey = new Button("Tạo khóa");
		mid.add(createKey);

		// Encrypt
		Button encrypt = new Button("Mã hóa");
		mid.add(encrypt);
		
		/*
		 * Bot Layout
		 */
		bot = new JPanel();
		bot.setLayout(new BorderLayout());
		Border botBD = BorderFactory.createLineBorder(Color.gray);
		bot.setBorder(botBD);
		
		JTextArea txtArea = new JTextArea(10, 800);
		txtArea.append("Result");
//		txtArea.setText(txtArea.getText() + "STT\t\tTen mon hoc\t\t\t\tDiem\n");
		bot.add(txtArea);
		
		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bot.add(scrollPane, BorderLayout.SOUTH);
		
		/*
		 * Main EncryptPanel
		 */
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(mid, BorderLayout.SOUTH);
		subPanel.add(top, BorderLayout.CENTER);
		add(subPanel);
		
		add(bot, BorderLayout.SOUTH);
	}
}
