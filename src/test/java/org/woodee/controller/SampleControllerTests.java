package org.woodee.controller;


import com.google.gson.Gson;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.woodee.domain.Ticket;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({
        "file:src/main/webapp/WEB-INF/applicationContext.xml",
        "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml"
})
@WebAppConfiguration
@Log4j2
public class SampleControllerTests {

    @Setter(onMethod_ = {@Autowired})
    private WebApplicationContext ctx;

    @Setter(onMethod_ = {@Autowired})
    private SampleController sampleController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).build();
    }

    @Test
    public void testConvert() throws Exception {
        //외부에서 /ticket/+Post 형식으로 파라미터 보내면 @RequestBody를 통해 잘 받는지 테스트
        Ticket ticket = new Ticket();
        ticket.setTno(123);
        ticket.setOwner("Admin");
        ticket.setGrade("AAA");

        //유닛테스트에서 JSON 형태로 만드는 법 (Gson 라이브러리를 사용)
        //외부에서 JSON 형태로 보내기 때문에 ticket 을 JSON 형태로 만든다.
        String jsonStr = new Gson().toJson(ticket);

        log.info(jsonStr);

        mockMvc.perform(post("/sample/ticket")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonStr))
                .andExpect(status().is(200));


    }

    @Test
    public void testController(){
        String name = "woodee";
        int age = 20;
        ArrayList list = new ArrayList();
        list.add("one");
        list.add("two");
        list.add("three");

        String[] names = new String[2];
        for(int i=0 ; i<=names.length-1; i++){
            names[i]="hello"+i;
        }
        log.info("Test -> String, int");
        log.info(sampleController.ex02(name,age));
        log.info("Test -> ArrayList");
        log.info(sampleController.ex02List(list));
        log.info("Test -> String[]");
        log.info(sampleController.ex02Array(names));
    }


}
