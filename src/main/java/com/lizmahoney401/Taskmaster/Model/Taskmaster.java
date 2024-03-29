package com.lizmahoney401.Taskmaster.Model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

@DynamoDBTable(tableName = "Taskmaster")
public class Taskmaster {

    public String id;
    public String title;
    public String description;
    public String status;
    public String assignee;
    public String imageUrl;
    public String thumbnailImageUrl;

    public Taskmaster(){}

    public Taskmaster(String id, String title, String description, String status, String assignee) {
        this.id = id;
        this.setTitle(title);
        this.description = description;
        this.status = status;
        this.assignee = assignee;
    }

    @DynamoDBHashKey
    public String getId() { return id; }

    @DynamoDBAttribute
    public String getTitle() { return title; }

    @DynamoDBAttribute
    public String getStatus() { return status; }

    @DynamoDBAttribute
    public String getDescription() { return description; }

    @DynamoDBAttribute
    public String getAssignee() { return assignee; }

    @DynamoDBAttribute
    public String getImageUrl() { return this.imageUrl; }

    @DynamoDBAttribute
    public String getThumbnailImageUrl() { return thumbnailImageUrl; }

    // Setters
    public void setThumbnailImageUrl(String thumbnailImageUrl) { this.thumbnailImageUrl = thumbnailImageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }
    public void setId(String id){
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
