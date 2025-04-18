// 목록으로 버튼에 대한 동작
const goToList = document.getElementById("goToList");
goToList.addEventListener("click",()=>{
    location.href="/"; // 메인페이지로 GET 방식 요청
})

// 완료 여부 변경 버튼에 대한 동작
const completeBtn = document.querySelector(".complete-btn");
completeBtn.addEventListener("click",(e)=>{

    // 요소.dataset(e.target.dataset) : data-* 속성에 저장된 값 반환
    // data-todo-no 세팅한 속성값 얻어오기
    // (html) : data-todo-no -> js(CamelCase) : dataset.todoNo
    const todoNo = e.target.dataset.todoNo;
    let complete = e.target.innerText;

    // Y <-> N
    complete = (complete === 'Y') ? 'N' : 'Y';

    // 완료 여부 수정 요청하기
    location.href = `/todo/changeComplete?todoNo=${todoNo}&complete=${complete}`;
})

// ----------------------------------------------------------------------------------

// 수정 버튼 클릭 시 동작
const updateBtn = document.getElementById("updateBtn");
updateBtn.addEventListener("click",(e)=>{
    const todoNo = e.target.dataset.todoNo;

    location.href = `/todo/update?todoNo=${todoNo}`;
})

// ----------------------------------------------------------------------------------

// 삭제 버튼 클릭 시 동작
const deleteBtn = document.querySelector("#deleteBtn");
deleteBtn.addEventListener("click",(e)=>{
    const todoNo = e.target.dataset.todoNo;

    let isDelete = confirm("삭제하시겠습니까?");

    if(isDelete) {
        location.href = `/todo/delete?todoNo=${todoNo}`;
    }
    else {
        alert("삭제를 취소하였습니다.");
    }
})