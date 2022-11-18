package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Substitution {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	char[] key;
	String encryptedString, decryptedString;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 *  Tạo khóa Substitution
	 * @return char[] key
	 */
	public char[] createKey() {
		key = new char[alphabet.length()];
		for (int k = 0; k < key.length; k++) {
			key[k] = alphabet.charAt(k);
		}
		swap(key);
		return key;
	}

	private char[] swap(char[] arr) {
		Random rd = new Random();
		int number = 0;
		int half = arr.length / 2;
		for (int i = 0; i < half; i++) {
			number = rd.nextInt(half);
			char subLowerHalf = arr[i];
			arr[i] = arr[(i + number) % half];
			arr[(i + number) % half] = subLowerHalf;

			char subHigherHalf = arr[i + half];
			arr[i + half] = arr[((i + number) % half) + half];
			arr[((i + number) % half) + half] = subHigherHalf;
		}
		return arr;
	}

	/**
	 *  Đọc khóa Substitution
	 * @param srcFile
	 * @return char[] key
	 * @throws IOException
	 */
	public char[] readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		key = keyString.toCharArray();
		br.close();
		
		return key;
	}

	/**
	 *  Mã hóa thay thế
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, char[] input, File destDir) throws Exception {
		String plainText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			plainText = Files.readString(path);
		} else {
			plainText = source;
		}

		int count;
		int length = alphabet.length();
		encryptedString = "";
		key = input;
		for (int i = 0; i < plainText.length(); i++) {
			count = 0;
			for (int j = 0; j < key.length; j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					encryptedString += key[j];
				} else {
					count++;
					if (count == length) {
						encryptedString += plainText.charAt(i);
					}
				}
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\SubstitutionEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Giải mã Thay thế
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(String source, char[] input, File destDir) throws Exception {
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
		int count;
		int length = alphabet.length();
		decryptedString = "";
		key = input;
		for (int i = 0; i < cipherText.length(); i++) {
			count = 0;
			for (int j = 0; j < key.length; j++) {
				if (cipherText.charAt(i) == key[j]) {
					decryptedString += alphabet.charAt(j);
				} else {
					count++;
					if (count == length) {
						decryptedString += cipherText.charAt(i);
					}
				}
			}
		}

		// Create decrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\SubstitutionDecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}