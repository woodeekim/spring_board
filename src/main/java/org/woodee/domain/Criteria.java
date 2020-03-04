package org.woodee.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@ToString
public class Criteria {

    private int pageNum;
    private int amount;

    private String type;
    private String keyword;

    public Criteria(){
        this(1,10);
    }

    public Criteria(int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
    }

    public String[] getTypeArr(){
        return type == null ? new String[] {} : type.split("");
    }

    //UriComponentsBuilder 를 이용하는 링크 생성
    /*
        - 여러 개의 파라미터들을 연결해서 URL 형태로 만들어주는 기능
        - URL 을 만들어주면 리다이렉트 하거나, <form> 태그를 사용하는 상황이
          많이 줄인다.
    */
    public String getListLink(){
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
                .queryParam("pageNum", this.pageNum)
                .queryParam("amount", this.getAmount())
                .queryParam("type", this.getType())
                .queryParam("keyword", this.getKeyword());
        return builder.toUriString();
    }

}
