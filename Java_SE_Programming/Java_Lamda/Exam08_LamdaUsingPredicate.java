package Java_Lamda;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

/*
 * Predicate는 입력매개변수가 있다. boolean 리턴
 * 사용되는 method는 testXXX() 가 사용된다!!
 * 입력매개변수를 조사하여 true, false값을 리턴해야 하는 경우
 * 
 * 학생객체를 만들어서 List로 유지할거에요
 * static method를 만들어서 람다식으로 인자를 넘겨줄거에요
 * 성별에 따른 특정 과목의 평균을 구할 수 있도록 method를 작성해보자
 */

public class Exam08_LamdaUsingPredicate {
	private static List<Exam08_Student> students =
			Arrays.asList(
					new Exam08_Student("홍길동",10,20,30,"남자"),
					new Exam08_Student("박길동",20,90,60,"남자"),
					new Exam08_Student("신사임당",30,30,90,"여자"),
					new Exam08_Student("육관순",80,90,100,"여자"),
					new Exam08_Student("이순신",60,90,10,"남자"));
	
	// static method를 하나 정의하는데 성별에 따른 특정 과목의 평균을 구하는
	// 작업을 할 것이다
	private static double avg(Predicate<Exam08_Student> predicate,
			ToIntFunction<Exam08_Student> function) {
	// boolean타입으로 파라미터를 받아서 integer 타입으로 리턴한다
		int sum = 0;
		int count = 0;
		
		for(Exam08_Student s : students) {
			if(predicate.test(s)) {
				count++;
				sum += function.applyAsInt(s);
			}
		}
		return (double)sum / count;
	}
	public static void main(String[] args) {

		System.out.println(avg(t -> t.getGender().equals("남자"),t -> t.getMath()));
		System.out.println(avg(t -> t.getGender().equals("여자"),t -> t.getEng()));
		// 입력은 객체로 들어가고 나오는값은 True , False로 나온다
		// 객체가 들어가서 인티져의 값이 리턴된다
	}

}

class Exam08_Student{
	private String name;   // 학생이름
	private int kor;       // 국어성적
	private int eng;       // 영어성적
	private int math;      // 수학성적
	private String gender; //성별
	public Exam08_Student() {
		//default 생성자
	}
	public Exam08_Student(String name, int kor, int eng, int math, String gender) {
		super();
		this.name = name;
		this.kor = kor;
		this.eng = eng;
		this.math = math;
		this.gender = gender;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMath() {
		return math;
	}
	public void setMath(int math) {
		this.math = math;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	
}

