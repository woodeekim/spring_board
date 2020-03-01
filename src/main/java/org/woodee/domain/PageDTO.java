package org.woodee.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PageDTO {

    private int startPage;
    private int endPage;
    private boolean prev, next;

    private int total;
    private Criteria cri;

    public PageDTO(Criteria cri, int total) {
        this.cri = cri;
        this.total = total;
        /*현재페이지에서 10.0으로 나눈뒤 ceil()로 소수점 이하를 올려서
        (보고있는 페이지를 기준) 마지막 페이지를 구한다.*/
        this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0))*10;
        this.startPage = this.endPage-9;
        /*끝 번호는 전체 데이터에 영향을 받기 때문에 검증해야된다.
        amount 멤버변수는 한페이지에 얼마나 나타낼지에 대한 값이다*/
        int realEnd = (int)(Math.ceil((total*1.0) / cri.getAmount()));
        if(realEnd < this.endPage){
            this.endPage = realEnd;
        }
        //prev와 next 는 boolean 타입
        this.prev = this.startPage > 1;
        this.next = this.endPage < realEnd;
    }
}
