/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author mituz
 */

public class Requirement {
    private int reqId;
    private String title;
    private User owner;
    private Setting complexity;
    private Setting status;
    private String description;
    private Timestamp createdAt;
    private User createdById;
    private Timestamp updatedAt;
    private User updatedById;

    public int getReqId() {
        return reqId;
    }

    public void setReqId(int reqId) {
        this.reqId = reqId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Setting getComplexity() {
        return complexity;
    }

    public void setComplexity(Setting complexity) {
        this.complexity = complexity;
    }

    public Setting getStatus() {
        return status;
    }

    public void setStatus(Setting status) {
        this.status = status;
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
        return "Requirement{" + "reqId=" + reqId + ", title=" + title + ", owner=" + owner + ", complexity=" + complexity + ", status=" + status + ", description=" + description + ", createdAt=" + createdAt + ", createdById=" + createdById + ", updatedAt=" + updatedAt + ", updatedById=" + updatedById + '}';
    }

    
}
