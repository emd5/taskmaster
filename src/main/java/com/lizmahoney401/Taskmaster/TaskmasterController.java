package com.lizmahoney401.Taskmaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Controller
public class TaskmasterController {

    @Autowired
    TaskmasterRepository taskmasterRepository;

    @GetMapping("/")
    public String getIndex(){
        return "index";
    }

    @GetMapping("/tasks")
    public ResponseEntity<Iterable<Taskmaster>> getTasks(Model m){
        Iterable<Taskmaster> findTask = taskmasterRepository.findAll();
        return ResponseEntity.ok(findTask);
    }

    @PostMapping("/tasks")
    public ResponseEntity<String> getTasks(@RequestParam String description,
                                           @RequestParam String title, @RequestParam String status, @RequestParam String assignee){
        String uuid = String.valueOf(UUID.randomUUID());
        Taskmaster newTask = new Taskmaster(uuid , title, description, status, assignee);
        taskmasterRepository.save(newTask);
        return ResponseEntity.ok("Done");
    }

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

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteUserByID(@PathVariable String id){
        taskmasterRepository.deleteById(id);
        return ResponseEntity.ok("User is deleted");
    }

//    @GetMapping("")
//    public ResponseEntity<List<Taskmaster>> getTasksByAvailability(@RequestParam String status){
//        Iterable<Taskmaster> allTask = taskmasterRepository.findAll();
//        List<Taskmaster> resultTaskList = new ArrayList<>();
//        System.out.println("STATUS: " + status);
//        for(Taskmaster t: allTask){
//            if(t.getStatus().equals(status)){
//                resultTaskList.add(t);
//            }
//        }
//        return ResponseEntity.ok(resultTaskList);
//    }







}
