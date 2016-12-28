package mil.ustranscom.at21.mrv.rules;

import mil.ustranscom.at21.mrv.RulesRunner;
import mil.ustranscom.at21.mrv.model.Requirement;
import mil.ustranscom.at21.mrv.model.ValidationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.drools.core.util.DateUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RulesTest {
    @Rule
    public ErrorCollector errors = new ErrorCollector();

    @Parameters
    public static Collection<Object[]> data() throws Exception{
        return Arrays.asList(new Object[][] {
                { new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                        new String[]{ "Requirement Start Date is in past" } },
                { new Requirement(UUID.randomUUID(), DateUtils.parseDate("02-Oct-2015"), null),
                        new String[]{ "Requirement Start Date is in past", "Requirement is not named" } },
                { new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                        new String[]{ "Requirement Start Date is in past" } },
                { new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                        new String[]{ "Requirement Start Date is in past" } },
        });
    }

    @Parameter
    public Requirement requirement;

    @Parameter(value = 1)
    public String[] expected;

    @Test
    public void testRule() throws Exception{
        try(RulesRunner runner = new RulesRunner()){
            ValidationResult result = runner.validateRequirements(this.requirement);

            errors.checkThat(result.getErrors(), is(notNullValue()));
            errors.checkThat(result.getErrors().size(), is(this.expected.length));
            errors.checkThat(result.getErrors().get(this.requirement), hasItems(this.expected));
        }
    }
}
