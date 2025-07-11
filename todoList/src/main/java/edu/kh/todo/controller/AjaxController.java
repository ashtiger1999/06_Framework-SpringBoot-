package edu.kh.todo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.kh.todo.model.dto.Todo;
import edu.kh.todo.model.service.TodoService;
import lombok.extern.slf4j.Slf4j;

/*
 * @ResponseBody
 * - 컨트롤러 메서드의 반환값을
 * 	 HTTP 응답 본문에 직접 바인딩하는 역할임을 명시
 * 
 * -> 컨트롤러 메서드의 반환값을
 * 비동기 요청했던 HTML/JS 파일 부분에 그대로 돌려보낼 것임을 명시
 * 
 * -> 해당 어노테이션이 붙은 컨트롤러의 메서드는
 * 	  return에 작성된 값이 forward/redirect로 인식 X
 * 
 * @RequestBody
 * - 비동기 요청시 전달되는 데이터 중 body 부분에 포함된 요청 데이터를
 * 	 알맞은 Java 객체 타입으로 바인딩하는 어노테이션
 * 
 * - 기본적으로 JSON 형식을 기대함
 * 
 * [HttpMessageConvertor]
 * Spring에서 비동기 통신 시
 * - 전달받은 데이터의 자료형
 * - 응답하는 데이터의 자료형
 * 위 두가지를 알맞은 형태로 가공(변환)해주는 객체
 * 
 * 		Java				JS
 * 문자열, 숫자 <--------> TEXT
 * 		MAP <-> JSON <-> JS Object
 * 		DTO <-> JSON <-> JS Object
 * 
 * */

@Controller				// 요청&응답 제어하는 역할 명시 + Bean 등록
@RequestMapping("ajax") // 요청주소 시작이 "ajax"인 요청을 매핑
@Slf4j
public class AjaxController {
	
	@Autowired
	private TodoService service;

	@GetMapping("main")
	public String ajaxMain() {
		return "ajax/main";
	}
	
	// 전체 Todo 개수 조회
	// -> forward/redirect를 원하는게 아님
	// -> "전체 Todo 개수"라는 데이터를
	// 	  비동기요청 보낸 클라이언트(브라우저)에 반환되는 것을 원함
	// => 반환되어야하는 결과값의 자료형을 반환형에 써야함 
	@ResponseBody
	// 반환값을 HTTP 응답 본문으로 직접 전송(값 그대로 돌려보낼거야)
	@GetMapping("totalCount")
	public int getTotalCount() {
		
		// 전체 할 일 개수 조회 서비스 호출 결과 반환받기
		int totalCount = service.getTotalCount();
		
		// 이 자리에 결과 작성하기
		return totalCount;	
	}
	
	@ResponseBody
	@GetMapping("completeCount")
	public int getCompleteCount() {
		
		return service.getCompleteCount();
	}
	
	// 할 일 추가
	@ResponseBody
	@PostMapping("add")
	public int addTodo(@RequestBody Todo todo) { // 요청 Body에 담긴 값을 Todo 객체에 저장
		// @RequestParam은 일반적으로 쿼리파라미터나 URL 파라미터에 사용
		log.debug("todo : "+todo);
		
		// 할 일 추가 서비스 호출 후 응답
		int result = service.addTodo(todo.getTodoTitle(), todo.getTodoContent());
		
		return result;
	}
	
	@ResponseBody
	@GetMapping("selectList")
	public List<Todo> selectList() {
		List<Todo> todoList = service.selectList();
		
		return todoList;
		// List(Java 전용 타입)를 반환
		// -> JS가 인식할 수 없기 때문에 JSON으로 변환 필요
		// -> HttpMessageConvertor가 JSON 형태로 변환하여 반환
	}

	@ResponseBody
	@GetMapping("detail")
	public Todo selectTodo(@RequestParam("todoNo") int todoNo) {

		return service.todoDetail(todoNo);
		// return 자료형 : Todo(dto)
		// -> HttpMessageConvertor가 JSON 형태로 변환하여 반환
	}
	
	 @ResponseBody
	 @DeleteMapping("delete")
	 public int todoDelete(@RequestBody int todoNo) {
		 return service.todoDelete(todoNo);
	 }
	 
	 @ResponseBody
	 @PutMapping("changeComplete")
	 public int changeComplete(@RequestBody Todo todo) {
		 return service.changeComplete(todo);
	 }
	 
	 @ResponseBody
	 @PutMapping("update")
	 public int updateTodo(@RequestBody Todo todo) {
		 return service.todoUpdate(todo);
	 }
	 
}