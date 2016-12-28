package mil.ustranscom.at21.mrv;

import mil.ustranscom.at21.mrv.model.Requirement;
import mil.ustranscom.at21.mrv.model.ValidationResult;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.command.Command;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.command.CommandFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class RulesRunner implements AutoCloseable {
	private final KieContainer kieContainer;
	private final KieBase kieBase;

	public RulesRunner() {
		KieServices kieServices = KieServices.Factory.get();
		this.kieContainer = kieServices.getKieClasspathContainer();
		
		KieBaseConfiguration config = kieServices.newKieBaseConfiguration();
		config.setOption(EventProcessingOption.CLOUD);
		
		this.kieBase = this.kieContainer.newKieBase(config);
	}

	@SuppressWarnings("unchecked")
	public Collection<ValidationResult> validateRequirements(Requirement... requirements) {
		List<Command<?>> commands = new ArrayList<>();

		commands.add(CommandFactory.newInsertElements(Arrays.asList(requirements)));
		commands.add(CommandFactory.newSetGlobal("results", new ArrayList<>(), true));

		BatchExecutionCommand batchExecutionCommand = CommandFactory.newBatchExecution(commands);

		ExecutionResults results = this.kieBase.newStatelessKieSession().execute(batchExecutionCommand);

        return (List<ValidationResult>)results.getValue("results");
	}

	@Override
	public void close() throws Exception {
		this.kieContainer.dispose();
	}
}
