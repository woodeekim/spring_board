package org.woodee.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.woodee.domain.BoardVO;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j2
public class BoardServiceTests {

    @Setter(onMethod_ = {@Autowired} )
    private BoardService service;

    @Test
    public void testExist(){
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void testRegister() {
        BoardVO board = new BoardVO();
        board.setTitle("제목");
        board.setContent("내용");
        board.setWriter("Woodee");
        //--Controller 에서 파라미터 받았다고 생각
        service.register(board);
        //service interface 의 추상메소드를 통해 구현객체의 해당 mapper interface가 등록된 xml에
        //mybatis 로 실행 후 결과값이 있으면 리턴
        log.info("생성된 게시물의 번호" + board.getBno());

    }

    @Test
    public void testGet() {
        log.info(service.get(2L));
    }

    @Test
    public void testModify() {
        //굳이 BoardVO 객체를 만들지 않고 기존 데이터를 불러오면 되는구나!
        BoardVO board = service.get(2L);
        if(board == null){
            return;
        }
        board.setTitle("이거 진짜 바뀌나?");
        log.info("Modify result : "+service.modify(board));
    }

    @Test
    public void testRemove() {
        log.info("Remove result : " + service.remove(1L));
    }

    @Test
    public void getList() {
        List<BoardVO> list = service.getList();
        service.getList().forEach(board -> log.info(board));
    }
}