package Java_Lamda;

import java.util.function.IntBinaryOperator;

/*
 * Operator는 Function과 하는일이 거의 비슷하다
 * 입력매개변수가 있고 리턴값이 있다
 * Function은 매핑 용도로 많이 사용됨(입력매개변수를 리턴타입으로 변환, 매핑의 용도)
 * Operator는 연산용도로 많이 사용된다(입력매개변수를 연산에 이용하여 같은 타입의
 * 							     리턴값을 돌려주는 형태로 사용)
 * 
 *  최댓값과 최소값을 구하는 static method를 하나 작성해 보아요
 */
public class Exam07_LamdaUsingOperator {
	private static int arr[] = { 100, 92, 50, 89, 34, 27, 99, 3};
	
	// getMaxMin()을 static method를 만들꺼에요!
	// 사용하는 Operator는 IntBinaryOperator를 이용.
	private static int getMaxMin(IntBinaryOperator operator) {
		int result = arr[0];
		
		for(int k : arr) {
			result = operator.applyAsInt(result, k);
		}		
		return result;
	}
	
	public static void main(String[] args) {
	
		//getMaxMin("MAX"); // 최댓값을 구하는 method호출
		//getMaxMin("MIN"); // 최소값을 구하는 method호출
		//이렇게 하지 말기 -> Operator를 이용해서 처리해보자
		//getMaxMin(람다식)
		System.out.println("최댓값 : " + getMaxMin((a,b) -> {
			return a >= b ? a : b;
		}));
		
		System.out.println("최소값 : " + getMaxMin((a,b) -> {
			return a <= b ? a : b;
		}));
	}
	

}
