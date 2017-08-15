package de.codecentric.resilient.connote.utils;

import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
@RefreshScope
public class RandomConnoteGenerator {

    // Connote start range
    private DynamicLongProperty startRange =
            DynamicPropertyFactory.getInstance().getLongProperty("connote.range.start", 1000000);

    // Connote end range
    private DynamicLongProperty endRange = DynamicPropertyFactory.getInstance().getLongProperty("connote.range.end", 5000000);

    @Value("${connote.range.start}")
    private int connoteStart;

    @Value("${connote.range.end}")
    private int connoteEnd;

    public long randomNumber() {
        startRange.get();
        endRange.get();
        return RandomUtils.nextLong(connoteStart, connoteEnd);

        //return RandomUtils.nextLong(startRange.get(), endRange.get());
    }
}
