package DecryptPanel;

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

public class AffineDecryptPanel extends JPanel {
	JPanel top, mid, bot, subPanel;

	public AffineDecryptPanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Giải mã Affine"));

		// CipherText
		JPanel ctPanel = new JPanel();
		ctPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("Cipher Text");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ctPanel.add(ptLabel);
		ctPanel.add(ptTextField);

		top.add(ctPanel);

		// KeyA
		JPanel keyAPanel = new JPanel();
		keyAPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyALabel = new JLabel("KeyA");
		JTextField keyATextField = new JTextField(30);
		keyATextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyAPanel.add(keyALabel);
		keyAPanel.add(keyATextField);

		top.add(keyAPanel);

		// KeyB
		JPanel keyBPanel = new JPanel();
		keyBPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel keyBLabel = new JLabel("KeyB");
		JTextField keyBTextField = new JTextField(30);
		keyBTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		keyBPanel.add(keyBLabel);
		keyBPanel.add(keyBTextField);

		top.add(keyBPanel);

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
		Button loadKey = new Button("Load khóa");
		mid.add(loadKey);

		// Decrypt
		Button decrypt = new Button("Giải mã");
		mid.add(decrypt);

		/*
		 * Bot Layout
		 */
		bot = new JPanel();
		bot.setLayout(new BorderLayout());
		Border botBD = BorderFactory.createLineBorder(Color.gray);
		bot.setBorder(botBD);

		JTextArea txtArea = new JTextArea(10, 800);
		txtArea.append("Result");
		bot.add(txtArea);

		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		bot.add(scrollPane, BorderLayout.SOUTH);

		/*
		 * Main DecryptPanel
		 */
		subPanel = new JPanel();
		subPanel.setLayout(new BorderLayout());
		subPanel.add(mid, BorderLayout.SOUTH);
		subPanel.add(top, BorderLayout.CENTER);
		add(subPanel);

		add(bot, BorderLayout.SOUTH);
	}
}
