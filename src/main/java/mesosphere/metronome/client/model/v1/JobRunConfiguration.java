package mesosphere.metronome.client.model.v1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mesosphere.client.common.ModelUtils;
import mesosphere.marathon.client.model.v2.Volume;

public class JobRunConfiguration {
    private List<Artifact> artifacts;
    private String cmd;
    private Double cpus;
    private Double mem;
    private Double disk;
    private Docker docker;
    private Map<String, Object> env;
    private Integer maxLaunchDelay;
    private Placement placement;
    private RestartPolicy restart;
    private String user;
    private List<Volume> volumes;

    public List<Artifact> getArtifacts() {
        return artifacts;
    }

    public void addArtifact(Artifact artifact) {
        if (this.artifacts == null) {
            this.artifacts = new ArrayList<>();
        }
        this.artifacts.add(artifact);
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public Double getCpus() {
        return cpus;
    }

    public void setCpus(Double cpus) {
        this.cpus = cpus;
    }

    public Double getMem() {
        return mem;
    }

    public void setMem(Double mem) {
        this.mem = mem;
    }

    public Double getDisk() {
        return disk;
    }

    public void setDisk(Double disk) {
        this.disk = disk;
    }

    public Docker getDocker() {
        return docker;
    }

    public void setDocker(Docker docker) {
        this.docker = docker;
    }

    public Map<String, Object> getEnv() {
        return env;
    }

    public void addEnv(String key, Object value) {
        if (this.env == null) {
            this.env = new HashMap<>();
        }
        this.env.put(key, value);
    }

    public void setEnv(Map<String, Object> env) {
        this.env = env;
    }

    public Integer getMaxLaunchDelay() {
        return maxLaunchDelay;
    }

    public void setMaxLaunchDelay(Integer maxLaunchDelay) {
        this.maxLaunchDelay = maxLaunchDelay;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public RestartPolicy getRestart() {
        return restart;
    }

    public void setRestart(RestartPolicy restart) {
        this.restart = restart;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public List<Volume> getVolumes() {
        return volumes;
    }

    public void addVolume(Volume volume) {
        if (this.volumes == null) {
            this.volumes = new ArrayList<>();
        }
        this.volumes.add(volume);
    }

    public void setVolumes(List<Volume> volumes) {
        this.volumes = volumes;
    }

    @Override
    public String toString() {
        return ModelUtils.toString(this);
    }
}
