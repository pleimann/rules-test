package com.asynchrony.rules;

import com.asynchrony.rules.model.Plan;
import com.asynchrony.rules.model.ValidationResult;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.StatelessKieSession;
import org.kie.internal.command.CommandFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RulesRunner {
	private final StatelessKieSession kieSession;

	public RulesRunner(StatelessKieSession kieSession) {
		this.kieSession = kieSession;
	}

	public Collection<ValidationResult> validatePlan(Plan plan){
        return this.extractResults(this.kieSession.execute(this.prepareCommands(plan)));
	}

	BatchExecutionCommand prepareCommands(Plan plan){
        List<Command<?>> commands = new ArrayList<>();

        commands.add(CommandFactory.newInsertElements(plan.getGeoLocations()));
        commands.add(CommandFactory.newInsertElements(plan.getRequirements()));
        commands.add(CommandFactory.newSetGlobal("results", new ArrayList<>(), true));

        return CommandFactory.newBatchExecution(commands);
    }

	List<ValidationResult> extractResults(ExecutionResults executionResults){
        return (List<ValidationResult>)executionResults.getValue("results");
    }
}
