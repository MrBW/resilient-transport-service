package de.codecentric.resilient.connote.utils;

import com.netflix.config.DynamicLongProperty;
import com.netflix.config.DynamicPropertyFactory;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

/**
 * @author Benjamin Wilms (xd98870)
 */
@Service
public class RandomConnoteGenerator {

    // Connote start range
    private DynamicLongProperty startRange =
            DynamicPropertyFactory.getInstance().getLongProperty("connote.range.start", 1000000);

    // Connote end range
    private DynamicLongProperty endRange = DynamicPropertyFactory.getInstance().getLongProperty("connote.range.end", 5000000);

    public long randomNumber() {
        return RandomUtils.nextLong(startRange.get(), endRange.get());
    }
}
