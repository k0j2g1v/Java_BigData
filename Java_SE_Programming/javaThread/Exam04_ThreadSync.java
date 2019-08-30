package javaThread;

/*
 * 2개의 Thread를 파생시켜서 공용객체를 이용하도록 만들어 보자
 * Thread가 공용객체를 동기화해서 사용하는 경우와 그렇지 않은 경우를
 * 비교해서 이해해보자
 * 
 * 공용객체를 만들기 위한 class를 정의해 보자!
 */
class SharedObj {
	private int number; // 공용객체가 가지는 field

	// getter & setter (Thread에 의해서 사용된다)
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	// Thread에 의해서 사용이 되는 business method
	// synchronized keyword로 동기화를 할 수 있다
	// mothod동기화는 효율이 좋지 않다
	// 동기화 block을 이용해서 처리하는게 일반적이다
	public void assignNumber(int number) {
		synchronized (this) {
			this.number = number;

			try {
				Thread.sleep(3000);
				System.out.println("현제 공용객체의 number : " + this.number);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

// Runnable interface를 구현한 class(Thread 생성자의 인자로 이용하기 위해)
class MyRunnable implements Runnable {

	SharedObj shared;
	int input;

	public MyRunnable(SharedObj shared, int input) {
		this.shared = shared;
		this.input = input;
	}

	@Override
	public void run() {
		shared.assignNumber(input);

	}

}

public class Exam04_ThreadSync {

	public static void main(String[] args) {

		// 공용객체를 하나 생성한다
		SharedObj shared = new SharedObj();

		// Thread를 생성(2개) - 공용객체를 가지는 Thread를 생성
		Thread t1 = new Thread(new MyRunnable(shared, 100));
		Thread t2 = new Thread(new MyRunnable(shared, 200));
		// Thread 실행( runnable상태로 전환 )
		t1.start();
		t2.start();

	}

}
