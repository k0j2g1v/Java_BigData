package javaThread;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class JavaFXUITemplate extends Application  {
	
	TextArea textarea;
	Button btn;
	
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
		
		btn = new Button("버튼 클릭!!");
		// 버튼 위에 쓰여질 글자를 생성자로 쓴다
		btn.setPrefSize(250, 50);
		// setOnAction( 생성자로 리스너 객체가 들어간다. )
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// Textarea에 글자를 넣어요!
			textarea.appendText("소리없는 아우성!!\n");
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
