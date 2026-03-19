public class Job {

    private String jobID;
    private String clientID;
    private int duration;
    private LocalDateTime deadline;
    private String status;

    public Job(String jobID, String clientID, int duration, LocalDateTime deadline) {
        this.jobID = jobID;
        this.clientID = clientID;
        this.duration = duration;
        this.deadline = deadline;
       
    }

    public String getJobID() { return jobID; }
    public String getClientID() { return clientID; }
    public int getDuration() { return duration; }
    public LocalDateTime getDeadline() { return deadline; }
    public String getStatus() { return status; }

    
    

    public String fileText() {
        return "Job ID: " + jobID +
               " | Client ID: " + clientID +
               " | Duration: " + duration + " min" +
               " | Deadline: " + deadline +
               " | Status: " + status;
    }
}
