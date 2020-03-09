package org.woodee.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.woodee.domain.SampleDTO;
import org.woodee.domain.SampleDTOList;
import org.woodee.domain.Ticket;
import org.woodee.domain.TodoDTO;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


@Controller
@RequestMapping("/sample/*")
@Log4j2
public class SampleController {

    /*
    < Date 타입인 파라미터가 들어왔을 때 처리하는 방법
    1)@InitBinder 를 사용해서 Date형을 처리하는 방법
    2)파라미터로 받는 TodoDTO 객체의 멤버필드에다가 @DateTimeFormat(pattern = "yyyy/MM/dd") 로 처리하는 방법
    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(java.util.Date.class,
                new CustomDateEditor(dateFormat, false));
    }
    */


    @RequestMapping("")
    public void basic(){
        log.info("basic................");
    }

    /*파라미터를 수집하는 다양한 방법들*/

    @GetMapping("/ex01")
    public String ex01(SampleDTO dto){
        log.info(""+dto);
        return "ex01";
    }

    @GetMapping("/ex02")
    public String ex02(@RequestParam("name") String name, @RequestParam("age") int age){
        log.info("name : "+name);
        log.info("age : "+age);
        return "ex02";
    }

    @GetMapping("/ex02List")
    public String ex02List(@RequestParam("ids")ArrayList<String> ids){
        log.info("ids : " + ids);
        return "ex02List";
    }
    @GetMapping("/ex02Array")
    public String ex02Array(@RequestParam("names") String[] names){
        log.info("names : " + names);
        return "ex02Array";
    }

    @GetMapping("/ex02Been")
    public String ex02Been(SampleDTOList list){

        return "ex02Been";
    }

    @GetMapping("/ex03")
    public String ex03(TodoDTO todo){
        log.info("todo : " + todo);
        return "ex03";
    }

    /*모델 객체 없이 Javabeans의 규칙에 맞으면 다시 화면으로 보낸다.*/
   /* @GetMapping("/ex04")
    public String ex04(SampleDTO dto, int page) {
        log.info("dto : " + dto);
        log.info("page :" + page);
        return "/sample/ex04";
    }*/
   /*@ModelAttribute :  Model 에 강제로 담아서 전달하도록 할 때 쓰는 어노테이션*/
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
        log.info("dto : " + dto);
        log.info("page :" + page);
        return "/sample/ex04";
    }

    @GetMapping("/ex05")
    public void ex05(){
        log.info("/ex05...........");
    }

    /*Jackson 을 이용해서 JSON 으로 데이터를 전달*/
    @GetMapping("/ex06")
    public @ResponseBody SampleDTO ex06(){
        log.info("ex06: 오호 JSON 으로 반환화기 위해 객체를 보내는 방법이 이렇게 보내는구나.");
        SampleDTO dto = new SampleDTO();
        dto.setAge(27);
        dto.setName("woodee");

        return dto;
    }

    /*ResponseEntity 타입, 즉 http 프로토콜의 헤더로 데이터를 보낼 떄 사용*/
    @GetMapping("/ex07")
    public ResponseEntity<String> ex07(){
        log.info("/ex07................");

        String msg = "이번에는 ResponseEntity 를 통해 문자열 데이터를 전송";

        HttpHeaders header = new HttpHeaders();
        header.add("Content-type","application/json;charset=UTF-8");

        return new ResponseEntity<>(msg, header, HttpStatus.OK);
    }

    /*파일 업로드*/
    //GET
    @GetMapping("/exUpload")
    public void exUpload(){
        log.info("/exUpload..............");
    }
    //POST
    @PostMapping("/exUploadPost")
    public void exUploadPost(ArrayList<MultipartFile> files){
        files.forEach(file ->{
            log.info("---------------------------");
            log.info("name:" + file.getOriginalFilename());
            log.info("size:" + file.getSize());
        });
    }

    //@Pathvariable
    /*
     - REST 방식에서는 URL 내에 최대한 많은 정보를 담으려고 노력한다.
     - REST방식에서는 URL 자체에 데이터를 식별할 수 있는 정보들을 표현하는 경우가 많다.
       그래서 다양한 방식으로 @PathVariable이 사용된다.
     - @Pathvariable 에서는 int, double과 같은 기본자료형은 사용할 수가 없다.
    */
    @GetMapping("/product/{cat}/{pid}")
    public @ResponseBody String[] getPath(@PathVariable("cat") String cat,
                            @PathVariable("pid") Integer pid){
        return new String[]{"category: " + cat, "product id: " +pid};
    }

    //@RequestBody
    //@RequestBody를 통해서 외부에서 보내는 파라미터를 원하는 타입으로 바꿀 수 있다.
    //대부분 JSON 데이터를 서버에 보내서 원하는 타입의 객체로 사용
    @PostMapping("/ticket")
    public Ticket convert(@RequestBody Ticket ticket){
        log.info("convert....  ticket" + ticket);
        return ticket;
    }



}
