package org.woodee.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.woodee.domain.Criteria;
import org.woodee.domain.ReplyPageDTO;
import org.woodee.domain.ReplyVO;
import org.woodee.mapper.ReplyMapper;

import java.util.List;

@Log4j2
@Service
public class ReplyServiceImpl implements ReplyService {
    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        log.info("register......" + vo);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get......." + rno);
        return mapper.read(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        log.info("modify......" + vo);
        return mapper.update(vo);
    }

    @Override
    public int remove(Long rno) {
        log.info("remove....." + rno);
        return mapper.delete(rno);
    }
    //1개의 게시물의 댓글들(페이징)
    @Override
    public List<ReplyVO> getListWithPaging(Criteria cri, Long bno) {
        log.info("get Reply List of a Board" + bno );
        return mapper.getListWithPaging(cri, bno);
    }

    @Override
    public ReplyPageDTO getListPage(Criteria cri, Long bno) {
        return new ReplyPageDTO(
                mapper.getCountByBno(bno),
                mapper.getListWithPaging(cri,bno)
        );
    }
}

