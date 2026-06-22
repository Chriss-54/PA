package model;

import java.io.IOException;
import java.util.Date;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		accountDAO adao=new accountDAO(new account(12345678,100.5,1,0.0,new Date()));
		try {
			adao.writerAccount();
		}catch (IOException e) {
			e.printStackTrace();
		}
	}

}
