package javaThread;
/*
 * 1부터 100까지의 숫자의 합을 구하려고한다
 * 1~10까지 1개의 Thread가 합을 계산해서 결과를 return
 * 11~20까지 1개의 Thread가 합을 계산해서 결과를 return
 * ...
 * 91~100까지 1개의 Thread가 합을 계산해서 결과를 return
 * ==> Thread Pool을 이용해야 하고 Callable을 이용해서 리턴값을 받아야한다
 * ==> 10개의 Thread로부터 각각 데이터를 받아들이는 Thread를 하나 만들어야 한다.
 * 
 */

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.IntStream;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam10_TheadCompleteService extends Application  {
	
	TextArea textarea;
	Button initbtn, startBtn, stopBtn;
	// initBtn : Thread Pool을 생성하는 버튼
	// startBtn : Thread Pool을 이용해서 Thread를 싱행시키는 버튼
	// stopBtn : Thread Pool을 종료하는 버튼
	ExecutorService executorService;
	ExecutorCompletionService<Integer> executeCompletionService;
	private int total = 0;
	
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
		
		initbtn = new Button("Thread Pool 생성");		
		initbtn.setPrefSize(250, 50);
		initbtn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			executorService = Executors.newCachedThreadPool();
			// 더좋은 Thread풀을 만든다
			executeCompletionService = new ExecutorCompletionService<Integer>(executorService);
			
			int threadNum = ((ThreadPoolExecutor)executorService).getPoolSize();
			printMsg("현재 Pool안의 Thread 개수 : " + threadNum);
//			executorService = Executors.newFixedThreadPool(5);
		}); 
		stopBtn = new Button("Thread Pool 종료");		
		stopBtn.setPrefSize(250, 50);
		stopBtn.setOnAction(t -> {
			executorService.shutdown();
		}); 
		startBtn = new Button("Thread Pool 실행");		
		startBtn.setPrefSize(250, 50);
		startBtn.setOnAction(t -> {
			for(int i=1; i<101; i=i+10) {
				final int k = i;
				
				Callable<Integer> callable = new Callable<Integer>() {
					@Override
					public Integer call() throws Exception {
						IntStream intStream = IntStream.rangeClosed(k, k+9);
						int sum = intStream.sum();
						return sum;
					}
				};				
				executeCompletionService.submit(callable);					
			}
			
			Runnable runnable = new Runnable() {
				
				@Override
				public void run() {
					for(int i=0; i<10; i++) {
						try {							
						Future<Integer> future = executeCompletionService.take();
						total += future.get();
						}
						catch(InterruptedException | ExecutionException e) {
							System.out.println(e);
						}
					}
					printMsg("최종 결과값은 : " + total);
				}
			};
			executorService.execute(runnable);
			}); 
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올린다.
		flowpane.getChildren().add(initbtn);
		flowpane.getChildren().add(startBtn);
		flowpane.getChildren().add(stopBtn);
		root.setBottom(flowpane);
		
		// Scene 객체 필요
	    Scene scene = new Scene(root);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("Thread 예제 ^^");
	    primaryStage.show();
			
	}

}
