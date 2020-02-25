package org.woodee.controller;


import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/dispatcher-servlet.xml")
@Log4j2
public class SampleControllerTests {

    @Setter(onMethod_ = {@Autowired})
    private SampleController sampleController;

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
