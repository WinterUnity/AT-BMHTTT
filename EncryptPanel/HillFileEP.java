package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import java.io.*;

import EncryptMethod.Hill;

public class HillFileEP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, destDir;
	File currentDir = new File("D:\\");
	int[][] matrixKey = new int[2][2];
	boolean fileUploaded, keyCreated;

	public HillFileEP() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(4, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Hill - File"));

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
		keyPanel.add(keyLabel);
		
		JPanel subKeyPanel = new JPanel();
		subKeyPanel.setLayout(new GridLayout(2,2));
		JTextField keyTextField1 = new JTextField(15);
		keyTextField1.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField2 = new JTextField(15);
		keyTextField2.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField3 = new JTextField(15);
		keyTextField3.setHorizontalAlignment(SwingConstants.CENTER);
		JTextField keyTextField4 = new JTextField(15);
		keyTextField4.setHorizontalAlignment(SwingConstants.CENTER);
		subKeyPanel.add(keyTextField1);
		subKeyPanel.add(keyTextField2);
		subKeyPanel.add(keyTextField3);
		subKeyPanel.add(keyTextField4);
		
		keyPanel.add(subKeyPanel);

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
					int userChoice = fileChooser.showOpenDialog(HillFileEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						currentDir = fileChooser.getCurrentDirectory();
						
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}

				// Create key
				if(e.getActionCommand().equals("Create key")) {
					Hill hill = new Hill();
					matrixKey = hill.createKey();
					keyTextField1.setText(String.valueOf(matrixKey[0][0]));
					keyTextField2.setText(String.valueOf(matrixKey[0][1]));
					keyTextField3.setText(String.valueOf(matrixKey[1][0]));
					keyTextField4.setText(String.valueOf(matrixKey[1][1]));
					keyCreated = true;
				}

				// Encryption
				if (e.getActionCommand().equals("Encrypt")) {
					if(ptTextField.getText().isBlank() ||
							keyTextField1.getText().isBlank() || keyTextField2.getText().isBlank()
							|| keyTextField3.getText().isBlank() || keyTextField4.getText().isBlank()) {
						JOptionPane.showMessageDialog(null, "There is no plain text or key", "Error", JOptionPane.ERROR_MESSAGE);
					} else {
						if (fileUploaded != true) {
							JOptionPane.showMessageDialog(null, "Source file not uploaded", "Error",
									JOptionPane.ERROR_MESSAGE);
						} else {
							JFileChooser dirChooser = new JFileChooser(currentDir);
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(HillFileEP.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();
								currentDir = dirChooser.getCurrentDirectory();

								int[][] key = new int[2][2];

								// Manual or not
								if (keyCreated == true) {
									key = matrixKey;
									keyCreated = false;
								} else {
									key[0][0] = Integer.parseInt(keyTextField1.getText());
									key[0][1] = Integer.parseInt(keyTextField2.getText());
									key[1][0] = Integer.parseInt(keyTextField3.getText());
									key[1][1] = Integer.parseInt(keyTextField4.getText());
								}

								// Encrypting
								Hill hill = new Hill();
								try {
									hill.encryptFile(srcFile, key, destDir);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("\nFile encrypted success fully");

									//Print key file
									File destFile = new File(destDir.getAbsolutePath() + "\\HillFileKey.txt");
									FileWriter fw = new FileWriter(destFile);
									PrintWriter pw = new PrintWriter(fw);
									pw.println("[[" + key[0][0] + ", " + key[0][1] + "], " + 
												"[" + key[1][0] + ", " + key[1][1] + "]]");
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