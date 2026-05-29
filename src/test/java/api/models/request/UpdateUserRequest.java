package api.models.request;

public class UpdateUserRequest {

    private String job;

    public UpdateUserRequest(
            String job
    ) {

        this.job = job;
    }

    public String getJob() {
        return job;
    }
}