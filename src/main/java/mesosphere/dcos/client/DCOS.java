package mesosphere.dcos.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import mesosphere.client.common.HeaderUtils;
import mesosphere.client.common.ThrowingSupplier;
import mesosphere.dcos.client.model.AuthenticateResponse;
import mesosphere.dcos.client.model.DCOSAuthCredentials;
import mesosphere.dcos.client.model.Secret;
import mesosphere.mesos.client.model.MesosAgentState;
import mesosphere.mesos.client.model.MesosMasterState;
import mesosphere.metronome.client.model.v1.GetJobResponse;
import mesosphere.marathon.client.Marathon;
import mesosphere.marathon.client.model.v2.App;
import mesosphere.marathon.client.model.v2.DeleteAppTasksResponse;
import mesosphere.marathon.client.model.v2.DeleteTaskCriteria;
import mesosphere.marathon.client.model.v2.GetAbdicateLeaderResponse;
import mesosphere.marathon.client.model.v2.GetAppNamespaceResponse;
import mesosphere.marathon.client.model.v2.GetAppVersionResponse;
import mesosphere.marathon.client.model.v2.GetEventSubscriptionRegisterResponse;
import mesosphere.marathon.client.model.v2.GetEventSubscriptionsResponse;
import mesosphere.marathon.client.model.v2.GetLeaderResponse;
import mesosphere.marathon.client.model.v2.GetMetricsResponse;
import mesosphere.marathon.client.model.v2.GetPluginsResponse;
import mesosphere.marathon.client.model.v2.GetServerInfoResponse;
import mesosphere.marathon.client.model.v2.GetTasksResponse;
import mesosphere.marathon.client.model.v2.Group;
import mesosphere.marathon.client.model.v2.Result;
import mesosphere.metronome.client.model.v1.Job;
import mesosphere.metronome.client.model.v1.JobRun;
import mesosphere.metronome.client.model.v1.JobSchedule;

@Headers({ "Content-Type: application/json", "Accept: application/json" })
public interface DCOS extends Marathon {
    // DCOS Auth
    @RequestLine("POST /acs/api/v1/auth/login")
    @Headers(HeaderUtils.AUTH_API_SOURCE_HEADER)
    AuthenticateResponse authenticate(DCOSAuthCredentials credentials) throws DCOSException;

    // DCOS Secrets
    @RequestLine("GET /secrets/v1/secret/{secretStore}/{secretPath}")
    @Headers(HeaderUtils.SECRETS_API_SOURCE_HEADER)
    Secret getSecret(@Param("secretStore") String secretStore,
                     @Param("secretPath") String secretPath)
            throws DCOSException;

    // Mesos
    @RequestLine("GET /mesos/state.json")
    @Headers(HeaderUtils.MESOS_API_SOURCE_HEADER)
    MesosMasterState getMasterState() throws DCOSException;

    @RequestLine("GET /agent/{agentId}/slave(1)/state.json")
    @Headers(HeaderUtils.MESOS_API_SOURCE_HEADER)
    MesosAgentState getAgentState(@Param("agentId") String agentId) throws DCOSException;

    @RequestLine("GET /agent/{agentId}/files/download?path={path}")
    @Headers({ "Accept: text/plain", HeaderUtils.MESOS_API_SOURCE_HEADER })
    feign.Response getAgentSandboxFile(@Param("agentId") String agentId,
                                       @Param("path") String path)
            throws DCOSException;


    // Apps
    /**
     * @param namespace - All apps under this group/subgroups will be returned. Example "/products/us-east"
     * @return
     * @throws DCOSException
     */
    @RequestLine("GET /v2/apps/{namespace}/*")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetAppNamespaceResponse getApps(@Param("namespace") String namespace) throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteApp(@Param("appId") String appId,
                     @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks?host={host}&force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    DeleteAppTasksResponse deleteAppTasksWithHost(@Param("appId") String appId,
                                                  @Param("host") String host,
                                                  @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks?host={host}&force={force}&scale=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteAppTasksAndScaleWithHost(@Param("appId") String appId,
                                          @Param("host") String host,
                                          @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks?host={host}&force={force}&wipe=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    DeleteAppTasksResponse deleteAppTasksAndWipeWithHost(@Param("appId") String appId,
                                                         @Param("host") String host,
                                                         @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks/{taskId}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    DeleteAppTasksResponse deleteAppTasksWithTaskId(@Param("appId") String appId,
                                                    @Param("taskId") String taskId,
                                                    @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks/{taskId}?force={force}&scale=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteAppTasksAndScaleWithTaskId(@Param("appId") String appId,
                                            @Param("taskId") String taskId,
                                            @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/apps/{appId}/tasks/{taskId}?force={force}&wipe=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    DeleteAppTasksResponse deleteAppTasksAndWipeWithTaskId(@Param("appId") String appId,
                                                           @Param("taskId") String taskId,
                                                           @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("GET /v2/apps/{appId}/versions")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetAppVersionResponse getAppVersion(@Param("id") String appId) throws DCOSException;

    @RequestLine("GET /v2/apps/{appId}/versions/{version}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    App getAppVersion(@Param("appId") String appId,
                      @Param("version") String version)
            throws DCOSException;

    // Deployments
    @RequestLine("DELETE /v2/deployments/{deploymentId}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteDeployment(@Param("deploymentId") String id) throws DCOSException;

    // Groups
    @RequestLine("GET /v2/groups")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Group getGroups() throws DCOSException;

    @RequestLine("PUT /v2/groups?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result modifyGroup(@Param("force") boolean force, Group group) throws DCOSException;

    @RequestLine("POST /v2/groups?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result createGroup(@Param("force") boolean force, Group group) throws DCOSException;

    @RequestLine("DELETE /v2/groups?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteGroup(@Param("force") boolean force) throws DCOSException;

    @RequestLine("GET /v2/groups/versions")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    List<String> getGroupVersion() throws DCOSException;

    @RequestLine("PUT /v2/groups/{id}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Group modifyGroups(@Param("id") String id,
                       @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("POST /v2/groups/{id}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Group createGroups(@Param("id") String id,
                       @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("DELETE /v2/groups/{id}?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result deleteGroups(@Param("id") String id,
                        @Param("force") boolean force)
            throws DCOSException;

    @RequestLine("GET /v2/groups/{id}/versions")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    Result getGroupVersion(@Param("id") String id) throws DCOSException;

    // Tasks
    @RequestLine("GET /v2/tasks?status={status}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetTasksResponse getTasks(@Param("status") String status) throws DCOSException;

    @RequestLine("DELETE /v2/tasks/delete?force={force}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetTasksResponse deleteTask(@Param("force") boolean force, DeleteTaskCriteria deleteTaskBody) throws DCOSException;

    @RequestLine("DELETE /v2/tasks/delete?force={force}&scale=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetTasksResponse deleteTaskAndScale(@Param("force") boolean force, DeleteTaskCriteria deleteTaskBody)
            throws DCOSException;

    @RequestLine("DELETE /v2/tasks/delete?force={force}&wipe=true")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetTasksResponse deleteTaskAndWipe(@Param("force") boolean force, DeleteTaskCriteria deleteTaskBody)
            throws DCOSException;

    // Event Subscriptions
    @RequestLine("GET /v2/eventSubscriptions")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetEventSubscriptionsResponse getSubscriptions() throws DCOSException;

    @RequestLine("POST /v2/eventSubscriptions?callbackUrl={url}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetEventSubscriptionRegisterResponse postSubscriptions(@Param("url") String url) throws DCOSException;

    @RequestLine("DELETE /v2/eventSubscriptions?callbackUrl={url}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetEventSubscriptionRegisterResponse deleteSubscriptions(@Param("url") String url) throws DCOSException;

    // Server Info
    @RequestLine("GET /v2/info")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetServerInfoResponse getInfo() throws DCOSException;

    // GetLeaderResponse
    @RequestLine("GET /v2/leader")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetLeaderResponse getLeader() throws DCOSException;

    @RequestLine("DELETE /v2/leader")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetAbdicateLeaderResponse deleteLeader() throws DCOSException;

    // Plugins
    @RequestLine("GET /v2/plugins")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetPluginsResponse getPlugin() throws DCOSException;

    @RequestLine("GET /v2/plugins/{pluginId}/{path}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    void getPlugin(@Param("pluginId") String pluginId, @Param("path") String path) throws DCOSException;

    @RequestLine("PUT /v2/plugins/{pluginId}/{path}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    void putPlugin(@Param("pluginId") String pluginId, @Param("path") String path) throws DCOSException;

    @RequestLine("POST /v2/plugins/{pluginId}/{path}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    void postPlugin(@Param("pluginId") String pluginId, @Param("path") String path) throws DCOSException;

    @RequestLine("DELETE /v2/plugins/{pluginId}/{path}")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    void deletePlugin(@Param("pluginId") String pluginId, @Param("path") String path) throws DCOSException;

    // Queue
    @RequestLine("DELETE /v2/queue/{appId}/delay")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    void deleteQueueDelay(@Param("appId") String appId) throws DCOSException;

    // Jobs
    @RequestLine("GET /v1/jobs")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    List<GetJobResponse> getJobs() throws DCOSException;

    @RequestLine("GET /v1/jobs?embed={embed}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    List<GetJobResponse> getJobs(@Param("embed") String embed) throws DCOSException;

    @RequestLine("POST /v1/jobs")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    Job createJob(Job job) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    GetJobResponse getJob(@Param("jobId") String jobId) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}?embed={embed}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    GetJobResponse getJob(@Param("jobId") String jobId, @Param("embed") String embed) throws DCOSException;

    @RequestLine("PUT /v1/jobs/{jobId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void updateJob(@Param("jobId") String jobId, Job job) throws DCOSException;

    @RequestLine("DELETE /v1/jobs/{jobId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void deleteJob(@Param("jobId") String jobId, Job job) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}/schedules")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    List<JobSchedule> getJobSchedules(@Param("jobId") String jobId) throws DCOSException;

    @RequestLine("POST /v1/jobs/{jobId}/schedules")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    JobSchedule createJobSchedule(@Param("jobId") String jobId, JobSchedule jobSchedule) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}/schedules/{scheduleId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    JobSchedule getJobSchedule(@Param("jobId") String jobId, @Param("scheduleId") String scheduleId) throws DCOSException;

    @RequestLine("PUT /v1/jobs/{jobId}/schedules/{scheduleId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void updateJobSchedule(@Param("jobId") String jobId, @Param("scheduleId") String scheduleId, JobSchedule jobSchedule) throws DCOSException;

    @RequestLine("DELETE /v1/jobs/{jobId/schedules/{scheduleId}}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void deleteJobSchedule(@Param("jobId") String jobId, @Param("scheduleId") String scheduleId) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}/runs")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    List<JobRun> getJobRuns(@Param("jobId") String jobId) throws DCOSException;

    @RequestLine("POST /v1/jobs/{jobId}/runs")
    @Body("{}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    JobRun triggerJobRun(@Param("jobId") String jobId) throws DCOSException;

    @RequestLine("GET /v1/jobs/{jobId}/runs/{runId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    JobRun getJobRun(@Param("jobId") String jobId, @Param("runId") String runId) throws DCOSException;

    @RequestLine("POST /v1/jobs/{jobId}/runs/{runId}/action/stop")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void stopJobRun(@Param("jobId") String jobId, @Param("runId") String runId) throws DCOSException;

    // Scheduled Jobs
    @RequestLine("POST /v0/scheduled-jobs")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    Job createJobWithSchedules(Job job) throws DCOSException;

    @RequestLine("PUT /v0/scheduled-jobs/{jobId}")
    @Headers(HeaderUtils.METRONOME_API_SOURCE_HEADER)
    void updateJobWithSchedules(@Param("jobId") String jobId, Job job) throws DCOSException;

    // Miscellaneous
    @RequestLine("GET /ping")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    String getPing() throws DCOSException;

    @RequestLine("GET /metrics")
    @Headers(HeaderUtils.MARATHON_API_SOURCE_HEADER)
    GetMetricsResponse getMetrics() throws DCOSException;

    default InputStream getAgentSandboxFileAsInputStream(final String agentId, final String path) throws DCOSException, IOException {
        return getAgentSandboxFile(agentId, path).body().asInputStream();
    }

    default String getAgentSandboxFileAsString(final String agentId, final String path) throws DCOSException {
        final feign.Response response = getAgentSandboxFile(agentId, path);

        try (final Reader reader = response.body().asReader()) {
            int charsExpected = response.body().length();
            char[] charArray = new char[charsExpected];
            int charsRead = reader.read(charArray);

            if (charsRead != charsExpected) {
                return null;
            }

            return new String(charArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    default GetJobResponse getJob(final String id, final List<String> embed) throws DCOSException {
        return getJob(id, String.join(",", embed));
    }

    default List<GetJobResponse> getJobs(final List<String> embed) throws DCOSException {
        return getJobs(String.join(",", embed));
    }

    // Convenience methods for identifiable resources.
    default Optional<App> maybeApp(final String id) throws DCOSException {
        return resource(() -> getApp(id).getApp());
    }

    default Optional<Job> maybeJob(final String id) throws DCOSException {
        return resource(() -> getJob(id));
    }

    default Optional<Group> maybeGroup(final String id) throws DCOSException {
        return resource(() -> getGroup(id));
    }

    default Optional<Secret> maybeSecret(final String secretStore, final String secretPath) throws DCOSException {
        return resource(() -> getSecret(secretStore, secretPath));
    }

    default Optional<GetAppNamespaceResponse> maybeApps(final String namespace) throws DCOSException {
        return resource(() -> getApps(namespace));
    }

    /**
     * Calls the supplied {@code resourceSupplier} to retrieve a DCOS resource of type T.
     * <p/>
     * If a {@link DCOSException} is thrown by the {@code resourceSupplier}, it will be caught. If
     * {@link DCOSException#getStatus()} is 404, then an empty optional will be returned. Any other
     * {@link DCOSException} will be rethrown.
     * @param resourceSupplier {@link ThrowingSupplier} instance for accessing the resource.
     * @param <T> The resource type
     * @return The optional resource.
     * @throws DCOSException if a non-404 DCOSException is thrown.
     */
    default <T> Optional<T> resource(
            ThrowingSupplier<T, DCOSException> resourceSupplier)
            throws DCOSException {
        try {
            return Optional.of(resourceSupplier.get());
        } catch (DCOSException e) {
            if (e.getStatus() == 404) {
                return Optional.empty();
            }

            throw e;
        }
    }
}
