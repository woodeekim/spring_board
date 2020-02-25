package org.woodee.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.woodee.domain.BoardVO;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j2
public class BoardMapperTests {
    //현재 getLis 를 테스트 할 예정이면
    //1. BoardMapper 인터페이스와 getList() 메소드를 주입시키면 되나?
    // -> BoardMapper 만 주입시키면 getList 는 멤버메소드니까 BoardMapper만 하면 될듯

    @Setter(onMethod_ = {@Autowired} )
    private BoardMapper boardMapper;

    @Test
    public void testGetList() {
        //List<BoardVO> list = boardMapper.getList();
        //함수형 프로그래밍을 쓰면 짧고 효율적인 코드를 사용할 수 있어 보인다.
        boardMapper.getList().forEach(board -> log.info(board));
    }
    @Test
    public void testInsert(){
        BoardVO board = new BoardVO();
        board.setTitle("안녕");
        board.setContent("여기는 테스트 내용");
        board.setWriter("우디야");
        boardMapper.insert(board);

        log.info(board);
    }

    @Test
    public void testInsertSelectKey(){
        BoardVO board = new BoardVO();
        board.setTitle("안녕2");
        board.setContent("여기는 테스트 내용2");
        board.setWriter("우디야2");
        boardMapper.insertSelectKey(board);
        log.info(board);
    }

    @Test
    public void testRead(){
        //존재하는 게시물 번호로 테스트
        BoardVO board= boardMapper.read(5L);
        log.info(board);
    }

    @Test
    public void testDelete(){
        //test code 의 장점은 실제 DB 의 데이터가 지워지지 않기 때문에 좋다.
        log.info("delete count: " + boardMapper.delete(5L));
    }

    @Test
    public void testUpdate(){
        BoardVO board = new BoardVO();
        board.setTitle("수정해보자");
        board.setContent("내용을 수정해보자");
        board.setWriter("Moodee");
        board.setBno(1L);

        int count = boardMapper.update(board);
        log.info("UPDATE COUNT : " + count);

    }

}