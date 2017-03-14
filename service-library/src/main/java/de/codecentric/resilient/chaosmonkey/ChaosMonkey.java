package de.codecentric.resilient.chaosmonkey;

import com.netflix.config.DynamicIntProperty;
import org.apache.commons.lang3.RandomUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;

@Aspect
@Component
@Profile("chaos")
public class ChaosMonkey {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChaosMonkey.class);

    private DynamicBooleanProperty chaosMonkey =
        DynamicPropertyFactory.getInstance().getBooleanProperty("chaos.monkey.active", false);

    private DynamicIntProperty chaosMonkeyLevel =
            DynamicPropertyFactory.getInstance().getIntProperty("chaos.monkey.level", 3);

    public ChaosMonkey() {

        String chaosMonkeyStatus = chaosMonkey.get() ? "bad mood or evil" : "Eats bananas or sleeps" ;

        LOGGER.info("\n\n---       Chaos Monkey       ---\n            __,__\n" +
                "   .--.  .-\"     \"-.  .--.\n" +
                "  / .. \\/  .-. .-.  \\/ .. \\\n" +
                " | |  '|  /   Y   \\  |'  | |\n" +
                " | \\   \\  \\ 0 | 0 /  /   / |\n" +
                "  \\ '- ,\\.-\"`` ``\"-./, -' /\n" +
                "   `'-' /_   ^ ^   _\\ '-'`\n" +
                "       |  \\._   _./  |\n" +
                "       \\   \\ `~` /   /\n" +
                "        '._ '-=-' _.'\n" +
                "           '~---~'\n" +
                " Status: " + chaosMonkeyStatus +
                "\n------------------------------------ -\n");
    }

    @Around("execution(* de.codecentric.resilient..*.*Service.*(..))")
    public Object createConnoteHystrix(ProceedingJoinPoint pjp) throws Throwable {
        LOGGER.debug(LOGGER.isDebugEnabled() ? "After Connote Service Call: createConnoteChaos()" : null);

        chaosMonkey();

        return pjp.proceed();
    }

    private void chaosMonkey() {
        if (chaosMonkey.get()) {
            // Trouble?
            int troubleRand = RandomUtils.nextInt(0, 10);
            int exceptionRand = RandomUtils.nextInt(0, 10);

            if (troubleRand > 5) {
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