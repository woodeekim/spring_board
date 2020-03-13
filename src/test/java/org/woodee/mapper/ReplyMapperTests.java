package org.woodee.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.woodee.domain.Criteria;
import org.woodee.domain.ReplyVO;

import java.util.List;
import java.util.stream.IntStream;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j2
public class ReplyMapperTests {
    //@ContextConfiguration
    /*   20-03-11
       - 속성값으로 applicationContext.xml을 설정한 이유는
         없으면 HICARI 설정부분과 / DataSource를 인식하지 못한다. 없으면 OracleDB에 접근을
         아예 못하게 된다.(?) / 확실하지는 않으니 단정 짓지 말자 /

       - 안했을 시 : java.lang.IllegalStateException: Failed to load ApplicationContext 메세지
    */

    @Setter(onMethod_ = {@Autowired})
    private ReplyMapper mapper;

    //해당 게시물 번호 있는지 항상 확인(DB는 값이 바뀌니까)
    private Long[] bnoArr = {100L,101L, 102L, 103L,104L};

    //ReplyMapper 객체를 정상적으로 사용할 수 있는지 Test
    @Test
    public void testMapper() {
        log.info(mapper);
    }

    //외래키를 이용한 등록 테스트
    @Test
    public void testInsert() {

        IntStream.rangeClosed(1,10).forEach(i -> {

            ReplyVO vo = new ReplyVO();
            vo.setBno(bnoArr[i % 5]);
            vo.setReply("댓글 테스트" + i);
            vo.setReplyer("replyer" + i);

            mapper.insert(vo);
        });
    }

    //조회
    @Test
    public void testRead(){
        ReplyVO vo = new ReplyVO();
        vo.setRno(5L);

        log.info(mapper.read(vo.getRno()));
    }

    //삭제
    @Test
    public void testDelete(){
        //다음과 같은 방법이면 굳이 VO 객체를 생성해서 값을 안 넣어줘도 된다
        Long targetRno = 5L;
        mapper.delete(targetRno);
    }

    //수정
    @Test
    public void testUpdate() {
        Long targetRno = 6L;
        ReplyVO vo = mapper.read(targetRno);
        vo.setReply("댓글 UPDATE 합니다");
        int count = mapper.update(vo);
        log.info("업데이트 숫자" + count);
    }

    //(페이징된)특정 게시물의 댓글 불러오기
    /*java.lang.Exception: No tests found matching Method testList(org.woodee.mapper.ReplyMapperTests) */
    //아! Test 어노테이션을 안했기 때문에 당연히 testList() 메소를 찾을 수 없었다.
    @Test
    public void testList(){
        Criteria cri = new Criteria();
        Long targetBno = 120L;
        List<ReplyVO> replies = mapper.getListWithPaging(cri,targetBno);

        replies.forEach(reply ->log.info(reply));
    }


}