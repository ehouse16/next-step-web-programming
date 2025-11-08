$(document).ready(function() {

  // ===== 템플릿 문자열 포맷 함수 =====
  String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
      return typeof args[number] != 'undefined'
          ? args[number]
          : match;
    });
  };

  // ===== 답변 추가 이벤트 =====
  $(".answerWrite input[type=submit]").click(addAnswer);

  function addAnswer(e) {
    e.preventDefault();

    var queryString = $("form[name=answer]").serialize();

    $.ajax({
      type: "post",
      url: "/api/qna/addAnswer",
      data: queryString,
      dataType: "json",
      error: onError,
      success: onSuccess,
    });
  }

  // ===== Ajax 성공 콜백 =====
  function onSuccess(json, status) {
    var result = json.result;
    if (result.status) {
      var answer = json.answer;

      // 템플릿 불러오기 (중요: .text() 사용)
      var answerTemplate = $("#answerTemplate").text();

      // 포맷 치환
      var template = answerTemplate.format(
          answer.writer,
          new Date(answer.createdDate).toLocaleString(),
          answer.contents,
          answer.answerId,
          answer.answerId
      );

      // 새 답변 prepend
      $(".qna-comment-slipp-articles").prepend(template);

      // 입력창 초기화
      $("form[name=answer] textarea[name=contents]").val("");

    } else {
      alert(result.message);
    }
  }

  // ===== Ajax 오류 콜백 =====
  function onError(xhr, status) {
    alert("에러가 발생했습니다. 잠시 후 다시 시도해주세요.");
  }

  // ===== 답변 삭제 이벤트 (동적 바인딩) =====
  $(".qna-comment").on("click", ".form-delete", deleteAnswer);

  function deleteAnswer(e) {
    e.preventDefault();

    var deleteBtn = $(this);
    var queryString = deleteBtn.closest("form").serialize();

    $.ajax({
      type: "post",
      url: "/api/qna/deleteAnswer",
      data: queryString,
      dataType: "json",
      error: function(xhr, status) {
        alert("삭제 중 오류가 발생했습니다.");
      },
      success: function(json, status) {
        var result = json.result;
        if (result.status) {
          deleteBtn.closest("article").remove();
        } else {
          alert(result.message);
        }
      }
    });
  }

}); // $(document).ready() 끝