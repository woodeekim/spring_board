package org.woodee.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.woodee.domain.RestSampleVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/rest")
@Log4j2
public class RestSampleController {

    //문자열 반환
    @GetMapping(value = "/getText", produces = "text/plain; charset=UTF-8")
    public String getText(){
        log.info("MIME TYPE : " + MediaType.TEXT_PLAIN_VALUE);
        return "안녕하세요";
    }

    //객체의 반환(1/3)
    //GetMapping 의 produces 속성은 꼭 지정해야 되는 건 아니다. 즉 생략가능
    @GetMapping(value = "/getSample", produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE
    })
    public RestSampleVO getSample() {
        return new RestSampleVO(112, "스타", "로드");
    }

    @GetMapping(value = "/getSampleSecond")
    public RestSampleVO getSampleSecond() {
        return new RestSampleVO(123,"테스트", "하하하");
    }

    //객체의 반환(2/3) - 컬렉션 타입의 형식(배열, 리스트, 맵 타입)
    //List
    @GetMapping(value = "/getList")
    public List<RestSampleVO> getList() {
        return IntStream.range(1,10).mapToObj(i -> new RestSampleVO(i, i+"Fist",i+"Lasy"))
                .collect(Collectors.toList());
    }
    //Map
    @GetMapping(value = "/getMap")
    public Map<String, RestSampleVO> getMap() {

        Map<String,RestSampleVO> map = new HashMap<>();
        map.put("First", new RestSampleVO(111,"맵을","이렇게 쓰는구나!"));
        return map;
    }

    //객체의 반환(3/3) ResponseEntity - 데이터와 HTTP 헤더의 상태 메시지를 같이 전달함으로써 받는 입장에서 확실하게 결과를 알 수 있다.
    @GetMapping(value = "/check", params = {"height","weight"})
    public ResponseEntity<RestSampleVO> check(Double height, Double weight) {
        RestSampleVO vo = new RestSampleVO(0, "" + height, "" +weight);

        ResponseEntity<RestSampleVO> result = null;
        if(height < 150) {
            result = ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(vo);
        } else {
            result = ResponseEntity.status(HttpStatus.OK).body(vo);
        }
        return result;
    }


}
