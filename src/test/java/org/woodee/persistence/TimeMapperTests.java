package org.woodee.persistence;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.woodee.mapper.TimeMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j
public class TimeMapperTests {

    @Setter(onMethod_ = {@Autowired})
    private TimeMapper timeMapper;

    @Test
    public void testGetTime(){
        log.info(timeMapper.getTime());
        log.info(timeMapper.getClass());
    }

    @Test
    public void testGetTime2(){
        log.info("---xml + 인터페이스로 한 getTime2----");
        log.info(timeMapper.getTime2());
    }
}
