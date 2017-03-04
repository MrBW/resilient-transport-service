package de.codecentric.resilient.chaosmonkey;

import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ChaosMonkey {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChaosMonkey.class);

    @Around("execution(* com.codecentric.workshop.hystrix.demo.service.ConnoteService.createConnoteChaos(..))")
    public Object createConnoteHystrix(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "After Connote Service Call: createConnoteChaos()" : null);

        chaosMonkey();

        return pjp.proceed();
    }

    private void chaosMonkey() {
        // Trouble?
        int troubleRand = RandomUtils.nextInt(0, 10);
        int exceptionRand = RandomUtils.nextInt(0, 10);

        if (troubleRand > 3) {
            LOGGER.debug("Chaos Monkey - generates trouble");
            // Timeout or Exception?
            if (exceptionRand < 5) {
                LOGGER.debug("Chaos Monkey - timeout");
                // Timeout
                generateTimeout();
            } else {
                LOGGER.debug("Chaos Monkey - exception");
                // Exception
                throw new RuntimeException("Chaos Monkey - RuntimeException");
            }
        }
    }

    /***
     * Generates a timeout exception, 3000ms
     */
    private void generateTimeout() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // do nothing, hystrix tries to interrupt
        }
    }

}