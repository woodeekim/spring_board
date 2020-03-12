package org.woodee.mapper;

import org.woodee.domain.ReployVO;

public interface ReplyMapper {

    //등록(외래키 사용)
    public int insert(ReployVO vo);
    //조회
    public ReployVO read(Long rno);
    //삭제
    public int delete(Long rno);
    //수정
    public int update(ReployVO reply);
}
