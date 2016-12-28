package mil.ustranscom.at21.mrv.model;

import org.apache.commons.collections4.ListValuedMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;

public class ValidationResult {
    private ListValuedMap<Requirement, String> errors = MultiMapUtils.newListValuedHashMap();
    private ListValuedMap<Requirement, String> warnings = MultiMapUtils.newListValuedHashMap();

    public void error(Requirement requirement, String message){
        this.errors.put(requirement, message);
    }

    public void warning(Requirement requirement, String message){
        this.warnings.put(requirement, message);
    }

    public MultiValuedMap<Requirement, String> getErrors() {
        return errors;
    }

    public MultiValuedMap<Requirement, String> getWarnings() {
        return warnings;
    }
}
