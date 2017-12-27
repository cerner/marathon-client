package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mesosphere.client.common.ModelUtils;

public class Job {
	private String id;
	private String description;
	private Map<String, String> labels;
	private JobRunConfiguration run;
	private List<JobSchedule> schedules;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Map<String, String> getLabels() {
		return labels;
	}

	public void addLabel(String key, String value) {
		if (this.labels == null) {
			this.labels = new HashMap<>();
		}
		this.labels.put(key, value);
	}

	public void setLabels(Map<String, String> labels) {
		this.labels = labels;
	}

	public JobRunConfiguration getRun() {
		return run;
	}

	public void setRun(JobRunConfiguration run) {
		this.run = run;
	}

	public List<JobSchedule> getSchedules() {
		return schedules;
	}

	public void addSchedule(JobSchedule schedule) {
		if (this.schedules == null) {
			this.schedules = new ArrayList<>();
		}
		this.schedules.add(schedule);
	}

	public void setSchedules(List<JobSchedule> schedules) {
		this.schedules = schedules;
	}

	@Override
	public String toString() {
		return ModelUtils.toString(this);
	}
}
