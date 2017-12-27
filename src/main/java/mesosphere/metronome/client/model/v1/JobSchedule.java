package mesosphere.metronome.client.model.v1;

import mesosphere.client.common.ModelUtils;

public class JobSchedule {
    private String id;
    private String cron;
    private String concurrencyPolicy;
    private Boolean enabled;
    private Integer startingDeadlineSeconds;
    private String timezone;
    private String nextRunAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public String getConcurrencyPolicy() {
        return concurrencyPolicy;
    }

    public void setConcurrencyPolicy(String concurrencyPolicy) {
        this.concurrencyPolicy = concurrencyPolicy;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getStartingDeadlineSeconds() {
        return startingDeadlineSeconds;
    }

    public void setStartingDeadlineSeconds(Integer startingDeadlineSeconds) {
        this.startingDeadlineSeconds = startingDeadlineSeconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getNextRunAt() {
        return nextRunAt;
    }

    public void setNextRunAt(String nextRunAt) {
        this.nextRunAt = nextRunAt;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}
