package javaThread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam08_ThreadPoolBasic extends Application  {
	
	TextArea textarea;
	Button initbtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 싱행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼
	ExecutorService executorService;
	
	private void printMsg(String msg) {
		Platform.runLater(()->{
			textarea.appendText(msg + "\n");
		});
	}
	
	public static void main(String[] args) {
		
		launch();
		
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
		
		initbtn = new Button("Thread Pool 생성");		
		initbtn.setPrefSize(250, 50);
		initbtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			executorService = Executors.newCachedThreadPool();
			int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("현재 Pool안의 Thread 개수 : " + threadNum);
			// 처음에 만들어지는 Thread Pool안에는 thread가 없다
			// 만약 필요하면 내부적으로 Thread를 생성
			// 만드는 Thread의 수는 제한이 없다
			// 60초 동안 Thread가 사용되지 않으면 자동적으로 삭제.
//			executorService = Executors.newFixedThreadPool(5);
			// 처음에 만들어지는 Thread Pool안에는 thread가 없다
			// 만약 필요하면 내부적으로 Thread를 생성
			// 인자로 들어온 int수만큼의 Thread를 넘지 못한다.
			// Thread가 사용되지 않더라도 만들어진 Thread는 계속 유지.
		}); 
		stopBtn = new Button("Thread Pool 종료");		
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown();
		}); 
		startBtn = new Button("Thread Pool 실행");		
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i=0; i<10; i++) {
				final int k = i;
				Runnable runnable = () -> {
					Thread.currentThread().setName("MYThread-" + k);
					String msg = Thread.currentThread().getName() + "Pool의 개수 : " +
									((ThreadPoolExecutor)executorService).getPoolSize();
					printMsg(msg);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				};				
				
				executorService.execute(runnable);
			}
		}); 
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올린다.
		flowpane.getChildren().add(initbtn);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		// Scene객체가 필요하다
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다.!!");
		primaryStage.show();
			
	}

}
