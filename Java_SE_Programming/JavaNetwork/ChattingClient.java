package JavaNetwork;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class ChattingClient extends Application  {
	/*
	 * 현재는 단 1번만 Chat가 동작하는대 클라이언트가 접속을 종료할때까지
	 * Chat작업이 지속적으로 동작하도록 프로그램 수정
	 */
	TextArea textarea;
	Button btn; // 서버 접속 버튼
	TextField tf; 
	Socket socket;
	BufferedReader br;
	PrintWriter out;
	
	public static void main(String[] args) {
		
		launch();
		
	}
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("Chat 서버 접속");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			Thread thread;
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 접속 버튼
			try {
				// 클라이언트는 버튼을 누르면 서버쪽에 Socket접속을 시도
				// 만약에 접속에 성공하면 Socket객체를 하나 획득
				Socket socket = new Socket("127.0.0.1",7777);
				// Stream을 생성
				InputStreamReader isr = new InputStreamReader(socket.getInputStream());
				br = new BufferedReader(isr);
				
				out = new PrintWriter(socket.getOutputStream());
				printMsg("Chat 서버 접속 성공!!");
				
				Runnable runnable = () -> {
					String result = "";
					try {
						while(true) {
							result = br.readLine();
							printMsg(result);
						}
					} catch (IOException e) {
						e.printStackTrace();
					}					
				};
				thread = new Thread(runnable);
				thread.start();
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}); 
		
		tf = new TextField();
		tf.setPrefSize(200, 40);
		tf.setOnAction(t -> {
			// 입력상자(TextField)에서 enter key가 입력되면 호출
			String msg = tf.getText();
			out.println(msg);  // 서버로 문자열 전송!!
			out.flush();
			tf.clear();
			
		});
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올린다.
		flowpane.getChildren().add(btn);
		flowpane.getChildren().add(tf);
		root.setBottom(flowpane);
		
		// Scene객체가 필요하다
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다.!!");
		primaryStage.show();
			
	}

}
