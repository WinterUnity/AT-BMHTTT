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
		fileMenu.addSeparator();
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
		encryptMenu.addSeparator();
		JMenuItem subEncrptItem = new JMenuItem("Mã hóa Substitution");
		subEncrptItem.setMnemonic(KeyEvent.VK_S);
		subEncrptItem.setActionCommand("SubstitutionEncrypt");
		subEncrptItem.addActionListener(itemAction);
		encryptMenu.add(subEncrptItem);

		// Affine encrypt menu item
		encryptMenu.addSeparator();
		JMenuItem affineEncryptItem = new JMenuItem("Mã hóa Affine");
		affineEncryptItem.setMnemonic(KeyEvent.VK_A);
		affineEncryptItem.setDisplayedMnemonicIndex(7);
		affineEncryptItem.setActionCommand("AffineEncrypt");
		affineEncryptItem.addActionListener(itemAction);
		encryptMenu.add(affineEncryptItem);

		// Vigenere encrypt menu item
		encryptMenu.addSeparator();
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
		decryptMenu.addSeparator();
		JMenuItem subDecryptItem = new JMenuItem("Giải mã Substitution");
		subDecryptItem.setMnemonic(KeyEvent.VK_S);
		subDecryptItem.setActionCommand("SubstitutionDecrypt");
		subDecryptItem.addActionListener(itemAction);
		decryptMenu.add(subDecryptItem);

		// Affine decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem affineDecryptItem = new JMenuItem("Giải mã Affine");
		affineDecryptItem.setMnemonic(KeyEvent.VK_A);
		affineDecryptItem.setActionCommand("AffineDecrypt");
		affineDecryptItem.addActionListener(itemAction);
		decryptMenu.add(affineDecryptItem);

		// Vigenere decrypt menu item
		decryptMenu.addSeparator();
		JMenuItem vigenereDecryptItem = new JMenuItem("Giải mã Vigenere");
		vigenereDecryptItem.setMnemonic(KeyEvent.VK_V);
		vigenereDecryptItem.setActionCommand("VigenereDecrypt");
		vigenereDecryptItem.addActionListener(itemAction);
		decryptMenu.add(vigenereDecryptItem);

		/*
		 * Encrypt File menu
		 */
		JMenu encryptFileMenu = new JMenu("Mã hóa File");
		menuBar.add(encryptFileMenu);

		// Caesar Encrypt File menu item
		JMenuItem caesarEncryptFileItem = new JMenuItem("Mã hóa Caesar");
		caesarEncryptFileItem.setMnemonic(KeyEvent.VK_C);
		caesarEncryptFileItem.setActionCommand("CaesarEncryptFile");
		caesarEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(caesarEncryptFileItem);

		// Substitution encrypt menu item
		encryptFileMenu.addSeparator();
		JMenuItem subEncryptFileItem = new JMenuItem("Mã hóa Substitution");
		subEncryptFileItem.setMnemonic(KeyEvent.VK_S);
		subEncryptFileItem.setActionCommand("SubstitutionEncryptFile");
		subEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(subEncryptFileItem);

		// Affine encrypt menu item
		encryptFileMenu.addSeparator();
		JMenuItem affineEncryptFileItem = new JMenuItem("Mã hóa Affine");
		affineEncryptFileItem.setMnemonic(KeyEvent.VK_A);
		affineEncryptFileItem.setDisplayedMnemonicIndex(7);
		affineEncryptFileItem.setActionCommand("AffineEncryptFile");
		affineEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(affineEncryptFileItem);

		// Vigenere encrypt menu item
		encryptFileMenu.addSeparator();
		JMenuItem vigenereEncryptFileItem = new JMenuItem("Mã hóa Vigenere");
		vigenereEncryptFileItem.setMnemonic(KeyEvent.VK_V);
		vigenereEncryptFileItem.setActionCommand("VigenereEncryptItem");
		vigenereEncryptFileItem.addActionListener(itemAction);
		encryptFileMenu.add(vigenereEncryptFileItem);
		
		/*
		 * Decrypt File Menu
		 */
		JMenu decryptFileMenu = new JMenu("Giải mã File");
		menuBar.add(decryptFileMenu);

		// Caesar decrypt file menu item
		JMenuItem caesarDecryptFileItem = new JMenuItem("Giải mã Caesar");
		caesarDecryptFileItem.setMnemonic(KeyEvent.VK_C);
		caesarDecryptFileItem.setActionCommand("CaesarDecryptFile");
		caesarDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(caesarDecryptFileItem);

		// Substitution decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem subDecryptFileItem = new JMenuItem("Giải mã Substitution");
		subDecryptFileItem.setMnemonic(KeyEvent.VK_S);
		subDecryptFileItem.setActionCommand("SubstitutionDecryptFile");
		subDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(subDecryptFileItem);

		// Affine decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem affineDecryptFileItem = new JMenuItem("Giải mã Affine");
		affineDecryptFileItem.setMnemonic(KeyEvent.VK_A);
		affineDecryptFileItem.setActionCommand("AffineDecryptFile");
		affineDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(affineDecryptFileItem);

		// Vigenere decrypt file menu item
		decryptFileMenu.addSeparator();
		JMenuItem vigenereDecryptFileItem = new JMenuItem("Giải mã Vigenere");
		vigenereDecryptFileItem.setMnemonic(KeyEvent.VK_V);
		vigenereDecryptFileItem.setActionCommand("VigenereDecryptFile");
		vigenereDecryptFileItem.addActionListener(itemAction);
		decryptFileMenu.add(vigenereDecryptFileItem);

		return menuBar;
	}

	public static void main(String[] args) {
		new MyFrame();
	}
}