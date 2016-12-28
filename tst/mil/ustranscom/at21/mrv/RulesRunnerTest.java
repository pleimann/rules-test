package mil.ustranscom.at21.mrv;

import mil.ustranscom.at21.mrv.model.Requirement;
import mil.ustranscom.at21.mrv.model.ValidationResultSeverity;
import mil.ustranscom.at21.mrv.model.ValidationResult;
import org.apache.commons.lang3.RandomStringUtils;
import org.drools.core.util.DateUtils;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

import java.util.Collection;
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

			Collection<ValidationResult> results = runner.validateRequirements(requirement);
			
            errors.checkThat(results, hasSize(1));

			errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("requirement", is(requirement))));
            errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("message", is("Requirement Start Date is in past"))));
            errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("severity", is(ValidationResultSeverity.WARNING))));
		}
	}

	@Test
	public void testValidateMultipleRequirements() throws Exception{
		try(RulesRunner runner = new RulesRunner()){
		    Requirement[] requirements = new Requirement[]{
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("02-Oct-2015"), null),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25)),
                    new Requirement(UUID.randomUUID(), DateUtils.parseDate("27-Dec-2016"), RandomStringUtils.randomAlphabetic(3, 25))
            };

            Collection<ValidationResult> results = runner.validateRequirements(requirements);

            errors.checkThat(results, hasSize(4));

			for(ValidationResult result : results){
                errors.checkThat(result.getRequirement(), is(notNullValue()));
                errors.checkThat(result.getMessage(), is(notNullValue()));
                errors.checkThat(result.getSeverity(), is(notNullValue()));

			    if(!result.getRequirement().getValid()) {
					errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("requirement", is(result.getRequirement()))));
					errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("message", is("Requirement Start Date is in past"))));
					errors.checkThat(results, hasItem(Matchers.<ValidationResult>hasProperty("severity", is(ValidationResultSeverity.WARNING))));

				}
            }
		}
	}
}
