package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyFrame extends JFrame {
	public MyFrame() {

		// Frame
		setTitle("Tool mã hóa code");
		setSize(800, 500);
		MyPanel myPanel = new MyPanel();

		setJMenuBar(createMenuBar(myPanel));
		getContentPane().add(myPanel);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
	}

	public JMenuBar createMenuBar(MyPanel myPanel) {
		// MenuBar
		JMenuBar menuBar = new JMenuBar();

		ActionListener itemAction = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				myPanel.change(e.getActionCommand());
			}
		};

		/*
		 * File menu
		 */
		JMenu fileMenu = new JMenu("File");
		fileMenu.setFont(new Font(null, Font.BOLD, 12));
		menuBar.add(fileMenu);

		// Default menu item
		JMenuItem defaultItem = new JMenuItem("Home");
		defaultItem.setMnemonic(KeyEvent.VK_H);
		defaultItem.setActionCommand("Home");
		defaultItem.addActionListener(itemAction);
		fileMenu.add(defaultItem);

		// Exit menu item
		JMenuItem exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_E);
		exitItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);

		/*
		 * Encrypt Menu
		 */
		JMenu encryptMenu = new JMenu("Mã hóa");
		menuBar.add(encryptMenu);

		// Caesar encrypt menu item
		JMenuItem caesarEncryptItem = new JMenuItem("Mã hóa Caesar");
		caesarEncryptItem.setMnemonic(KeyEvent.VK_C);
		caesarEncryptItem.setActionCommand("CaesarEncrypt");
		caesarEncryptItem.addActionListener(itemAction);
		encryptMenu.add(caesarEncryptItem);

		// Substitution encrypt menu item
		JMenuItem subEncrptItem = new JMenuItem("Mã hóa Substitution");
		subEncrptItem.setMnemonic(KeyEvent.VK_S);
		subEncrptItem.setActionCommand("SubstitutionEncrypt");
		subEncrptItem.addActionListener(itemAction);
		encryptMenu.add(subEncrptItem);

		// Affine encrypt menu item
		JMenuItem affineEncryptItem = new JMenuItem("Mã hóa Affine");
		affineEncryptItem.setMnemonic(KeyEvent.VK_A);
		affineEncryptItem.setDisplayedMnemonicIndex(7);
		affineEncryptItem.setActionCommand("AffineEncrypt");
		affineEncryptItem.addActionListener(itemAction);
		encryptMenu.add(affineEncryptItem);

		// Vigenere encrypt menu item
		JMenuItem vigenereEncryptItem = new JMenuItem("Mã hóa Vigenere");
		vigenereEncryptItem.setMnemonic(KeyEvent.VK_V);
		vigenereEncryptItem.setActionCommand("VigenereEncrypt");
		vigenereEncryptItem.addActionListener(itemAction);
		encryptMenu.add(vigenereEncryptItem);
		
		/*
		 * Decrypt Menu
		 */
		JMenu decryptMenu = new JMenu("Giải mã");
		menuBar.add(decryptMenu);

		// Caesar decrypt menu item
		JMenuItem caesarDecryptItem = new JMenuItem("Giải mã Caesar");
		caesarDecryptItem.setMnemonic(KeyEvent.VK_C);
		caesarDecryptItem.setActionCommand("CaesarDecrypt");
		caesarDecryptItem.addActionListener(itemAction);
		decryptMenu.add(caesarDecryptItem);

		// Substitution decrypt menu item
		JMenuItem subDecryptItem = new JMenuItem("Giải mã Substitution");
		subDecryptItem.setMnemonic(KeyEvent.VK_S);
		subDecryptItem.setActionCommand("SubstitutionDecrypt");
		subDecryptItem.addActionListener(itemAction);
		decryptMenu.add(subDecryptItem);

		// Affine decrypt menu item
		JMenuItem affineDecryptItem = new JMenuItem("Giải mã Affine");
		affineDecryptItem.setMnemonic(KeyEvent.VK_A);
		affineDecryptItem.setActionCommand("AffineDecrypt");
		affineDecryptItem.addActionListener(itemAction);
		decryptMenu.add(affineDecryptItem);

		// Vigenere decrypt menu item
		JMenuItem vigenereDecryptItem = new JMenuItem("Giải mã Vigenere");
		vigenereDecryptItem.setMnemonic(KeyEvent.VK_V);
		vigenereDecryptItem.setActionCommand("VigenereDecrypt");
		vigenereDecryptItem.addActionListener(itemAction);
		decryptMenu.add(vigenereDecryptItem);

		return menuBar;
	}

	public static void main(String[] args) {
		new MyFrame();
	}
}