package org.woodee.service;


import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/applicationContext.xml")
@Log4j2
public class SampleServiceTests {

    @Setter(onMethod_ = @Autowired)
    private SampleService service;

    /*testClass 메소드를 통해 Proxy 객체가 정상적으로 만들어지는지 확인 */
    @Test
    public void testClass() {
        log.info(service);
        log.info(service.getClass().getName());
    }

    @Test
    public void testAdd() throws Exception{
        log.info(service.doAdd("123","223"));
    }
}
