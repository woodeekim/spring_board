package org.woodee.controller;

import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration

@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
})
@Log4j2
public class BoardControllerTests {

    @Setter(onMethod_ = {@Autowired} )
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testList() throws Exception{
        log.info(
                mockMvc.perform(MockMvcRequestBuilders.get("/board/list"))
                .andReturn()
                .getModelAndView()
                .getModelMap());
    }

    @Test
    public void testRegister() throws Exception{
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/register")
        .param("title", "테스틑 새글 제목")
        .param("content",  "새글 테스트 내용")
        .param("writer", "김가짜")
        ).andReturn().getModelAndView().getViewName();

        log.info("뭐가나오지?" + resultPage);
    }

    @Test
    public void testGet() throws Exception {
        log.info(mockMvc.perform(MockMvcRequestBuilders.get("/board/get")
        .param("bno","2"))
        .andReturn()
        .getModelAndView().getModelMap());
    }

    @Test
    public void testModify() throws Exception{
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/modify")
        .param("bno","1")
        .param("title", "수정된 테스트 새글 제목이다")
        .param("content", "수정된 테스트 새글 내용이다.")
        .param("writer", "김수정"))
        .andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }

    @Test
    public void testRemove() throws Exception {
        String resultPage = mockMvc.perform(MockMvcRequestBuilders.post("/board/remove")
        .param("bno","2")
        ).andReturn().getModelAndView().getViewName();

        log.info(resultPage);
    }

}