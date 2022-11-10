package com.example.marketpricehandlerexercise.component;

import com.example.marketpricehandlerexercise.domain.PriceModel;
import com.example.marketpricehandlerexercise.repository.PriceModelRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ListenerComponent {
    private static final String TIMESTAMP_PATTERN = "MM-dd-yyyy HH:mm:ss:SSS";
    private final PriceModelRepository priceModelRepository;

    public void onPriceFeedMessage(String message) throws IOException, CsvException {
        new CSVReader(new StringReader(message)).readAll()
                .forEach(strings -> {
                    var priceModel = createPriceModelInstance(strings);
                    applyBidCommission(priceModel,0.1);
                    applyAskCommission(priceModel,0.1);
                    priceModelRepository.save(priceModel);
                });
    }

    private static PriceModel createPriceModelInstance(String[] strings) {
        return PriceModel
                .builder()
                .id(Long.valueOf(strings[0].strip()))
                .instrumentName(strings[1].strip())
                .bid(NumberUtils.createBigDecimal(strings[2].strip()))
                .ask(NumberUtils.createBigDecimal(strings[3].strip()))
                .timestamp(Timestamp.valueOf(convertStringIntoLocalDateTime(strings[4])))
                .build();
    }

    private static LocalDateTime convertStringIntoLocalDateTime(String string) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIMESTAMP_PATTERN);
        return LocalDateTime.from(formatter.parse(string));
    }

    protected void applyBidCommission(PriceModel priceModel,Double commissionRate) {
        var commission = priceModel.getBid().divide(BigDecimal.ONE.subtract(BigDecimal.valueOf(commissionRate).scaleByPowerOfTen(-2)),
                        RoundingMode.HALF_DOWN).subtract(priceModel.getBid()).setScale(2, RoundingMode.HALF_DOWN);
        priceModel.setBid(priceModel.getBid().subtract(commission));
    }

    protected void applyAskCommission(PriceModel priceModel,Double commissionRate) {
        var commission = priceModel.getAsk().divide(BigDecimal.ONE.subtract(BigDecimal.valueOf(commissionRate).scaleByPowerOfTen(-2)),
                RoundingMode.HALF_DOWN).subtract(priceModel.getAsk()).setScale(2, RoundingMode.HALF_DOWN);
        priceModel.setAsk(priceModel.getAsk().add(commission));
    }
}
