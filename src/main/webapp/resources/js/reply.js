/*
    즉시 실행함수
    - 즉시 실행함수는 ()안에 함수를 선언하고
      바깥쪽에서 실행하는 걸 말한다.
    - 즉시 실행함수는 함수의 실행 결과가 바깥쪽에
      선언된 변수에 할당된다. (할당된다는 말이 중요한 것 같다.)
    - 말로하자면 replyService 변수는 즉시실행함수에 의해
      name 이라는 속성에 AAA 라는 속성값을 가진 객체가 할당된다.

var replyService = (function () {
    return {name:"AAAA"};
})();
*/
console.log("Reply Module......");
var replyService = (function () {

    function add(reply, callback, error) {
        console.log("add reply using ajax");

        $.ajax({
            type : 'post',
            url : '/replies/new',
            data : JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            success : function (result, status, xhr) {
                if(callback) {
                    callback(result);
                }
            },
            error : function (xhr, status, er) {
                if(error){
                    error(er);
                }
            }
        })//ajax end
    }
    return {add:add};
})();