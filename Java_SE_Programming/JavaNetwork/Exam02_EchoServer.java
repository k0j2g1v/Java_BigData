package JavaNetwork;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * Echo program을 작성해보자
 * 클라이언트 프로그램으로부터 문자열을 네트워크를 통해 전달받아서
 * 다시 클라이언트에게 전달하는 echo 서버 프로그램
 * 현재는 단 1번만 echo가 동작하는데
 * 
 * 지금은 프로그램은 서버가 클라이언트 1명만 서비스할 수 있다.
 * 다수의 클라이언트를 서비스 하려면 어떻게 해야 하나요?
 * ==> Thread를 이용해서 이 문제를 해결.
 * 
 */

public class Exam02_EchoServer {

	public static void main(String[] args) {
		
		ServerSocket server =  null;
		Socket socket = null;
		
		try {
			server = new ServerSocket(5557);
			System.out.println("서버 프로그램 기동 - 5557");
			socket = server.accept();
			// stream생성
			// 입력 스트림
			BufferedReader br = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
			// 출력 스트림
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			
			// br로 부터 데이터를 읽어서 out을 통해 다시 전달
//			String msg = br.readLine();
			String msg = "";
			while(!((msg = br.readLine()).equals("/@EXIT"))) {
				out.println(msg);
				out.flush();
			}
//			out.println(msg);
//			out.flush();
			// 사용된 리소스를 해제
			out.close();
			br.close();
			socket.close();
			server.close();
			System.out.println("서버 프로그램 종료 !!");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
