package mil.ustranscom.at21.mrv;

import mil.ustranscom.at21.mrv.model.Requirement;
import mil.ustranscom.at21.mrv.model.ValidationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.drools.core.util.DateUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class RulesRunnerTest {
    @Rule
    public ErrorCollector errors = new ErrorCollector();

	@Test
	public void testCreateKieSession() throws Exception {
		RulesRunner runner = new RulesRunner();
		
		runner.close();
	}

	@Test
	public void testValidateSingleRequirement() throws Exception{
		try(RulesRunner runner = new RulesRunner()){
            Requirement requirement = new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25));

            ValidationResult result = runner.validateRequirements(requirement);
			
			errors.checkThat(result.getErrors(), is(notNullValue()));
            errors.checkThat(result.getErrors().size(), is(1));
            errors.checkThat(result.getErrors().get(requirement), hasItems("Requirement Start Date is in past"));
		}
	}

	@Test
	public void testValidateMultipleRequirements() throws Exception{
		try(RulesRunner runner = new RulesRunner()){
		    Requirement[] requirements = new Requirement[]{
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("02-Oct-2015"), null),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25))
            };

            ValidationResult result = runner.validateRequirements(requirements);

            errors.checkThat(result.getErrors(), is(notNullValue()));
            errors.checkThat(result.getErrors().size(), is(5));

			for(Requirement requirement : requirements){
                errors.checkThat(requirement, is(notNullValue()));

			    if(!requirement.getValid()) {
                    System.out.println(result.getErrors().get(requirement));
                }
            }
		}
	}
}
