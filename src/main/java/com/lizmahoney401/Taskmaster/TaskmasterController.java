package com.lizmahoney401.Taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class TaskmasterController {

    @Autowired
    TaskmasterRepository taskmasterRepository;

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Taskmaster>> getTasks(Model m){
        Iterable<Taskmaster> findTask = taskmasterRepository.findAll();
        return ResponseEntity.ok(findTask);
    }

    /**
     * A user should be able to make a POST request to /tasks with body parameters for title and description to add a new task.
     * All tasks should start with a status of Available.
     * The response to that request should contain the complete saved data for that task.
     * A user should be able to make a PUT request to /tasks/{id}/state to advance the status of that task.
     * Tasks should advance from Available -> Assigned -> Accepted -> Finished.
     */

    @PostMapping("/tasks")
    public ResponseEntity<String> getTasks(@RequestParam String id, @RequestParam String description,
                                          @RequestParam String title, @RequestParam String status){
        Taskmaster newTask = new Taskmaster(id, title, description, status);
        taskmasterRepository.save(newTask);
        return ResponseEntity.ok("Done");
    }

    @PostMapping("/tasks/{id}/state")
    public ResponseEntity<Taskmaster> updateStatus(@PathVariable String id){
        Taskmaster oneTask =taskmasterRepository.findById(id).get();
        if(oneTask.getStatus().toLowerCase().equals("available")){
            oneTask.setStatus("assigned");
        }else if(oneTask.getStatus().toLowerCase().equals("assigned")){
            oneTask.setStatus("accepted");
        }else if(oneTask.getStatus().toLowerCase().equals("accepted")){
            oneTask.setStatus("finished");
        }
        taskmasterRepository.save(oneTask);
        return ResponseEntity.ok(oneTask);
    }



}
