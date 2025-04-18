package edu.kh.todo.model.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.kh.todo.model.mapper.TodoMapper;

@Repository // DAO 계층 역할 명시 + Bean 등록
// Mybatis 사용시, @Mapper는 필수
// @Repository만 사용 => 불가능, @Mapper만 사용 => 가능
public class TodoDAO {
	
	@Autowired // 의존성주입(DI) -> 같은 타입 + 상속관계 Bean을 의존성 주입(DI)
	private TodoMapper mapper; // mapper에는 TodoMapper의 구현체가 의존성 주입됨
							   // 해당 구현체가 sqlSessionTemplate 이용

	public String testTitle() {
		// 결과 저장용 변수 선언
		// SQL 작성
		return mapper.testTitle();
	}

}
