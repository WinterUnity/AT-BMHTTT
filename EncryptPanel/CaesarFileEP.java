package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;

import EncryptMethod.Caesar;

public class CaesarFileEP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, destDir;
	File currentDir = new File("D:\\");
	int caesarKey;
	boolean fileUploaded, keyCreated;

	public CaesarFileEP() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Caesar - File"));

		// PlainText
		JPanel ptPanel = new JPanel();
		ptPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

		JLabel ptLabel = new JLabel("File");
		JTextField ptTextField = new JTextField(30);
		ptTextField.setHorizontalAlignment(SwingConstants.RIGHT);
		ptTextField.setEditable(false);
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
		bot.add(txtArea);

		JScrollPane scrollPane = new JScrollPane(txtArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
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

		/*
		 * Handle event
		 */
		ActionListener buttonListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Upload File
				if (e.getActionCommand().equals("Choose File")) {
					JFileChooser fileChooser = new JFileChooser(currentDir);
					int userChoice = fileChooser.showOpenDialog(CaesarFileEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						currentDir = fileChooser.getCurrentDirectory();
						
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}

				// Create key
				if (e.getActionCommand().equals("Create key")) {
					Caesar caesar = new Caesar();
					caesarKey = caesar.createKey();
					keyTextField.setText(String.valueOf(caesarKey));
					keyCreated = true;
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if (ptTextField.getText().isBlank() || keyTextField.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded != true) {
							JOptionPane.showMessageDialog(null, "Source file not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JFileChooser dirChooser = new JFileChooser(currentDir);
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(CaesarFileEP.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();
								currentDir = dirChooser.getCurrentDirectory();

								int key = 0;

								// Manual or not
								if (keyCreated == true) {
									key = caesarKey;
									keyCreated = false;
								} else {
									key = Integer.parseInt(keyTextField.getText());
								}

								// Encrypting
								Caesar caesar = new Caesar();
								try {
									caesar.encryptFile(srcFile, key, destDir);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("\nFile encrypted success fully");

									// Print key file
									File destFile = new File(destDir.getAbsolutePath() + "\\CaesarFileKey.txt");
									FileWriter fw = new FileWriter(destFile);
									PrintWriter pw = new PrintWriter(fw);
									pw.println(String.valueOf(key));
									pw.close();
									fw.close();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
					}
				}
			}
		};

		chooseFile.addActionListener(buttonListener);
		createKey.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		createKey.setActionCommand("Create key");
		encrypt.setActionCommand("Encrypt");
	}
}