package aes;

public class MainAES {
	
		 
	    public static void main(String[] args) throws Exception {
	        String key = "aes256-test-key!!";       // key�� 16�� �̻�
	        AES256Util aes256 = new AES256Util(key);
	         
	        String text = "hyein9503!@qw";
	        String encText = aes256.aesEncode(text);
	        String decText = aes256.aesDecode(encText);
	         
	        System.out.println("��ȣȭ�� ���� : " + text);
	        System.out.println("��ȣȭ�� ���� : " + encText);
	        System.out.println("��ȣȭ�� ���� : " + decText);
	    }
	 
	}

