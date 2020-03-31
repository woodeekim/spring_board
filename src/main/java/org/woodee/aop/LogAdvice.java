package org.woodee.aop;
/*
* AOP 개념에서 관심사를 실제로 구현하기 위한 클래스
* - 지금까지 해왔던 수많은 로그를 기록하는 일은
*   반복적이면서 핵심 로직은 아니지만 필요한
*   기능이기 때문에 관심사로 간주할 수 있다.
*/

import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/*
* AOP
* - 코드를 작성하지 않았는데 다른 코드와 같이 결합되어서 동작한다.
* - AOP 를 이용해서 좀 더 구체적인 처리를 하고 싶으면 다음 설정을 해야한다.
*   1. @Around
*   2. ProceedingJoinPoint
*/
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
    @Before("execution(* org.woodee.service.SampleService*.*(..))")
    public void logBefore() {
        log.info("==========AOP===========");
    }

    /*
     * args 를 이용한 파라미터 추적
     * - @Before어노테이션과 해당 문자열은 어떤 위치에 Advice를
     *   적용할 것인지를 결정하는 Pointcut이다.
     * - 뒤쪽의 &&args() 안에 변수명을 지정하는데, 이 2종류의 정보를 이용해서
     *   logBeforeWithParam() 메서드의 파라미터를 설정하게 된다.
     * - && args를 이용하는 설정은 간단히 파라미터를 찾아서 기록할 때에는 유용하지만
     *   파라미터가 다른 여러 종류의 메서드에 적용하는 데에는 간단하지 않다는 단점이 있다.
     *      - 이에 대해서는 @Around와 ProceedingJoinPoint를 이용해서 해결 할 수 있다고 한다.
    */
    @Before("execution(* org.woodee.service.SampleService*.doAdd(String, String)) && args(str1, str2)")
    public void logBeforeWithParam(String str1, String str2){
        log.info("str1: " + str1 );
        log.info("str2: " + str2 );
    }

    /*
    * @AfterThrowing
    * - 지정된 대상이 예외를 발생한 후에 동작하면서
    *   문제를 찾을 수 있도록 도와주는 어노테이션이다.
    */
    @AfterThrowing(pointcut = "execution(* org.woodee.service.SampleService*.*(..))",
        throwing = "exception")
    public void logException(Exception exception) {
        log.info("Exception...!!!");
        log.info("exception: " + exception);
    }

    /*
    * @Around와 @Around와 같이 결합해서 쓰는 ProceedingJoinPoint
    * - ProceedingJoinPoint 파라미터를 지정하면 AOP의 대상이 되는
    *   Target이나 파라미터 등을 파악할 뿐만 아니라, 직접 실행을
    *   결정할 수도 있다.
    * - @Around가 적용되는 메서드의 경우 리턴 타입이 void가 아닌 타입으로
    *   설정과, 메서드의 실행 결과도 직접 반환하는 형태로 작성해야 한다.
    * - 테스트 실행 결과를 보면 @Around가 먼저 동작하고, @Before이 실행된 후
    *   메서드가 실행되는데 걸린 시간이 로그로 기록된다.
    */
    @Around("execution(* org.woodee.service.SampleService*.*(..))")
    public Object logTime(ProceedingJoinPoint pjp) {
        long start = System.currentTimeMillis();

        log.info("Target: " + pjp.getTarget());
        log.info("Param: " + Arrays.toString(pjp.getArgs()));

        Object result = null;

        try {
            result = pjp.proceed();
        } catch (Throwable e) {
            e.printStackTrace();
        } //end try~catch

        long end = System.currentTimeMillis();
        log.info("TIME: " + (end-start));

        return result;
    }
}
