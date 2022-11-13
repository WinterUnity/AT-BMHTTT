package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.VigenereFile;

public class VigenereEncryptFilePanel extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, keyFile, destDir;
	int[] vigenereKey;
	boolean fileUploaded, keyUploaded, keyCreated;

	public VigenereEncryptFilePanel() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
		Border topBD = BorderFactory.createLineBorder(Color.blue);
		top.setBorder(BorderFactory.createTitledBorder(topBD, "Thông tin - Mã hóa Vigenere - File"));

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
		Button uploadkey = new Button("Upload khóa");
		mid.add(uploadkey);

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
					JFileChooser fileChooser = new JFileChooser("D:\\");
					int userChoice = fileChooser.showOpenDialog(VigenereEncryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}

				// Upload Key
				if (e.getActionCommand().equals("Upload Key")) {
					JFileChooser keyFileChooser = new JFileChooser("D:\\");
					int userChoice = keyFileChooser.showOpenDialog(VigenereEncryptFilePanel.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();

						VigenereFile vigenereFile = new VigenereFile();

						try {
							FileReader fr = new FileReader(keyFile);
							BufferedReader br = new BufferedReader(fr);
							String keyString = br.readLine();
							if (keyString.contains("\\[")) {
								if (keyString.contains("]")) {
									vigenereKey = vigenereFile.readKeyArray(keyFile);
								}
							} else {
								vigenereKey = vigenereFile.readKeyString(keyFile);
							}
							keyTextField.setText(keyString);
							br.close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}

						keyUploaded = true;
					}
				}

				// Create key
				if (e.getActionCommand().equals("Create key")) {
					VigenereFile vigenereFile = new VigenereFile();
					vigenereKey = vigenereFile.createKeyBaseOnSize(10);
					String keyString = "[";
					for (int i = 0; i < vigenereKey.length; i++) {
						if (i == vigenereKey.length - 1) {
							keyString += vigenereKey[i] + "]";
						} else {
							keyString += vigenereKey[i] + ", ";
						}
					}
					keyTextField.setText(keyString);
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
							JFileChooser dirChooser = new JFileChooser("D:\\");
							dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							int userChoice = dirChooser.showSaveDialog(VigenereEncryptFilePanel.this);
							if (userChoice == JFileChooser.APPROVE_OPTION) {
								destDir = dirChooser.getSelectedFile();

								VigenereFile vigenereFile = new VigenereFile();
								int[] key;

								// Manual or not
								if (keyCreated == true || keyUploaded == true) {
									key = vigenereKey;
								} else {
									String keyString = keyTextField.getText();
									if (vigenereFile.isNumeric(keyString) == true) {
										key = vigenereFile.createKeyBaseOnSize(Integer.parseInt(keyString));
									} else {
										key = vigenereFile.createKeyBaseOnKeyWord(keyString);
									}
								}

								// Encrypting
								try {
									vigenereFile.encrypt(srcFile, key, destDir);

									// Show result to Text Area
									txtArea.setText("Result");
									txtArea.append("\nFile encrypted successfully");

									// Print key file
									String keyPrint = "";
									for (int i = 0; i < vigenereKey.length; i++) {
										if (i == vigenereKey.length - 1) {
											keyPrint += vigenereKey[i] + "]";
										} else {
											if (i == 0) {
												keyPrint += "[" + vigenereKey[0] + ", ";
											} else {
												keyPrint += vigenereKey[i] + ", ";
											}
										}
									}
									File destFile = new File(destDir.getAbsolutePath() + "\\VigenereFileKey.txt");
									FileWriter fw = new FileWriter(destFile);
									PrintWriter pw = new PrintWriter(fw);
									pw.println(keyPrint);
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
		uploadkey.addActionListener(buttonListener);
		createKey.addActionListener(buttonListener);
		encrypt.addActionListener(buttonListener);

		chooseFile.setActionCommand("Choose File");
		uploadkey.setActionCommand("Upload Key");
		createKey.setActionCommand("Create key");
		encrypt.setActionCommand("Encrypt");
	}
}
