package model;

import java.sql.Timestamp;
import java.util.Date;

public class Issue {
    private int issueId;
    private String title;
    private Setting typeId;
    private Requirement reqId;
    private User assignerId;
    private User assigneeId;
    private Date deadline;
    private int statusId;
    private Timestamp statusDate;
    private String description;
    private Timestamp createdAt;
    private User createdById;
    private Timestamp updatedAt;
    private User updatedById;

    public int getIssueId() {
        return issueId;
    }

    public void setIssueId(int issueId) {
        this.issueId = issueId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Setting getTypeId() {
        return typeId;
    }

    public void setTypeId(Setting typeId) {
        this.typeId = typeId;
    }

    public Requirement getReqId() {
        return reqId;
    }

    public void setReqId(Requirement reqId) {
        this.reqId = reqId;
    }

    public User getAssignerId() {
        return assignerId;
    }

    public void setAssignerId(User assignerId) {
        this.assignerId = assignerId;
    }

    public User getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(User assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public Timestamp getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(Timestamp statusDate) {
        this.statusDate = statusDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public User getCreatedById() {
        return createdById;
    }

    public void setCreatedById(User createdById) {
        this.createdById = createdById;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(User updatedById) {
        this.updatedById = updatedById;
    }

    @Override
    public String toString() {
        return "Issue{" + "issueId=" + issueId + ", title=" + title + ", typeId=" + typeId + ", reqId=" + reqId + ", assignerId=" + assignerId + ", assigneeId=" + assigneeId + ", deadline=" + deadline + ", statusId=" + statusId + ", statusDate=" + statusDate + ", description=" + description + ", createdAt=" + createdAt + ", createdById=" + createdById + ", updatedAt=" + updatedAt + ", updatedById=" + updatedById + '}';
    }
}
