package com.lizmahoney401.Taskmaster.Controller;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.lizmahoney401.Taskmaster.Model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
public class TaskmasterController {

    @Autowired
    S3Client s3Client;

    @Autowired
    TaskmasterRepository taskmasterRepository;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @CrossOrigin
    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Taskmaster>> getTasks(){
        Iterable<Taskmaster> findTask = taskmasterRepository.findAll();
        return ResponseEntity.ok(findTask);
    }

    /**
     * This method
     * @param description
     * @param title
     * @param assignee
     * @return
     */
    @CrossOrigin
    @PostMapping("/tasks")
    public ResponseEntity<String> getTasks(@RequestParam String description, @RequestParam String title, @RequestParam String assignee){
        String uuid = String.valueOf(UUID.randomUUID());
        Taskmaster newTask;
        if(assignee == null || assignee.equals("")){
            newTask = new Taskmaster(uuid , title, description, "available", null);
        }
        else {
            newTask = new Taskmaster(uuid , title, description, "assigned", assignee);
        }
        taskmasterRepository.save(newTask);
        return ResponseEntity.ok("Done");
    }

    @CrossOrigin
    @PutMapping("/tasks/{id}/state")
    public ResponseEntity<Taskmaster> updateStatus(@PathVariable String id){
        Taskmaster oneTask =taskmasterRepository.findById(id).get();
        switch (oneTask.getStatus().toLowerCase()) {
            case "available":
                oneTask.setStatus("assigned");
                break;
            case "assigned":
                oneTask.setStatus("accepted");
                break;
            case "accepted":
                oneTask.setStatus("finished");
                break;
        }
        taskmasterRepository.save(oneTask);
        return ResponseEntity.ok(oneTask);
    }

    /**
     * This method returns a list of tasks by user
     * @param name String name of the assignee
     * @return the list of tasks
     */
    @CrossOrigin
    @GetMapping("/users/{name}/tasks")
    public ResponseEntity<List<Taskmaster>> getTasksByUser(@PathVariable String name){
        Iterable<Taskmaster> allTasks = taskmasterRepository.findAll();
        List<Taskmaster> resultTaskList = new ArrayList<>();
        for(Taskmaster t : allTasks){
            if(name.equals(t.assignee)){
                resultTaskList.add(t);
            }
        }
        return ResponseEntity.ok(resultTaskList);
    }

    /**
     * This method taks in a task id and the new assignee and checks the assignee by the task id
     * @param id UUID of the task
     * @param assignee a string name of the assignee
     * @return Response Entity - "User Assigned"
     */
    @CrossOrigin
    @PutMapping("/tasks/{id}/assign/{assignee}")
    public ResponseEntity<String> assignTaskToUser(@PathVariable String id, @PathVariable String assignee){
        Iterable<Taskmaster> allTasks = taskmasterRepository.findAll();
        for(Taskmaster t : allTasks){
            if(id.equals(t.id)){
                Taskmaster oneTask = new Taskmaster(t.id, t.title, t.description,"assigned", assignee);
                taskmasterRepository.save(oneTask);
            }
        }
        return ResponseEntity.ok("User Assigned");
    }


    /**
     * This method deletes tasks by id
     * @param id UUID of the task
     * @return Response Entity - "User is deleted"
     */
    @CrossOrigin
    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteUserByID(@PathVariable String id){
        taskmasterRepository.deleteById(id);
        return ResponseEntity.ok("User is deleted");
    }

    @CrossOrigin
    @GetMapping("/tasks/{id}")
    public ResponseEntity<Taskmaster> getTasksById(@PathVariable String id){
        Taskmaster oneTask =taskmasterRepository.findById(id).get();
        return ResponseEntity.ok(oneTask);
    }


    @CrossOrigin
    @PostMapping("/tasks/{id}/images")
    public ResponseEntity<Taskmaster> uploadFile(
            @PathVariable String id,
            @RequestPart(value = "file") MultipartFile file
    ){
        Taskmaster oneTask =taskmasterRepository.findById(id).get();
        String pic = this.s3Client.uploadFile(file);
        oneTask.setImageUrl(pic);

        String[] picSplit = pic.split("/");
        String fileName = picSplit[picSplit.length-1];
        oneTask.setThumbnailImageUrl("https://taskmasterappimageresized.s3-us-west-2.amazonaws.com/resized-" + fileName);
        taskmasterRepository.save(oneTask);

        return ResponseEntity.ok(oneTask);

    }
}
