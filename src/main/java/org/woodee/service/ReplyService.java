package org.woodee.service;

import org.woodee.domain.Criteria;
import org.woodee.domain.ReplyVO;

import java.util.List;

public interface ReplyService {
    //등록
    public int register(ReplyVO vo);
    //1개의 댓글 조회
    public ReplyVO get(Long rno);
    //수정
    public int modify(ReplyVO vo);
    //삭제
    public int remove(Long rno);
    //(페이징된)댓글들 조회
    public List<ReplyVO> getListWithPaging(Criteria cri, Long bno);
}
