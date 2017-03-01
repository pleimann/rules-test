package com.asynchrony.rules;

import com.asynchrony.rules.model.DataFactory;
import com.asynchrony.rules.model.Plan;
import com.asynchrony.rules.model.ValidationResult;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;

import java.util.Collection;

public class Main implements Runnable {
    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    private final KieContainer kieContainer;
    private final KieBase kieBase;

    public Main() {
        KieServices kieServices = KieServices.Factory.get();
        this.kieContainer = kieServices.getKieClasspathContainer();

        KieBaseConfiguration config = kieServices.newKieBaseConfiguration();
        config.setOption(EventProcessingOption.CLOUD);

        this.kieBase = this.kieContainer.newKieBase(config);
    }

    @Override
    public void run() {
        try {
            RulesRunner runner = new RulesRunner(this.kieBase.newStatelessKieSession());


            for(int i = 0; i < 10; i++) {
                Plan plan = DataFactory.randomPlan();

                Collection<ValidationResult> results = runner.validatePlan(plan);

                System.out.println("\nPlan ID: " + plan.getId());
                System.out.println("==================================");
                for(ValidationResult result: results){
                    System.out.println(result);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Exception closing RulesSession", e);

        } finally {
            this.kieContainer.dispose();
        }
    }
}
