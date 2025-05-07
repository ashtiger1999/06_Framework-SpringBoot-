// 1. #boardLike가 클릭 되었을때
document.querySelector("#boardLike").addEventListener("click", (e) => {

    // 2. 로그인 상태가 아닌 경우 동작 X
    if (loginMemberNo == null){
      alert("로그인 후 이용해주세요");
      return;
    }
  
    // 3. 준비된 3개의 변수를 객체로 저장(JSON 변환 예정)
    const obj = {
      "memberNo" : loginMemberNo,
      "boardNo" : boardNo,
      "likeCheck" : likeCheck
    }
  
    // 4. 좋아요 INSERT/DELETE 비동기 요청
    fetch("/board/like", {
      method: "POST",
      headers: {"Content-Type" : "application/json"},
      body: JSON.stringify(obj)
    })
    .then(resp => resp.text())
    .then(count => {
      
      if(count == -1){
        console.log("좋아요 처리 실패");
        return;
      }
  
      // 5. likeCheck 값 0 <-> 1 변환
      // -> 클릭 될 때마다 INSERT/DELETE 동작을 번갈아 가면서 하게끔.
      likeCheck = likeCheck == 0 ? 1 : 0;
  
      // 6. 하트를 채웠다/비웠다 바꾸기
      e.target.classList.toggle("fa-regular"); // 빈 하트
      e.target.classList.toggle("fa-solid"); // 채워진 하트
  
      // 7. 게시글 좋아요 수 수정
      e.target.nextElementSibling.innerText = count;
    })
  
  });

// ----------------- 게시글 수정 버튼 -----------------

const updateBtn = document.querySelector("#updateBtn");

if(updateBtn != null) { // 수정 버튼 존재 시
  
  updateBtn.addEventListener("click",()=>{
    // get 방식
    // 현재 : /board/1/2004?cp=1
    // 목표 : /editBoard/1/2001/update?cp=1
    location.href = location.pathname.replace('board','editBoard')
                  + "/update"
                  + location.search;
  })

}