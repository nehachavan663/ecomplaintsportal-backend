package com.ecomplaintsportal.studentProfile;

public class ComplaintSummaryDTO {

    private long pending;
    private long inProgress;
    private long resolved;

    public ComplaintSummaryDTO(long pending, long inProgress, long resolved) {
        this.pending = pending;
        this.inProgress = inProgress;
        this.resolved = resolved;
    }

    public long getPending() {
        return pending;
    }

    public long getInProgress() {
        return inProgress;
    }

    public long getResolved() {
        return resolved;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public void setInProgress(long inProgress) {
        this.inProgress = inProgress;
    }

    public void setResolved(long resolved) {
        this.resolved = resolved;
    }
}