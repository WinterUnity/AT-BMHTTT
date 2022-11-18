package Main;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import EncryptPanel.*;
import DecryptPanel.*;

public class MyPanel extends JPanel{
	JPanel mainPanel, defaultPanel;
	JPanel caesarTextEP, substitutionTextEP, affineTextEP, vigenereTextEP, 
			hillTextEP, permutationTextEP, rsaTextEP, hashTextEP;
	JPanel caesarTextDP, substitutionTextDP, affineTextDP, vigenereTextDP,
			hillTextDP, permutationTextDP, rsaTextDP;
	JPanel caesarFileEP, affineFileEP, vigenereFileEP,
			hillFileEP, permutationFileEP, aesFileEP, rsaFileEP, hashFileEP;
	JPanel caesarFileDP, affineFileDP, vigenereFileDP,
			hillFileDP, permutationFileDP, aesFileDP, rsaFileDP;
	
	CardLayout cardLayout;
	
	public MyPanel() {
		setLayout(new BorderLayout());
		
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		defaultPanel = new DefaultPanel();
		
		//Text
		caesarTextEP = new CaesarEP();
		substitutionTextEP = new SubstitutionEP();
		affineTextEP = new AffineEP();
		vigenereTextEP = new VigenereEP();
		hillTextEP = new HillEP();
		permutationTextEP = new PermutationEP();
		rsaTextEP = new RSAEP();
		hashTextEP = new HashEP();
		
		caesarTextDP = new CaesarDP();
		substitutionTextDP = new SubstitutionDP();
		affineTextDP = new AffineDP();
		vigenereTextDP = new VigenereDP();
		hillTextDP = new HillDP();
		permutationTextDP = new PermutationDP();
		rsaTextDP = new RSADP();
		
		//File
		caesarFileEP = new CaesarFileEP();
		affineFileEP = new AffineFileEP();
		vigenereFileEP = new VigenereFileEP();
		hillFileEP = new HillFileEP();
		permutationFileEP = new PermutationFileEP();
		aesFileEP = new AESFileEP();
		rsaFileEP = new RSAFileEP();
		hashFileEP = new HashFileEP();
		
		caesarFileDP = new CaesarFileDP();
		affineFileDP = new AffineFileDP();
		vigenereFileDP = new VigenereFileDP();
		hillFileDP = new HillFileDP();
		permutationFileDP = new PermutationFileDP();
		aesFileDP = new AESFileDP();
		rsaFileDP = new RSAFileDP();
		
		
		mainPanel.add("Home", defaultPanel);
		
		//Text 
		mainPanel.add("CaesarEncrypt", caesarTextEP);
		mainPanel.add("SubstitutionEncrypt", substitutionTextEP);
		mainPanel.add("AffineEncrypt", affineTextEP);
		mainPanel.add("VigenereEncrypt", vigenereTextEP);
		mainPanel.add("HillEncrypt", hillTextEP);
		mainPanel.add("PermutationEncrypt", permutationTextEP);
		mainPanel.add("RSAEncrypt", rsaTextEP);
		mainPanel.add("HashEncrypt", hashTextEP);
		
		mainPanel.add("CaesarDecrypt", caesarTextDP);
		mainPanel.add("SubstitutionDecrypt", substitutionTextDP);
		mainPanel.add("AffineDecrypt", affineTextDP);
		mainPanel.add("VigenereDecrypt", vigenereTextDP);
		mainPanel.add("HillDecrypt", hillTextDP);
		mainPanel.add("PermutationDecrypt", permutationTextDP);
		mainPanel.add("RSADecrypt", rsaTextDP);
		
		//File
		mainPanel.add("CaesarEncryptFile", caesarFileEP);
		mainPanel.add("AffineEncryptFile", affineFileEP);
		mainPanel.add("VigenereEncryptFile", vigenereFileEP);
		mainPanel.add("HillEncryptFile", hillFileEP);
		mainPanel.add("PermutationEncryptFile", permutationFileEP);
		mainPanel.add("AESEncryptFile", aesFileEP);
		mainPanel.add("RSAEncryptFile", rsaFileEP);
		mainPanel.add("HashEncryptFile", hashFileEP);
		
		mainPanel.add("CaesarDecryptFile", caesarFileDP);
		mainPanel.add("AffineDecryptFile", affineFileDP);
		mainPanel.add("VigenereDecryptFile", vigenereFileDP);
		mainPanel.add("HillDecryptFile", hillFileDP);
		mainPanel.add("PermutationDecryptFile", permutationFileDP);
		mainPanel.add("AESDecryptFile", aesFileDP);
		mainPanel.add("RSADecryptFile", rsaFileDP);
		
		add(mainPanel, BorderLayout.CENTER);
	}

	public void change(String actionCommand) {
		cardLayout.show(mainPanel, actionCommand);
	}
}