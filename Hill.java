package Test;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Hill {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz ";
	int[][] matrixKey = new int[2][2];
	String encryptedString, decryptedString;
	boolean acceptable;
	
	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa
	 * @return
	 * @throws Exception
	 */
	public int[][] createKey() throws Exception{
		Random rd = new Random();
		int a = 0, b = 0, c = 0, d = 0;
		while((a*d - b*c) != 0 || this.isCoprime(a*d - b*c) == false) {
			a = rd.nextInt(20);
			b = rd.nextInt(20);
			c = rd.nextInt(20);
			d = rd.nextInt(20);
		}
		matrixKey[0][0] = a;
		matrixKey[0][1] = b;
		matrixKey[1][0] = c;
		matrixKey[1][1] = d;
		
		return matrixKey;
	}
	
	// Tìm số nguyên tố chung
	public boolean isCoprime(int determinant) {		
		// Trả về true nếu determinant là -1 và 1
		if (determinant == -1 || determinant == 1) {
			return true;
		}
		
		// Chuyển về số dương nếu determinant âm
		if(determinant < 0) {
			determinant = determinant * (-1);
		}
		
		// Tìm ước số chung
		List<Integer> detDivisors = new ArrayList<Integer>();
		for (int dd = 1; dd < Math.sqrt(determinant); dd++) {
			if(determinant % dd == 0) {
				detDivisors.add(dd);
			}
		}
		List<Integer> alphabetDivisors = new ArrayList<Integer>();
		int length = alphabet.length() / 2;
		for (int ad = 0; ad < Math.sqrt(length); ad++) {
			if(length % ad == 0) {
				alphabetDivisors.add(ad);
			}
		}
		
		int count = 0; 
		for (int i = 0; i < detDivisors.size(); i++) {
			for (int j = 0; j < alphabetDivisors.size(); j++) {
				if (detDivisors.get(i) == alphabetDivisors.get(j)){
					count++;
				}
			}
		}
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Đọc khóa
	 * @return int[] keyPair
	 * @throws IOException
	 */
	public int[][] readKey(File srcFile) throws IOException {
		FileReader fr = new FileReader(srcFile);
		BufferedReader br = new BufferedReader(fr);
		String keyString = br.readLine();
		String[] stringArray = keyString.replace("[", "").replace("]", "").split(", ");
		matrixKey[0][0] = Integer.valueOf(stringArray[0]);
		matrixKey[0][1] = Integer.valueOf(stringArray[1]);
		matrixKey[1][0] = Integer.valueOf(stringArray[2]);
		matrixKey[1][1] = Integer.valueOf(stringArray[3]);
		br.close();
		
		return matrixKey;
	}
	
	/**
	 *  Mã hóa Affine
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encrypt(String source, int[][] input, File destDir) throws Exception {
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
		if(isAcceptableString(plainText) == false) {
			plainText += "Z";
		}
		int count, number1 = 0, number2 = 0, encryptNum1, encryptNum2;
		int length = alphabet.length(), 
			half = alphabet.length() / 2;
		encryptedString = "";
		matrixKey = input;
		char char1, char2;
		for (int i = 0; i < plainText.length(); i = i+2) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					number1 = j;
				}
				if(plainText.charAt(i+1) == alphabet.charAt(j)) {
					number2 = j;
				}
			}
			if(number1 > half) {
				number1 = number1 - half;
				encryptNum1 = (matrixKey[0][0]*number1 + matrixKey[0][1]*number2) % half;
				encryptNum2 = (matrixKey[1][0]*number1 + matrixKey[1][1]*number2) % half;
				encryptedString += alphabet.charAt(encryptNum1 + half);
				encryptedString += alphabet.charAt(encryptNum2);
			}
			if(number2 > half) {
				number2 = number2 - half;
				encryptNum1 = (matrixKey[0][0]*number1 + matrixKey[0][1]*number2) % half;
				encryptNum2 = (matrixKey[1][0]*number1 + matrixKey[1][1]*number2) % half;
				encryptedString += alphabet.charAt(encryptNum1);
				encryptedString += alphabet.charAt(encryptNum2 + half);
			}
			if(number1 > half && number2 > half) {
				number1 = number1 - half;
				number2 = number2 - half;
				encryptNum1 = (matrixKey[0][0]*number1 + matrixKey[0][1]*number2) % half;
				encryptNum2 = (matrixKey[1][0]*number1 + matrixKey[1][1]*number2) % half;
				encryptedString += alphabet.charAt(encryptNum1 + half);
				encryptedString += alphabet.charAt(encryptNum2 + half);
			}
		}
		if (isAcceptableString(plainText) == false) {
			encryptedString += " false";
		}
		
		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\HillEncrypted.txt");
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
	 *  Giải mã Affine
	 * @param source
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decrypt(String source, int[][] input, File destDir) throws Exception {
		String cipherText;
		File file = new File(source);

		// Check if source is file path or not
		if (file.isFile()) {
			Path path = Path.of(source);
			cipherText = Files.readString(path);
		} else {
			cipherText = source;
		}
		
		if (cipherText.substring(cipherText.length() - 6, cipherText.length() - 1).equals(" false")) {
			cipherText.substring(0, cipherText.length()-7);
			acceptable = false;
		}

		int count, number1 = 0, number2 = 0, decryptNum1, decryptNum2;
		int length = alphabet.length(), 
			half = alphabet.length() / 2;
		decryptedString = "";
		double[][] decryptMatrixKey = findInvertMatrix(input);
		
		char char1, char2;
		for (int i = 0; i < cipherText.length(); i = i+2) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					number1 = j;
				}
				if(cipherText.charAt(i+1) == alphabet.charAt(j)) {
					number2 = j;
				}
			}
			if(number1 > half) {
				number1 = number1 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0]*number1 + decryptMatrixKey[0][1]*number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0]*number1 + decryptMatrixKey[1][1]*number2) % half);
				encryptedString += alphabet.charAt(decryptNum1 + half);
				encryptedString += alphabet.charAt(decryptNum2);
			}
			if(number2 > half) {
				number2 = number2 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0]*number1 + decryptMatrixKey[0][1]*number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0]*number1 + decryptMatrixKey[1][1]*number2) % half);
				encryptedString += alphabet.charAt(decryptNum1);
				encryptedString += alphabet.charAt(decryptNum2 + half);
			}
			if(number1 > half && number2 > half) {
				number1 = number1 - half;
				number2 = number2 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0]*number1 + decryptMatrixKey[0][1]*number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0]*number1 + decryptMatrixKey[1][1]*number2) % half);
				encryptedString += alphabet.charAt(decryptNum1 + half);
				encryptedString += alphabet.charAt(decryptNum2 + half);
			}
		}

		// Create decrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\HillDecrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(decryptedString);
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	// Độ dài String chẵn hay lẻ
	public boolean isAcceptableString(String str) {
		if(str.length() % 2 == 1) {
			return false;
		}
		return true;
	}
	
	// Tìm ma trận nghịch đảo
	public double[][] findInvertMatrix(int[][] matrix){
		double[][] result = new double[2][2];
		int determinant = matrix[0][0]*matrix[1][1] - matrix[0][1]*matrix[1][0];
		if (determinant == 0) {
			System.out.println("Can't find invert matrix");
			return null;
		} else {
		result[0][0] = matrix[1][1] / determinant;
		result[0][1] = (matrix[0][1]*(-1)) / determinant;
		result[1][0] = (matrix[1][0]*(-1)) / determinant;
		result[1][1] = matrix[0][0] / determinant;
		
		return result;
		}
	}
}
