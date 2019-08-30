package javaThread;

import java.awt.Panel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

class ShareObj{
	private int number = 0;

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
	
	public void assignNumber() {
		synchronized (this) {
			try {
				this.number += 1;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
}



class UserPanel extends FlowPane{
	private TextField nameFied = new TextField();
	private ProgressBar progressBar = new ProgressBar(0.0);
	private ProgressIndicator progressIndicator = 
			new ProgressIndicator(0.0);
	
	
	public UserPanel() {
	}
	
	public UserPanel(String name) {
		this.setPrefSize(700, 50);
		nameFied.setText(name);
		nameFied.setPrefSize(100, 50);
		progressBar.setPrefSize(500, 50);
		progressIndicator.setPrefSize(50, 50);
		getChildren().add(nameFied);
		getChildren().add(progressBar);
		getChildren().add(progressIndicator);
	}	
	
	public TextField getNameFied() {
		return nameFied;
	}

	public void setNameFied(TextField nameFied) {
		this.nameFied = nameFied;
	}

	public ProgressBar getProgressBar() {
		return progressBar;
	}

	public void setProgressBar(ProgressBar progressBar) {
		this.progressBar = progressBar;
	}

	public ProgressIndicator getProgressIndicator() {
		return progressIndicator;
	}

	public void setProgressIndicator(ProgressIndicator progressIndicator) {
		this.progressIndicator = progressIndicator;
	}	
}

class ProgressRunnable implements Runnable{
	private String name;
	private ProgressBar progressBar;
	private ProgressIndicator progressIndicator;
	private TextArea textarea;
	private ShareObj shareObj;
	
	public ProgressRunnable(String name, ProgressBar progressBar, ProgressIndicator progressIndicator,
			TextArea textarea,ShareObj shareObj) {
		super();
		
		this.name = name;
		this.progressBar = progressBar;
		this.progressIndicator = progressIndicator;
		this.textarea = textarea;
		this.shareObj = shareObj;
	}


	@Override
	public void run() {
		// Thread가 동작해서 progressBar를 제어하면 될 것 같아요!
		Random random = new Random();
		double k = 0;
		while(progressBar.getProgress() <= 1.0) {
			try {
				Thread.sleep(1000); // 1초 동안 현제 Thread를 sleep
				k += (random.nextDouble() * 0.1);
				final double tt = k;
				// 람다식에선 지역변수가 불가능하기에 final변수를 만들어준다.
				// k값이 지속적으로 증가
				Platform.runLater(() -> {
					progressBar.setProgress(tt);
					progressIndicator.setProgress(tt);
					//textarea.appendText("aaa\n");
					System.out.println("확인용 : " + progressBar.getProgress());
					if(progressBar.getProgress() >= 1.0) {
						shareObj.assignNumber();
						textarea.appendText(shareObj.getNumber()+". " + name+ " " + "도착하였습니다\n");
					}
				});
				if ( k > 1.0) break;
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
	}	
}

public class Exam02_ThreadRace extends Application  {
	
	private List<String> names = Arrays.asList("홍길동","이순신","강감찬");
	
	// progressBar를 제어할 Thread가 FlowPane 1개당 1개씩 존재해야 한다.
	private List<ProgressRunnable> uRunnable =
					new ArrayList<ProgressRunnable>();
	// 실질적인 runnable객체가 리스트에 담긴다
	
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
		
		// center부분을 차지할 TilePane을 생성해야 한다!
		TilePane center = new TilePane();
		center.setPrefColumns(1); // 1열만 존재하는 TilePane
		center.setPrefRows(4);    // 4행이 존재하는 TilePane
				
		// 메세지가 출력될 TextArea 생성 및 크기 결정
		textarea = new TextArea();
		textarea.setPrefSize(600, 100);
		ShareObj shareObj = new ShareObj();
		// 이제 만들어진 TilePane에 3개의 FlowPane과 TextArea를 부착
		for(String name : names) {
			UserPanel panel = new UserPanel(name);
			center.getChildren().add(panel);
			uRunnable.add(
					new ProgressRunnable(panel.getNameFied().getText(),
							panel.getProgressBar(),
							panel.getProgressIndicator(),
							textarea,shareObj));
		}
		center.getChildren().add(textarea);
		
		root.setCenter(center);
		
		btn = new Button("버튼 클릭!!");
		btn.setPrefSize(250, 50);
		btn.setOnAction(t -> {
			// 버튼에서 Action이 발생(클릭)했을 때 호출!
			// uRunnable(ArrayList)를 돌면서 Thread를 생성하고
			// start() 호출
			for(ProgressRunnable runnable : uRunnable) {
				new Thread(runnable).start();
			}
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
