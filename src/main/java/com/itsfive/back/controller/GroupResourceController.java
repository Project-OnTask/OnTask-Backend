package com.itsfive.back.controller;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.model.GroupResource;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.TaskResourceResponse;
import com.itsfive.back.payload.UploadFileResponse;
import com.itsfive.back.repository.GroupResourceRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.service.FileService;
import com.itsfive.back.service.GroupResourceService;

@RestController
@RequestMapping("/api")
public class GroupResourceController {
  
  @Autowired
  private FileService fileService;
  
  @Autowired
  GroupResourceService groupResourceService;
  
  @Autowired
  private GroupResourceRepository taskResRepository;
  
  @PostMapping("/task_resources/{userId}/{taskId}")
  public void addResourceToTask(@RequestParam("file") MultipartFile file,@PathVariable("userId") long userId,@PathVariable("taskId") long taskId) {
      String fileName = fileService.storeFile(file);

      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
               .path("/api/downloadFile/")
               .path(fileName)
               .toUriString();

       UploadFileResponse response =  new UploadFileResponse(fileName, fileDownloadUri,
              file.getContentType(), file.getSize());
       
       groupResourceService.addResource(userId,taskId, fileDownloadUri);
  }
  
  @GetMapping("/task_resources/{taskId}")
  public Stream<Object> getTaskResources(@PathVariable long taskId){
	  List<GroupResource> res = taskResRepository.findByTaskId(taskId);
	  return res.stream().map(t -> new TaskResourceResponse(t.getId(),t.getAddedBy().getId(),t.getAddedBy().getFName(),t.getCreatedAt(),t.getUrl()));
  }
  
}
