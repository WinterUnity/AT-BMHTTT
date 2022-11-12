package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Vigenere {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int[] key;
	String encryptedString, decryptedString;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa dựa theo kích thước khóa
	 * 
	 * @param size
	 * @return int[] key
	 */
	public int[] createKeyBaseOnSize(int size) {
		key = new int[size];
		Random rd = new Random();
		int number;
		for (int i = 0; i < key.length; i++) {
			number = rd.nextInt(alphabet.length() / 2);
			key[i] = number;
		}

		return key;
	}

	/**
	 * Tạo khóa dựa theo tên của khóa
	 * 
	 * @param keyWord
	 * @return int[] key
	 */
	public int[] createKeyBaseOnKeyWord(String keyWord) {
		key = new int[keyWord.length()];
		for (int i = 0; i < keyWord.length(); i++) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (keyWord.charAt(i) == alphabet.charAt(j)) {
					key[i] = j;
				}
			}
		}

		return key;
	}

	/**
	 * Đọc khóa
	 * 
	 * @param srcFile
	 * @return int[] key
	 * @throws Exception
	 */
	public int[] readKeyArray(File srcFile) throws Exception {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		String[] stringArray = keyString.replace("[", "").replace("]", "").split(", ");
		key = new int[stringArray.length];
		for (int i = 0; i < stringArray.length; i++) {
			key[i] = Integer.valueOf(stringArray[i]);
		}
		br.close();

		return key;
	}

	public int[] readKeyString(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		key = createKeyBaseOnKeyWord(keyString);
		br.close();
		
		return key;
	}

	/**
	 * Mã hóa Vigenere
	 * 
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, int[] input, File destDir) throws Exception {
		String plainText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		// Encrypting
		int count1, count2 = 0, number;
		int length = alphabet.length(), half = alphabet.length() / 2;
		encryptedString = "";
		key = input;
		for (int i = 0; i < plainText.length(); i++) {
			count1 = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					if (count2 % key.length == 0) {
						count2 = 0;
					}
					if (j < half) {
						number = (j + key[count2]) % half;
						encryptedString += alphabet.charAt(number);
					} else {
						number = (j + key[count2]) % half + half;
						encryptedString += alphabet.charAt(number);
					}
					count2++;
				} else {
					count1++;
					if (count1 == length) {
						encryptedString += plainText.charAt(i);
					}
				}
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\VigenereEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	// Giải mã Vigenere
	public void decrypt(String source, int[] input, File destDir) throws Exception {
		String cipherText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			cipherText = Files.readString(path);
		} else {
			cipherText = source;
		}

		// Decrypting
		int count1, count2 = 0, number;
		int length = alphabet.length(), half = alphabet.length() / 2;
		decryptedString = "";
		key = input;
		for (int i = 0; i < cipherText.length(); i++) {
			count1 = 0;
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					if (count2 % key.length == 0) {
						count2 = 0;
					}
					if (j < half) {
						if (j - key[count2] < 0) {
							number = (j - key[count2]) + half;
						} else {
							number = (j - key[count2]);
						}
						decryptedString += alphabet.charAt(number);
					} else {
						number = (j - key[count2]) % half + half;
						decryptedString += alphabet.charAt(number);
					}
					count2++;
				} else {
					count1++;
					if (count1 == length) {
						decryptedString += cipherText.charAt(i);
					}
				}
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\VigenereEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public boolean isNumeric(String strNum) {
	    if (strNum == null) {
	        return false;
	    }
	    try {
	        int number = Integer.parseInt(strNum);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
}