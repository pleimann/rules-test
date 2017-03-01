package com.asynchrony.rules;

import com.asynchrony.rules.model.GeoLocation;
import com.asynchrony.rules.model.Plan;
import com.asynchrony.rules.model.Requirement;
import com.asynchrony.rules.model.ValidationResult;
import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.SetGlobalCommand;
import org.drools.core.command.runtime.rule.InsertElementsCommand;
import org.drools.core.runtime.impl.ExecutionResultImpl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.kie.api.runtime.StatelessKieSession;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class RulesRunnerTest {
    @Rule
    public ErrorCollector errors = new ErrorCollector();

    @Mock
    private StatelessKieSession kieSession;

    @Test
    public void testPrepareCommands() throws Exception {
        RulesRunner runner = new RulesRunner(this.kieSession);

        Plan plan = new Plan();
        plan.addRequirements(mock(Requirement.class));
        plan.addGeoLocations(mock(GeoLocation.class));

        BatchExecutionCommandImpl batchExecutionCommand = (BatchExecutionCommandImpl)runner.prepareCommands(plan);

        List<? extends GenericCommand<?>> commands = batchExecutionCommand.getCommands();
        errors.checkThat(commands, hasSize(3));

        errors.checkThat(commands.get(0), is(instanceOf(InsertElementsCommand.class)));
        errors.checkThat(commands.get(0), hasProperty("objects", hasSize(1)));
        errors.checkThat(commands.get(0), hasProperty("objects", equalTo(plan.getGeoLocations())));

        errors.checkThat(commands.get(1), is(instanceOf(InsertElementsCommand.class)));
        errors.checkThat(commands.get(1), hasProperty("objects", hasSize(1)));
        errors.checkThat(commands.get(1), hasProperty("objects", equalTo(plan.getRequirements())));

        errors.checkThat(commands.get(2), is(instanceOf(SetGlobalCommand.class)));
        errors.checkThat(commands.get(2), hasProperty("object", is(instanceOf(ArrayList.class))));
        errors.checkThat(commands.get(2), hasProperty("identifier", equalTo("results")));
    }

    @Test
    public void testValidatePlan() throws Exception{
        RulesRunner runner = new RulesRunner(this.kieSession);

        List<ValidationResult> results = new ArrayList<>();

        HashMap<String, Object> output = new HashMap<>();
        output.put("results", results);

        ExecutionResultImpl executionResult = new ExecutionResultImpl();
        executionResult.setResults(output);

        List<ValidationResult> validationResults = runner.extractResults(executionResult);
        errors.checkThat(validationResults, hasItems(results.toArray(new ValidationResult[0])));
    }
}
