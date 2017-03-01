package com.asynchrony.rules.rules;

import com.asynchrony.rules.RulesRunner;
import com.asynchrony.rules.model.*;
import org.drools.core.util.DateUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.StatelessKieSession;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.runners.Parameterized.Parameter;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class RulesTest {
    @Rule
    public ErrorCollector errors = new ErrorCollector();

    private StatelessKieSession kieSession;

    @Before
    public void setUp() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();

        KieBaseConfiguration config = kieServices.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.CLOUD);

        KieBase kieBase = kieContainer.newKieBase(config);

        this.kieSession = kieBase.newStatelessKieSession();
    }

    @Parameters
    public static Collection<Object[]> data() throws Exception {
        return Arrays.asList(new Object[][]{
                {new Plan(DataFactory.randomPlanId(),
                        Arrays.asList(new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), DateUtils.parseDate("03-Jan-2017"), DataFactory.randomTransportMode(), DataFactory.randomGeoCode())),
                        Arrays.asList(DataFactory.randomGeoLocation("US1DF"))
                ),
                        new String[]{"Requirement earliest date is in past"},
                        new ValidationResultSeverity[]{ValidationResultSeverity.WARNING}},

                {new Plan(DataFactory.randomPlanId(),
                        Arrays.asList(new Requirement(UUID.randomUUID(), DateUtils.parseDate("02-Jan-2016"), DateUtils.parseDate("02-Oct-2015"), DataFactory.randomTransportMode(), DataFactory.randomGeoCode())),
                        Arrays.asList(DataFactory.randomGeoLocation("AFDC4"))
                ),
                        new String[]{"Requirement earliest date is in past", "Invalid GeoLoc code"},
                        new ValidationResultSeverity[]{ValidationResultSeverity.WARNING, ValidationResultSeverity.FATAL}},

                {new Plan(DataFactory.randomPlanId(),
                        Arrays.asList(new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), DateUtils.parseDate("20-Dec-2016"), DataFactory.randomTransportMode(), DataFactory.randomGeoCode())),
                        Arrays.asList(DataFactory.randomGeoLocation("1234"))
                ),
                        new String[]{"Requirement earliest date is in past"},
                        new ValidationResultSeverity[]{ValidationResultSeverity.WARNING}},

                {new Plan(DataFactory.randomPlanId(),
                        Arrays.asList(new Requirement(UUID.randomUUID(), DateUtils.parseDate("20-Dec-2016"), null, DataFactory.randomTransportMode(), DataFactory.randomGeoCode())),
                        Arrays.asList(DataFactory.randomGeoLocation("US1DF"))
                ),
                        new String[]{"Requirement Start Date is in past"},
                        new ValidationResultSeverity[]{ValidationResultSeverity.WARNING}},
        });
    }

    @Parameter
    public Plan plan;

    @Parameter(value = 1)
    public String[] expectedMessages;

    @Parameter(value = 2)
    public ValidationResultSeverity[] expectedTypes;

    @Test
    public void testRule() throws Exception {
        RulesRunner runner = new RulesRunner(this.kieSession);

        Collection<ValidationResult> results = runner.validatePlan(this.plan);

        System.out.println("\nPlan ID: " + plan.getId());
        System.out.println("==================================");
        for(ValidationResult result: results){
            System.out.println(result);
        }

        for (Requirement requirement : plan.getRequirements()) {
            errors.checkThat(results, Matchers.<ValidationResult>hasItem(hasProperty("requirement", equalTo(requirement))));

            for (ValidationResult result : results){
                if (result.getRequirement().equals(requirement)) {
                    continue;
                }
            }
        }
    }
}
