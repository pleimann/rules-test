package mil.ustranscom.at21.mrv.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class ValidationResult {
    private Requirement requirement;
    private ValidationResultSeverity severity;
    private String message;

    public ValidationResult(Requirement requirement, ValidationResultSeverity severity, String message) {
        this.requirement = requirement;
        this.severity = severity;
        this.message = message;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ValidationResultSeverity getSeverity() {
        return severity;
    }

    public void setSeverity(ValidationResultSeverity severity) {
        this.severity = severity;
    }

    @Override
    public String toString() {
        return "ValidationResult{" +
                "requirement=" + requirement +
                ", message='" + message + '\'' +
                ", severity=" + severity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        ValidationResult that = (ValidationResult) o;

        return EqualsBuilder.reflectionEquals(this, that);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this, false);
    }
}
