package org.woodee.mapper;

import org.apache.ibatis.annotations.Param;
import org.woodee.domain.Criteria;
import org.woodee.domain.ReployVO;

import java.util.List;

public interface ReplyMapper {

    //등록(외래키 사용)
    public int insert(ReployVO vo);
    //조회
    public ReployVO read(Long rno);
    //삭제
    public int delete(Long rno);
    //수정
    public int update(ReployVO reply);
    //(페이징된)댓글 가져오기
    /*2개 이상의 파라미터를 보낼 때 방법
      1)별도의 객체 생성 2)Map이용 3)@Param 사용
    */
    public List<ReployVO> getListWithPaging(
            @Param("cri") Criteria cri,
            @Param("bno") Long bno);
}
