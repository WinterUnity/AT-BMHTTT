package Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DefaultPanel extends JPanel{
	JLabel label;
	
	public DefaultPanel() {
		setLayout(new BorderLayout());
		
		//Label
		String home = "<html><center>Chào mừng bạn đến với tool Mã hóa ký tự.<br>"
					+ "Để thực hiện mã hóa ký tự, vui lòng chọn menu Mã hóa và chọn phương pháp mã hóa.<br>"
					+ "Để thực hiện giải mã ký tự, vui lòng chọn menu Giải mã và chọn phương pháp mã hóa.</center></html>";
		
		label = new JLabel(home, SwingConstants.CENTER);
		label.setFont(new Font(null, Font.BOLD, 15));
		add(label);
	}
	
	
}