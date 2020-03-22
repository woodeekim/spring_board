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

    //댓글 추가 함수
    /*
        get.jsp 에서 아직 댓글의 view 를 구현하지 않은 상태다.
        상상해보자면 댓글 등록을 누르면 스크립트 처리를 통해
        add() 함수를 호출할 것 같다.
        reply 객체 안에는 reply(내용), replyr(댓글 작성자), Model에서 받은 bno 와
        callback 함수에는

    */
    function add(reply, callback, error) {
        $.ajax({
            type : 'post',
            url : '/replies/new',
            data : JSON.stringify(reply),
            contentType : "application/json; charset=utf-8",
            //result에 담기는 값이 ajax 의 결과값인가? (결과는 success가 나오는데
            //이거에 대해서 찾아보자
            //찾는 키워드 콜백함수, ajax
            //*=>get.jsp 에서 이해하고 정리한 내용있음!
            success : function (result, status, xhr) {
                //callback 이 있으면 true
                if(callback) {
                    //callback함수를 그디어 실행시키는데 result 를 담아서 실행.
                    callback(result);
                }
            },
            error : function (xhr, status, er) {
                if(error){
                    error(er);
                }
            }
        })//ajax end
    }//add() end

    function getList(param, callback, error) {
        var bno = param.bno;
        var page = param.page || 1;

        //jQuery 에서 제공하는 gegJson() 함수는 처음 쓴다.
        $.getJSON("/replies/pages/" + bno + "/" + page + ".json",
            function (data) {
                if(callback) {
                    callback(data);
                }
            }).fail(function (xhr, status, err) {
                if(error) {
                    error();
                }
        });
    }//getList()

    function remove(rno, callback, error) {
        $.ajax({
            type : 'delete',
            url : '/replies/' + rno,
            success : function (deleteResult, status, xhr) {
                if(callback) {
                    callback(deleteResult);
                }
            },
            error : function (xhr, status, er) {
                if(error) {
                    error(er);
                }
            }
        });//ajax
    }//remove

    function update(reply, callback, error) {
        $.ajax({
            type : 'put',
            url : '/replies/' + reply.rno,
            data : JSON.stringify(reply),
            contentType : 'application/json; charset=utf-8',
            success : function (result, status, xhr) {
                if(callback){
                    callback(result);
                }
            },
            error : function (xhr, status, er) {
                if(error){
                    error(er);
                }
            }
        });//ajax
    }//update

    function get(rno, callback, error) {
        $.get("/replies/" + rno + ".json", function (result) {
            if(callback){
                callback(result);
            }
        }).fail(function (xhr, status, err) {
            if(error){
                error();
            }
        });
    }//get

    function displayTime(timeValue) {
        var today = new Date();
        var timeGapLastPost = today.getTime() - timeValue;
        var dateObj = new Date(timeValue);
        var str = "";

        //24시간 60분 60초(86,400,000초)이다.
        /*
            즉 24시간 60분 60초가 넘지 않으면 시/분/초,
            넘으면 년/월/일로 보여준다.
         */
        if(timeGapLastPost < (1000 * 60 * 60 * 24)){
            var hours = dateObj.getHours();
            var minutes = dateObj.getMinutes();
            var seconds = dateObj.getSeconds();


            //.join()은 배열의 원소들을 연결하여 하나의 값으로 만든다. (JS함수)
            return [(hours > 9 ? '' : '0') + hours, ':', (minutes > 9 ? '' : 0) + minutes,
                    ':', (seconds > 9 ? '' : '0') + seconds].join('');
        }else{
            var year = dateObj.getFullYear();
            var month = dateObj.getMonth() + 1;
            var day = dateObj.getDate();

            return [year, '/', (month > 9 ? '' : '0') + month, '/',
                (day > 9 ? '' : '0') + day].join('');
        }// end if~else
    }// end displayTime

    return {
        add:add,
        getList:getList,
        remove:remove,
        update:update,
        get:get,
        displayTime:displayTime
    };
})();
/*
    (다시 되새기기)
    - 즉시 실행함수 var yogi = (function(){})();
        - 즉시 실행함수의 결과가 변수에 할당한다.
        - 즉시 실행함수를 사용하면 자바에서 여러 메소드를 가진
          하나의 클래스와 같은 역할을 javascript 에서 할 수 있기 때문이다.
*/