package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Hill {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ abcdefghijklmnopqrstuvwxyz ";
	int[][] matrixKey = new int[2][2];
	String encryptedString, decryptedString;
	byte[] encryptedBytes, decryptedBytes;
	boolean acceptable = true;

	public String getEncryptedString() {
		return encryptedString;
	}

	public String getDecryptedString() {
		return decryptedString;
	}

	/**
	 * Tạo khóa
	 * 
	 * @return
	 * @throws Exception
	 */
	public int[][] createKey() {
		matrixKey[0][0] = 2;
		matrixKey[0][1] = 3;
		matrixKey[1][0] = 5;
		matrixKey[1][1] = 7;

		return matrixKey;
	}

	/**
	 * Đọc khóa
	 * 
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
	 * Mã hóa Affine - Text
	 * 
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
		if (isAcceptableString(plainText) == false) {
			plainText += "Z";
		}
		int number1 = 0, number2 = 0, encryptNum1, encryptNum2;
		int half = alphabet.length() / 2;
		encryptedString = "";
		matrixKey = input;
		for (int i = 0; i < plainText.length(); i = i + 2) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (plainText.charAt(i) == alphabet.charAt(j)) {
					number1 = j;
				}
				if (plainText.charAt(i + 1) == alphabet.charAt(j)) {
					number2 = j;
				}
			}
			if (number1 >= half && number2 < half) {
				number1 = number1 - half;
				encryptNum1 = (matrixKey[0][0] * number1 + matrixKey[0][1] * number2) % half;
				encryptNum2 = (matrixKey[1][0] * number1 + matrixKey[1][1] * number2) % half;
				encryptedString += alphabet.charAt(encryptNum1 + half);
				encryptedString += alphabet.charAt(encryptNum2);
			}
			else if (number1 < half && number2 >= half) {
				number2 = number2 - half;
				encryptNum1 = (matrixKey[0][0] * number1 + matrixKey[0][1] * number2) % half;
				encryptNum2 = (matrixKey[1][0] * number1 + matrixKey[1][1] * number2) % half;
				encryptedString += alphabet.charAt(encryptNum1);
				encryptedString += alphabet.charAt(encryptNum2 + half);
			}
			else if (number1 >= half && number2 >= half) {
				number1 = number1 - half;
				number2 = number2 - half;
				encryptNum1 = (matrixKey[0][0] * number1 + matrixKey[0][1] * number2) % half;
				encryptNum2 = (matrixKey[1][0] * number1 + matrixKey[1][1] * number2) % half;
				encryptedString += alphabet.charAt(encryptNum1 + half);
				encryptedString += alphabet.charAt(encryptNum2 + half);
			}
			else if (number1 < half && number2 < half) {
				encryptNum1 = (matrixKey[0][0] * number1 + matrixKey[0][1] * number2) % half;
				encryptNum2 = (matrixKey[1][0] * number1 + matrixKey[1][1] * number2) % half;
				encryptedString += alphabet.charAt(encryptNum1);
				encryptedString += alphabet.charAt(encryptNum2);
			}
		}

		// Create encrypted file
		try {
			File destFile = new File(destDir.getAbsolutePath() + "\\HillEncrypted.txt");
			FileWriter fw = new FileWriter(destFile);
			PrintWriter pw = new PrintWriter(fw);
			pw.println(encryptedString + " false");
			pw.close();
			fw.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Mã hóa Hill - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void encryptFile(File srcFile, int[][] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		byte[] exFileContent;

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Encrypting
			if (fileContent.length % 2 != 0) {
				exFileContent = new byte[fileContent.length + 1];
				for (int f = 0; f < fileContent.length; f++) {
					exFileContent[f] = fileContent[f];
				}
				exFileContent[exFileContent.length - 1] = 0;
			} else {
				exFileContent = fileContent;
			}

			int number1, number2, encryptNum1, encryptNum2;
			int neg;
			byte[] resultBytes = new byte[exFileContent.length];
			matrixKey = input;
			for (int i = 0; i < exFileContent.length; i += 2) {
				number1 = exFileContent[i];
				number2 = exFileContent[i + 1];
				encryptNum1 = (matrixKey[0][0] * number1 + matrixKey[0][1] * number2) % 256;
				encryptNum2 = (matrixKey[1][0] * number1 + matrixKey[1][1] * number2) % 256;
				if (encryptNum1 < 0) {
					neg = encryptNum1 % 256;
					if (neg < -128) {
						encryptNum1 = 256 + neg;
					} else {
						encryptNum1 = neg;
					}
				} else if (encryptNum1 >= 128) {
					encryptNum1 = encryptNum1 - 256;
				}

				if (encryptNum2 < 0) {
					neg = encryptNum2 % 256;
					if (neg < -128) {
						encryptNum2 = 256 + neg;
					} else {
						encryptNum2 = neg;
					}
				} else if (encryptNum2 >= 128) {
					encryptNum2 = encryptNum2 - 256;
				}
				resultBytes[i] = (byte) (encryptNum1);
				resultBytes[i + 1] = (byte) (encryptNum2);
			}

			if (fileContent.length % 2 != 0) {
				encryptedBytes = new byte[resultBytes.length + 1];
				for (int f = 0; f < resultBytes.length; f++) {
					encryptedBytes[f] = resultBytes[f];
				}
				encryptedBytes[encryptedBytes.length - 1] = 0;
			} else {
				encryptedBytes = resultBytes;
			}

			// Create encrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\HillEncryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(encryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	/**
	 * Giải mã Affine - Text
	 * 
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

		if (cipherText.substring(cipherText.length() - 8, cipherText.length() - 2).equals(" false")) {
			cipherText = cipherText.substring(0, cipherText.length() - 8);
			acceptable = false;
		}

		// Decrypting
		int number1 = 0, number2 = 0, decryptNum1, decryptNum2;
		int half = alphabet.length() / 2;
		decryptedString = "";
		double[][] decryptMatrixKey = findInvertMatrixText(input);

		for (int i = 0; i < cipherText.length(); i = i + 2) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (cipherText.charAt(i) == alphabet.charAt(j)) {
					number1 = j;
				}
				if (cipherText.charAt(i + 1) == alphabet.charAt(j)) {
					number2 = j;
				}
			}
			if (number1 >= half && number2 < half) {
				number1 = number1 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0] * number1 + decryptMatrixKey[0][1] * number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0] * number1 + decryptMatrixKey[1][1] * number2) % half);
				decryptedString += alphabet.charAt(decryptNum1 + half);
				decryptedString += alphabet.charAt(decryptNum2);
			}
			else if (number1 < half && number2 > half) {
				number2 = number2 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0] * number1 + decryptMatrixKey[0][1] * number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0] * number1 + decryptMatrixKey[1][1] * number2) % half);
				decryptedString += alphabet.charAt(decryptNum1);
				decryptedString += alphabet.charAt(decryptNum2 + half);
			}
			else if (number1 >= half && number2 >= half) {
				number1 = number1 - half;
				number2 = number2 - half;
				decryptNum1 = (int) ((decryptMatrixKey[0][0] * number1 + decryptMatrixKey[0][1] * number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0] * number1 + decryptMatrixKey[1][1] * number2) % half);
				decryptedString += alphabet.charAt(decryptNum1 + half);
				decryptedString += alphabet.charAt(decryptNum2 + half);
			}
			else if (number1 < half && number2 < half) {
				decryptNum1 = (int) ((decryptMatrixKey[0][0] * number1 + decryptMatrixKey[0][1] * number2) % half);
				decryptNum2 = (int) ((decryptMatrixKey[1][0] * number1 + decryptMatrixKey[1][1] * number2) % half);
				decryptedString += alphabet.charAt(decryptNum1);
				decryptedString += alphabet.charAt(decryptNum2);
			}
		}
		
		if (acceptable == false) {
			decryptedString = decryptedString.substring(0, cipherText.length() - 1);
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

	/**
	 * Giải mã Hill - File
	 * 
	 * @param srcFile
	 * @param input
	 * @param destDir
	 * @throws Exception
	 */
	public void decryptFile(File srcFile, int[][] input, File destDir) throws Exception {
		byte[] fileContent = Files.readAllBytes(srcFile.toPath());
		byte[] exFileContent;

		// Check if source is file path or not
		if (srcFile.isFile()) {
			// Decrypting
			if (fileContent.length % 2 != 0) {
				exFileContent = new byte[fileContent.length - 1];
				for (int f = 0; f < exFileContent.length; f++) {
					exFileContent[f] = fileContent[f];
				}
			} else {
				exFileContent = fileContent;
			}

			int number1, number2, decryptNum1, decryptNum2;
			int neg;
			byte[] resultBytes = new byte[exFileContent.length];
			double[][] decryptMatrixkey = findInvertMatrixFile(input);
			;
			for (int i = 0; i < exFileContent.length; i += 2) {
				number1 = exFileContent[i];
				number2 = exFileContent[i + 1];
				decryptNum1 = (int) ((decryptMatrixkey[0][0] * number1 + decryptMatrixkey[0][1] * number2) % 256);
				decryptNum2 = (int) ((decryptMatrixkey[1][0] * number1 + decryptMatrixkey[1][1] * number2) % 256);
				if (decryptNum1 < 0) {
					neg = decryptNum1 % 256;
					if (neg < -128) {
						decryptNum1 = 256 + neg;
					} else {
						decryptNum1 = neg;
					}
				} else if (decryptNum1 >= 128) {
					decryptNum1 = decryptNum1 - 256;
				}

				if (decryptNum2 < 0) {
					neg = decryptNum2 % 256;
					if (neg < -128) {
						decryptNum2 = 256 + neg;
					} else {
						decryptNum2 = neg;
					}
				} else if (decryptNum2 >= 128) {
					decryptNum2 = decryptNum2 - 256;
				}
				resultBytes[i] = (byte) (decryptNum1);
				resultBytes[i + 1] = (byte) (decryptNum2);
			}

			if (fileContent.length % 2 != 0) {
				decryptedBytes = new byte[resultBytes.length - 1];
				for (int f = 0; f < decryptedBytes.length; f++) {
					decryptedBytes[f] = resultBytes[f];
				}
			} else {
				decryptedBytes = resultBytes;
			}

			// Create encrypted file
			try {
				File destFile = new File(destDir.getAbsolutePath() + "\\HillDecryptedFile" + getFileExtension(srcFile));
				FileOutputStream fos = new FileOutputStream(destFile);
				fos.write(decryptedBytes);
				fos.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			System.out.println("This is not a file");
		}
	}

	// Độ dài String chẵn hay lẻ
	public boolean isAcceptableString(String str) {
		if (str.length() % 2 == 1) {
			return false;
		}
		return true;
	}

	// Tìm ma trận nghịch đảo
	public double[][] findInvertMatrixText(int[][] matrix) {
		double[][] result = new double[2][2];
		int determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
		int length = alphabet.length() / 2;

		if (determinant == 0) {
			System.out.println("Can't find invert matrix");
			return null;
		} else {
			result[0][0] = matrix[1][1] / determinant;
			result[0][1] = (matrix[0][1] * (-1)) / determinant;
			result[1][0] = (matrix[1][0] * (-1)) / determinant;
			result[1][1] = matrix[0][0] / determinant;

			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					if (result[i][j] < 0) {
						result[i][j] = length - (result[i][j] * (-1));
					}
					result[i][j] = result[i][j] % length;
				}
			}

			return result;
		}
	}
	
	public double[][] findInvertMatrixFile(int[][] matrix) {
		double[][] result = new double[2][2];
		int determinant = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];

		if (determinant == 0) {
			System.out.println("Can't find invert matrix");
			return null;
		} else {
			result[0][0] = matrix[1][1] / determinant;
			result[0][1] = (matrix[0][1] * (-1)) / determinant;
			result[1][0] = (matrix[1][0] * (-1)) / determinant;
			result[1][1] = matrix[0][0] / determinant;

			for (int i = 0; i < result.length; i++) {
				for (int j = 0; j < result[i].length; j++) {
					if (result[i][j] < 0) {
						result[i][j] = 256 - (result[i][j] * (-1));
					}
					result[i][j] = result[i][j] % 256;
				}
			}

			return result;
		}
	}

	// Lấy extension của file
	public String getFileExtension(File file) {
		String fileName = file.getName();

		int i = fileName.lastIndexOf(".");
		String fileExtension = fileName.substring(i);

		return fileExtension;
	}
}
