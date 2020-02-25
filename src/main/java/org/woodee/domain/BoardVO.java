package org.woodee.domain;

import lombok.Data;

import java.util.Date;

@Data
public class BoardVO {
    /*
BNO
TITLE
CONTENT
WRITER
REGDATE
UPDATEDATE
    */
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private Date regdate;
    private Date updatedate;

}
