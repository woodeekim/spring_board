package org.woodee.aop;
/*
* AOP 개념에서 관심사를 실제로 구현하기 위한 클래스
* - 지금까지 해왔던 수많은 로그를 기록하는 일은
*   반복적이면서 핵심 로직은 아니지만 필요한
*   기능이기 때문에 관심사로 간주할 수 있다.
*/

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Log4j2
@Component
public class LogAdvice {
    /*
    * execution 문자열
    * - AspectJ의 표현식이다.
    * - 맨 앞의 *은 접근제한자를 의미
    * - 맨 뒤의 *은 클래스의 이름과 메서드의 이름을 의미
    */
    @Before( "execution(* org.woodee.service.SampleService*.*(..))")
    public void logBefore() {
        log.info("==========AOP===========");
    }
}
