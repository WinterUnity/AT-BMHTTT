package EncryptMethod;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Vigenere {
	final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	int[] key;
	String encryptedString, decryptedString;

	// Tạo khóa
	public int[] createKeyBaseOnSize(int size) {
		key = new int[size];
		Random rd = new Random();
		int number;
		for (int i = 0; i < key.length; i++) {
			number = rd.nextInt(alphabet.length() / 2);
			key[i] = number;
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("key.txt"));
			oos.writeObject(key);
			oos.close();
			System.out.println("Key create successfully");
		} catch (Exception e) {
			System.out.println("Key cannot save to file");
		}

		return key;
	}

	public int[] createKeyBaseOnKeyWord(String keyWord) {
		key = new int[keyWord.length()];
		for (int i = 0; i < keyWord.length(); i++) {
			for (int j = 0; j < alphabet.length(); j++) {
				if (keyWord.charAt(i) == alphabet.charAt(j)) {
					key[i] = j;
				}
			}
		}
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("key.txt"));
			oos.writeObject(key);
			oos.close();
			System.out.println("Key create successfully");
		} catch (Exception e) {
			System.out.println("Key cannot save to file");
		}

		return key;
	}

	// Đọc khóa
	public int[] readKeyArray(String path) {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(path));
			key = (int[]) ois.readObject();
			for (int i = 0; i < key.length; i++) {
				if (i == key.length - 1) {
					System.out.println(key[i]);
				} else {
					System.out.print(key[i] + " ");
				}
			}
			return key;
		} catch (IOException e) {
			System.out.println("Key cannot read from file");
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("Key not found");
			throw new RuntimeException(e);
		}
	}

	public int[] readKeyString(String path) {
		try {
			Path p = Path.of(path);
			String input = Files.readString(p);
			createKeyBaseOnKeyWord(input);
			for (int i = 0; i < key.length; i++) {
				if (i == key.length - 1) {
					System.out.println(key[i]);
				} else {
					System.out.print(key[i] + " ");
				}
			}
			return key;
		} catch (IOException e) {
			System.out.println("Key cannot read from file");
			throw new RuntimeException(e);
		}
	}

	// Mã hóa Vigenere
	public void encrypt(String srcFile) throws Exception {
		File file = new File(srcFile);
		if (file.isFile()) {
			Path path = Path.of(srcFile);
			String fileString = Files.readString(path);

			int count1, count2 = 0, number;
			int half = alphabet.length() / 2;
			encryptedString = "";
			for (int i = 0; i < fileString.length(); i++) {
				count1 = 0;
				for (int j = 0; j < alphabet.length(); j++) {
					if (fileString.charAt(i) == alphabet.charAt(j)) {
						if(count2%key.length == 0) {
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
						if (count1 == 52) {
							encryptedString += fileString.charAt(i);
						}
					}
				}
			}

			try {
				FileWriter fw = new FileWriter("CipherText.txt");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(encryptedString);
				pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			System.out.println("Encrypt successfully");
		} else

		{
			System.out.println("This is not a File");
		}
	}
	
	//Giải mã Vigenere
	public void decrypt(String srcFile) throws Exception {
		File file = new File(srcFile);
		if(file.isFile()) {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
			
			byte byteRead;
			List<Byte> list = new ArrayList<Byte>();
			while((byteRead = (byte) bis.read()) != -1) {
				list.add(byteRead);
			}
			
			byte[] ex = new byte[list.size()];
			for (int a = 0; a < ex.length; a++) {
				ex[a] = list.get(a);
			}
			
			String content = new String(ex, "UTF-8");
			
			int count1, count2 = 0, number;
			int half = alphabet.length() / 2;
			decryptedString = "";
			for (int i = 0; i < content.length(); i++) {
				count1 = 0;
				for (int j = 0; j < alphabet.length(); j++) {
					if (content.charAt(i) == alphabet.charAt(j)) {
						if(count2%key.length == 0) {
							count2 = 0;
						}
						if (j < half) {
							if(j-key[count2]<0) {
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
						if (count1 == 52) {
							decryptedString += content.charAt(i);
						}
					}
				}
			}
			
			try {
				FileWriter fw = new FileWriter("PlainText.txt");
				PrintWriter pw = new PrintWriter(fw);
				pw.println(decryptedString);
				pw.close();
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			System.out.println("Decrypt successfully");
		} else {
			System.out.println("This is not a File");
		}
	}
	
	private int findKeyA2(int key) {
		int result = 0, half = alphabet.length()/2;
		for (int i = 0; i < half; i++) {
			if((i*key) % half == 1){
				result = i;
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		Vigenere v = new Vigenere();
//		v.createKeyBaseOnSize(6);
//		v.createKeyBaseOnKeyWord("CIPHER");
		v.readKeyArray("key.txt");
//		v.readKeyString("keyText.txt");
		v.encrypt("D:\\BMHTTT\\Vigenere\\SourceText.txt");
		v.decrypt("CipherText.txt");
	}
}