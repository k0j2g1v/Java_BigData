package javaThread;
/*
 * Java Application은 main thread가 main() method를 호출해서 실행
 * 
 * 프로그램은 main method()가 종료 될때 종료되는게 아니라 프로그램 내에서
 * 파생된 모든 Thread가 종료될 때 종료된다
 * 
 * 1. Thread의 생성 
 *    => Thread class를 상속받아서 class를 정의하고 객체 생성해서 사용
 *    => Runnable interface를 구현한 class를 정의하고 객체를 생성해서
 *       Thread 생성자의 인자로 넣어서 Thread 생성
 *    => 현제 사용되는 Thread의 이름을 출력!!
 * 2. 실제 Thread의 생성(new) -> start() (thread를 실행시키는게 아니라
 * 	  runnable상태로 전환) -> JVM안에 있는 Thread schedule에 의해
 *    하나의 Thread가 선택되서 thread가 running상태로 전환 -> 어느 시점이
 *    되면 Thread scheduler에 의해서 runnable 상태로 전환 -> 다른 thread
 *    를 선택해서 running상태로 전환
 */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class Exam01_ThreadBasic extends Application  {
	
	TextArea textarea;
	Button btn;
	
	public static void main(String[] args) {
		// 현제 main method를 호출한 Thread의 이름을 출력!
		// 기억하기 이 코드를 수행하는 Thread가 무엇인지 확인
		// Thread의 이름을 확인이 가능하고 정해주는것도 가능하다
		System.out.println(Thread.currentThread().getName());
		
		launch();
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// start는 누가 호출하는지 알수 있다.
		// 우리가 Thread를 만든적 없지만 실행될때 자동으로 내부 Thread가 실행된다
		// JavaFX는 내부적으로 화면을 제어하는 Thread를 생성해서 사용한다!
		System.out.println(Thread.currentThread().getName());
		
		// 화면구성해서 window 띄우는 코드
		// 화면기본 layout을 설정 => 화면을 동서남북중앙(5개의 영역)으로 분리
		BorderPane root = new BorderPane();
		
		// BorderPane의 크기를 설정 => 화면에 띄우는 window의 크기 설정
		root.setPrefSize(700, 500);
		
		// Component생성해서 BorderPane에 부착
		textarea = new TextArea();
		root.setCenter(textarea);
		
		btn = new Button("버튼 클릭!!");
		// 버튼 위에 쓰여질 글자를 생성자로 쓴다
		btn.setPrefSize(250, 50);
		// setOnAction( 생성자로 리스너 객체가 들어간다. )
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// 버튼을 클릭하면 Thread를 생성
//			new Thread(new Runnable() {				
//				@Override
//				public void run() {
//					
//				}
//			}).start();
		
			new Thread(() -> {
				System.out.println(Thread.currentThread().getName());
				// 화면 제어는 JavaFX Application Thread가 당담
				// textarea에 출력하기 위해서 JavaFX Application Thread
				// 한테 부탁해야 한다
				Platform.runLater(()->{
					textarea.appendText("aaa\n");
					// 화면 깨짐을 방지 
				});
				
			}).start();
		}); 
			
		FlowPane flowpane = new FlowPane();
		flowpane.setPrefSize(700, 50);
		// flowpane에 버튼을 올린다.
		flowpane.getChildren().add(btn);
		root.setBottom(flowpane);
		
		// Scene객체가 필요하다
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Thread 예제입니다.!!");
		primaryStage.show();
			
	}

}
