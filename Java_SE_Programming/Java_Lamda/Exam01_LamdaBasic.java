package Java_Lamda;
/*
 * 함수적 프로그래밍 패턴을 위해 Java는 8버젼부터 Lamda를 지원한다.
 *  
 * 람다는 익명함수를 만들기 위한 expression(식)
 * ==> 객체지향언어보다는 함수지향적 언어에서 사용된다
 * 
 * 기존 자바 개발자들은 이 Lamda라는 개념에 익숙해지기가 쉽지 않다.
 * 그럼에도 불구하고 자바가 Lamda를 도입한 이유는 크게 2가지 정도로 생각할 수 있다.
 * 1. 코드가 간결해진다
 * 2. Java Stream을 이용하기 위해서 람다를 이용.
 * 3. Java Stream은 collection(List,Map,Set,Array..)의 처리를
 * 	   굉장히 효율적으로 할 수 있다. ( 병렬처리가 가능 )
 * 
 * 람다식의 기본 형태
 * (매개변수) -> {실행 코드}
 * 익명함수를 정의하는 형태로 되어 있지만 실제로는 익명클래스의 인스턴스를 생성
 * 
 * 람다식이 어떤 객체를 생성하느냐는 람다식이 대입되는 interface 변수가
 * 어떤 interface인가에 달려있다.
 * 
 * 일반적인 interface를 정의해서 람다식으로 표현해 보자!
 */

//// class MyThread extends Thread -> Runnable
//class MyRunnable implements Runnable {
//	public void run() {
//		System.out.println("쓰레드가 실행되요!");
//	};
//}

interface Exam01_LamdaIF {
	// 추상 method만 올 수 있다.
	// method의 정의가 없고 선언만 해주는게 추상(abstract method) method
	// 무조건 abstract 상태일것이기 때문에 생략된다 
	// abstract void myFunc();
	void myFunc(int k);
	// 람다식의 추상메소드는 무조건 하나만 존재해야한다.
	
}

public class Exam01_LamdaBasic {

	public static void main(String[] args) {

		// Thread를 생성하려고 한다!
		// 1. Thread를 subClass를 이용해서 Thread 생성
		//    그다지 좋은 방식이 아니다!
		// 2. Runnable interface를 구현한 class를 이용해서
		//    Thread를 생성. ( 더 좋은 방식 )
		// 3. Runnable interface를 구현한 익명 class를 이용해서
		//    Thread를 생성. (안드로이드에서 일반적인 형태 )

//		Runnable runnable = new Runnable() {
//			// 객체를 생성 못하는 이유는 추상 메소드가 존재하기 때문인데
//			// 이 method를 overriding하면 객체를 생성할 수 있다.
//			@Override
//			public void run() {
//								
//			}			
//		};		
		
//		Runnable runnable = () -> {
//			
//		}; // 람다식
//		
//		Thread t = new Thread(runnable);
//		t.start();
		
//		new Thread(()->  {
//			System.out.println("쓰레드 실행!!");
//		}).start();
		
		Exam01_LamdaIF sample = 	
				(int k) -> {System.out.println("출력된다");};
	    sample.myFunc(100);
	
	}

}
