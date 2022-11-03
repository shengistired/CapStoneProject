/*
 * 
 * 
 * 
 * Done By: Izdihar 
 * Request Mapping done By: Yong Sheng
 * Request Mapping for Login done By: Odelia
 * 
 * 
 */
package com.cognixia.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cognixia.model.Login;
import com.cognixia.model.UserInfo;
import com.cognixia.service.LoginService;
import com.cognixia.service.StorageService;
import com.cognixia.storage.StorageFileNotFoundException;

@RestController
public class FileUploadController {

	private final StorageService storageService;

	public static String usernameString;

	@Autowired
	public FileUploadController(StorageService storageService) {
		usernameString = null;
		this.storageService = storageService;
	}
	
	@Autowired
	LoginService loginService;
	
	@GetMapping("/login")
	public ModelAndView showLogin(Login login) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("login.html");
	    return modelAndView;
	}
	
	@PostMapping("/login")
	public ModelAndView checkLoginInfo(@Valid UserInfo userInfo, BindingResult bindingResult, Login login) {
		if (bindingResult.hasErrors() || loginService.getUser(userInfo) == null) {
			ModelAndView modelAndView = new ModelAndView();
		    modelAndView.setViewName("login.html");
		    return modelAndView;
		}
		
		loginService.addLogin(login);
		usernameString = userInfo.getUsername();
		ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("uploadForm.html");
	    return modelAndView;
	}

	@GetMapping("/")
	public ModelAndView listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files",
				storageService.loadAll()
						.map(path -> MvcUriComponentsBuilder
								.fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
								.build().toUri().toString())
						.collect(Collectors.toList()));

	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("uploadForm.html");
	    return modelAndView;
	}



	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}


	
	@PostMapping("/")
	public ModelAndView handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		//String fileType  = // get file type from file
		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		
	    ModelAndView modelAndView = new ModelAndView();
	    modelAndView.setViewName("redirect:/");
	    return modelAndView;
	}
	
	@Autowired
	JobLauncher jobLauncher;

	@Autowired
	Job job;
	
	
	@RequestMapping(path = "/purge/", method = RequestMethod.POST)
	public void purgeFile(@RequestParam("file") MultipartFile file) throws IOException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException{
		File newFile = new File(file.getOriginalFilename());
		

		File outputFile = new File(file.getOriginalFilename());
		try(FileOutputStream outputStream = new FileOutputStream(outputFile)){
			outputStream.write(file.getBytes());
		}
		
		JobParameters params = new JobParametersBuilder().addString("JobID", String.valueOf(System.currentTimeMillis()))
				.toJobParameters();
		jobLauncher.run(job, params);
		newFile.delete();

	}

	@ExceptionHandler(StorageFileNotFoundException.class)
	public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
		return ResponseEntity.notFound().build();
	}

}
