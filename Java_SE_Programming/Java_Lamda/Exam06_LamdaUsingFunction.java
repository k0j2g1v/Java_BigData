package Java_Lamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

/*
 * Function 함수적 인터페이스는 입력매개변수와 리턴값이 applyxxx() method가
 * 제공된다
 * 일반적으로 입력매개변수를 리턴값으로 mapping시킬 때 일반적으로 사용된다
 * 
 * Function<T,R> func = t -> { return ~~~ };
 * T : 입력 매개변수의 generic
 * R : 리턴값의 generic
 */
// Student VO class를 정의한다!
class Exam06_Student {

	private String sName;
	private int sKor;
	private int sEng;
	private int sMath;

	public Exam06_Student() {

	}

	public Exam06_Student(String sName, int sKor, int sEng, int sMath) {
		super();
		this.sName = sName;
		this.sKor = sKor;
		this.sEng = sEng;
		this.sMath = sMath;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public int getsKor() {
		return sKor;
	}

	public void setsKor(int sKor) {
		this.sKor = sKor;
	}

	public int getsEng() {
		return sEng;
	}

	public void setsEng(int sEng) {
		this.sEng = sEng;
	}

	public int getsMath() {
		return sMath;
	}

	public void setsMath(int sMath) {
		this.sMath = sMath;
	}

}

public class Exam06_LamdaUsingFunction {

	private static List<Exam06_Student> students = Arrays.asList(new Exam06_Student("홍길동", 10, 20, 30),
			new Exam06_Student("김길동", 50, 60, 70), new Exam06_Student("이순신", 90, 20, 30),
			new Exam06_Student("신사임당", 10, 100, 70));

	private static void printName(Function<Exam06_Student, String> function) {
		for (Exam06_Student s : students) {
			System.out.println(function.apply(s));
		}
	}

	// 내가 해본것
//	private static void getAvg(Function<Exam06_Student, Integer> AvgFunc) {
//		for(Exam06_Student s : students) {
//			System.out.println(AvgFunc.apply(s)/3);
//		}
//	}
	
	// 안좋은 케이스
//	private static double getAvg(String subject) {
//		int sum = 0;
//		if (subject.equals("KOR")) {
//			// 국어총점
//			for (Exam06_Student s : students) {
//				sum += s.getsKor();
//			}
//		}
//		if (subject.equals("MATH")) {
//			// 수학총점
//			for (Exam06_Student s : students) {
//				sum += s.getsMath();
//			}
//		}
//		if (subject.equals("ENG")) {
//			// 영어총점
//			for (Exam06_Student s : students) {
//				sum += s.getsEng();
//			}
//		}
//		return (double) sum / students.size();
//	}
//
	// 방법 1
//	private static double getAvg(Function<Exam06_Student, Integer> function) {
//		int sum = 0;
//		// 총점
//		for (Exam06_Student s : students) {
//			sum += function.apply(s);
//		}
//		return (double)sum / students.size();
//	}
	// 방법 2
	private static double getAvg(ToIntFunction<Exam06_Student> function) {
		int sum = 0;
		// 총점
		for (Exam06_Student s : students) {
			sum += function.applyAsInt(s);
		}
		return (double)sum / students.size();
	}
	

	public static void main(String[] args) {

		// 학생이름을 출력 !!
		printName(t -> {
			return t.getsName();
		});

		// getAvg()라는 static method을 만들어서 다음의 내용을 출력하세요
		// 학생들의 국어성적 평균 -> getAvg()
//		System.out.println("국어평균 : " + getAvg("KOR"));
		System.out.println("국어평균 : " + getAvg(t -> t.getsKor()));
		// 학생들의 영어성적 평균 -> getAvg()
		System.out.println("영어평균 : " + getAvg(t -> t.getsEng()));
		// 학생들의 수학성적 평균 -> getAvg()
		System.out.println("수학평균 : " + getAvg(t -> t.getsMath()));
	}

}
