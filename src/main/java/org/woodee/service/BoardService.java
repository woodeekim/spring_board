package org.woodee.service;

import org.woodee.domain.BoardVO;

import java.util.List;

public interface BoardService
{
    //등록
    public void register(BoardVO board);
    //조회
    public BoardVO get(Long bno);
    //수정
    public boolean modify(BoardVO board);
    //삭제
    public boolean remove(Long bno);
    //리스트 조회
    public List<BoardVO> getList();
}
