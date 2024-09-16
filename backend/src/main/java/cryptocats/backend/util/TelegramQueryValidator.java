package cryptocats.backend.util;

import cryptocats.backend.exception.ExpiredAuthDateException;
import cryptocats.backend.exception.NoParamInQueryException;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

@Component
public class TelegramQueryValidator {

    // TODO: algorithm depends on that variable
    @Value("${telegram.signature.algorithm}")
    private String algorithm;

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.signature.key}")
    private String key;

    private static final String SEPARATOR = "&";
    private static final String DELIMITER = "\n";
    private static final String HASH = "hash=";
    private static final String AUTH_DATE = "auth_date=";

    private static final Long INTERVAL = (long) (60 * 60 * 4);

    private long getTimeNow() {
        return Instant.now().getEpochSecond();
    }

    // TODO: Clean up code!!!
    // TODO: Improve algorithm of search
    public boolean validate(String query) throws NoParamInQueryException, ExpiredAuthDateException {
        String[] params = query.split(SEPARATOR);

        long authDate = Long.parseLong(Arrays.stream(params)
                .filter(param -> param.startsWith(AUTH_DATE))
                .findFirst()
                .orElseThrow(() -> new NoParamInQueryException("No auth date in the query"))
                .substring(AUTH_DATE.length()));

        if (getTimeNow() - INTERVAL > authDate) {
            throw new ExpiredAuthDateException("Auth date has expired");
        }

        String hash = Arrays.stream(params)
                .filter(param -> param.startsWith(HASH))
                .findFirst()
                .orElseThrow(() -> new NoParamInQueryException("No hash in the query"))
                .substring(HASH.length());

        String dataCheckString = Arrays.stream(params)
                .filter(string -> !string.startsWith("hash="))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.joining(DELIMITER));

        byte[] hmacSecret = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, key).hmac(botToken);
        String calculatedHash = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, hmacSecret).hmacHex(dataCheckString);

        return calculatedHash.equals(hash);
    }
}
