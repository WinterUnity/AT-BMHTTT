package EncryptPanel;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.Border;

import EncryptMethod.Vigenere;

public class VigenereEP extends JPanel {
	JPanel top, mid, bot, subPanel;
	File srcFile, keyFile, destDir;
	File currentDir = new File("D:\\");
	int[] vigenereKey;
	boolean fileUploaded, keyUploaded, keyCreated;

	public VigenereEP() {
		setLayout(new BorderLayout());

		/*
		 * Top Layout
		 */
		top = new JPanel();
		top.setLayout(new GridLayout(5, 1));
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
					JFileChooser fileChooser = new JFileChooser(currentDir);
					int userChoice = fileChooser.showOpenDialog(VigenereEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						srcFile = fileChooser.getSelectedFile();
						currentDir = fileChooser.getCurrentDirectory();
						
						ptTextField.setText(srcFile.getName());
						fileUploaded = true;
					}
				}
				
				// Upload Key
				if (e.getActionCommand().equals("Upload Key")) {
					JFileChooser keyFileChooser = new JFileChooser(currentDir);
					int userChoice = keyFileChooser.showOpenDialog(VigenereEP.this);
					if (userChoice == JFileChooser.APPROVE_OPTION) {
						keyFile = keyFileChooser.getSelectedFile();
						currentDir = keyFileChooser.getCurrentDirectory();
						
						Vigenere vigenere = new Vigenere();
						
						try {
							FileReader fr = new FileReader(keyFile);
							BufferedReader br = new BufferedReader(fr);
							String keyString = br.readLine();
							if(keyString.contains("[")) {
								if(keyString.contains("]")) {
									vigenereKey = vigenere.readKeyArray(keyFile);
								}
							} else {
								vigenereKey = vigenere.readKeyString(keyFile);
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
					Vigenere vigenere = new Vigenere();
					vigenereKey = vigenere.createKeyBaseOnSize(10);
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
						JFileChooser dirChooser = new JFileChooser(currentDir);
						dirChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						int userChoice = dirChooser.showSaveDialog(VigenereEP.this);
						if (userChoice == JFileChooser.APPROVE_OPTION) {
							destDir = dirChooser.getSelectedFile();
							currentDir = dirChooser.getCurrentDirectory();

							Vigenere vigenere = new Vigenere();
							String text = "";
							int[] key;

							// Manual or not
							if (fileUploaded == true) {
								text = srcFile.getAbsolutePath();
								fileUploaded = false;
							} else {
								text = ptTextField.getText();
							}

							if (keyCreated == true || keyUploaded == true) {
								key = vigenereKey;
								keyCreated = false;
								keyUploaded = false;
							} else {
								String keyString = keyTextField.getText();
								if(vigenere.isNumeric(keyString) == true) {
									key = vigenere.createKeyBaseOnSize(Integer.parseInt(keyString));
								} else {
									key = vigenere.createKeyBaseOnKeyWord(keyString);
								}
							}

							// Encrypting
							try {
								vigenere.encrypt(text, key, destDir);

								// Show result to Text Area
								txtArea.setText("Result");
								txtArea.append("\n" + vigenere.getEncryptedString());

								// Print key file
								String keyPrint = "";
								for (int i = 0; i < key.length; i++) {
									if (i == key.length - 1) {
										keyPrint += key[i] + "]";
									} else {
										if(i == 0) {
											keyPrint += "[" + key[0] + ", ";
										} else {
											keyPrint += key[i] + ", ";
										}
									}
								}
								File destFile = new File(destDir.getAbsolutePath() + "\\VigenereKey.txt");
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
