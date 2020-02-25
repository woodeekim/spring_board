package org.woodee.mapper;

import org.apache.ibatis.annotations.Select;
import org.woodee.domain.BoardVO;

import java.util.List;

public interface BoardMapper {

    //XML 에서 SELECT 문을 실행시키기 위해 에노테이션 삭제
    //@Select("select * from tbl_board where bno > 0")
    public List<BoardVO> getList();

    public void insert(BoardVO board);
    public void insertSelectKey(BoardVO board);
    public BoardVO read(Long bno);
    public int delete(Long bno);
    public int update(BoardVO board);
}
