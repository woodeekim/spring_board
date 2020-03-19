package org.woodee.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.woodee.domain.Criteria;
import org.woodee.domain.ReplyVO;
import org.woodee.service.ReplyService;

import java.util.List;

@RequestMapping("/replies/")
@RestController
@Log4j2
@AllArgsConstructor
/*
    lombok 에서 생성자를 만들어주는 어노테이션은
    -1. @NoArgsConstructor
    -2. @AllArgsConstructor
    -3. @RequiredArgsConstructor
*/
public class ReplyController {

    private ReplyService replyService;

    //댓글 등록
    /*
        - Mapping의 consumes 와 produces 속성을 이용해
          JSON 방식의 데이터만 처리하고 문자열을 반환하도록 설계
        - @RequestBody를 적용해 JSON 데이터를 ReplyVO 탕비으로 변환하도록 지정
        - 200 OK 혹은 500 Internal Server Error 반환
     */
    @PostMapping(value = "/new",
            consumes = "application/json",
            produces = {MediaType.TEXT_PLAIN_VALUE})
    public ResponseEntity<String> create(@RequestBody ReplyVO replyVO) {
        log.info("ReplyVO : " + replyVO);

        int insertCount = replyService.register(replyVO);
        log.info("Reply insert count  : " + insertCount);

        return insertCount == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //1개의 게시물의 댓글들 조회
    @GetMapping(value = "/pages/{bno}/{page}",
        produces = {
            MediaType.APPLICATION_XML_VALUE,
            MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<List<ReplyVO>> getList(
            @PathVariable("bno") Long bno,
            @PathVariable("page") int page) {

        log.info("getList...........");
        //Criteria(pageNum,amount);
        Criteria criteria = new Criteria(page, 10);
        log.info(criteria);

        return new ResponseEntity<>(replyService.getListWithPaging(criteria,bno),HttpStatus.OK);
    }
    //1개의 댓글 조회
    @GetMapping(value = "/{rno}",
        produces = { MediaType.APPLICATION_XML_VALUE,
                    MediaType.APPLICATION_JSON_VALUE })
    public ResponseEntity<ReplyVO> get(@PathVariable("rno") Long rno) {
        log.info("get : " + rno);

        return new ResponseEntity<>(replyService.get(rno),HttpStatus.OK);
    }
    //댓글 삭제
    /*
        ResponseEntity
        - ResponseEntity는 HttpEntity를 상속받음으로써
          HttpHeader 와 body를 가질 수 있다.
        - ResponseEntity는 status field를 가지기 때문에
          상태코드를 필수적으로 리턴해줘야 한다.
        - 사용하는 이유는 응답 코드, 헤더, 본문을 다루기 위해서
          사용한다.
    */
    @DeleteMapping(value = "/{rno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("rno") Long rno) {
        log.info("remove : " + rno);

        return replyService.remove(rno) == 1
                ? new ResponseEntity<>("test",HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    //댓글 수정
    /*
        - HTTP PATCH 메소드는 리소스의 부분적인 수정을 할 때 사용한다.
        <-> HTTP PUT 메소드는 완전한 교체만을 허용한다.
     */
    @RequestMapping(value = "/{rno}",
            method = { RequestMethod.PUT, RequestMethod.PATCH },
            consumes = "application/json",
            produces = { MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> modify(
            @RequestBody ReplyVO replyVO,
            @PathVariable("rno") Long rno ) {
        /*
            @RequestBody 를 통해서 이미 json 데이터가 ReplyVO 타입으로
            변환해서 저장이 된 상태(?) 라고 이해중이다.
         */
        replyVO.setRno(rno);

        log.info("rno : " + rno);
        log.info("modify : " + replyVO);

        return replyService.modify(replyVO) == 1
                ? new ResponseEntity<>("success", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    
}

