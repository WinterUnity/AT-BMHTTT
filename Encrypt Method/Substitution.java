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

	// Tạo khóa Substitution
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
		int half = arr.length/2;
		for (int i = 0; i < half; i++) {
			number = rd.nextInt(half);
			char subLowerHalf = arr[i];
			arr[i] = arr[(i+number)%half];
			arr[(i+number)%half] = subLowerHalf;
			
			char subHigherHalf = arr[i+half];
			arr[i+half] = arr[((i+number)%half)+half];
			arr[((i+number)%half)+half] = subHigherHalf;
		}
		return arr;
	}

	// Đọc khóa Substitution
	public char[] readKey() {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("key.txt"));
			key = (char[]) ois.readObject();
			System.out.println(key);
			return key;
		} catch (IOException e) {
			System.out.println("Key cannot read from file");
			throw new RuntimeException(e);
		} catch (ClassNotFoundException e) {
			System.out.println("Key not found");
			throw new RuntimeException(e);
		}
	}
	
	//Mã hóa thay thế
	public void encrypt(String srcFile) throws Exception{
		File file = new File(srcFile);
		if(file.isFile()) {
			Path path = Path.of(srcFile);
			String fileString = Files.readString(path);
			
			int count;
			encryptedString = "";
			for (int i = 0; i < fileString.length(); i++) {
				count = 0;
				for (int j = 0; j < key.length; j++) {
					if(fileString.charAt(i) == alphabet.charAt(j)) {
						encryptedString += key[j];
					} else {
						count++;
						if(count == 52) {
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
		} else {
			System.out.println("This is not a File");
		}
	}
	
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
			
			int position, count;
			decryptedString = "";
			for (int i = 0; i < content.length(); i++) {
				count = 0;
				for (int j = 0; j < key.length; j++) {
					if(content.charAt(i) == key[j]) {
						decryptedString += alphabet.charAt(j);
					} else {
						count++;
						if(count == 52) {
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
	
	public static void main(String[] args) throws Exception {
		Substitution s = new Substitution();
//		s.createKey();
		s.readKey();
		s.encrypt("D:\\BMHTTT\\Substitution\\SourceText.txt");
		s.decrypt("CipherText.txt");
	}
}