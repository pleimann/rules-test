package util;

import mil.ustranscom.at21.mrv.model.Requirement;
import org.apache.commons.lang3.RandomStringUtils;
import org.fluttercode.datafactory.impl.DataFactory;

import java.util.Date;
import java.util.UUID;

public class TestDataFactory {
    private static final DataFactory df = new DataFactory();

    private TestDataFactory(){}

    public static final Requirement createRandomRequirement(int minDaysFromDate, int maxDaysFromDate){
        return new Requirement(UUID.randomUUID(), df.getDate(new Date(), minDaysFromDate, maxDaysFromDate), RandomStringUtils.randomAlphabetic(3, 25));
    }
}
