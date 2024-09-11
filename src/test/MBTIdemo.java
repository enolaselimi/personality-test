package test;

import java.io.FileNotFoundException;

import javax.swing.JFrame;

public class MBTIdemo {

	public static void main(String[] args) throws FileNotFoundException {
		JFrame test = new MBTI();
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.setVisible(true);
	}

}
