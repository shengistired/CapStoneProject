package com.cognixia.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cognixia.service.FileService;
import com.cognixia.model.File;
import com.cognixia.model.ResponseData;

@RestController
public class FileController {
	
	private final FileService fileService;

	@Autowired
	public FileController(FileService fileService) {
		super();
		this.fileService = fileService;
	}
	
	@GetMapping("/")
	public String listUploadedFiles(Model model) throws IOException {

		model.addAttribute("files", fileService.loadAll().map(
				path -> MvcUriComponentsBuilder.fromMethodName(FileController.class,
						"serveFile", path.getFileName().toString()).build().toUri().toString())
				.collect(Collectors.toList()));

		return "uploadForm";
	}
	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

		Resource file = FileService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
	
	@PostMapping("/upload")
	public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
		File files = null;
		files = fileService.saveFile(file);
		String downloadURL = "";
		downloadURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(files.getFileId()).toUriString();
		return new ResponseData(files.getFileName(),downloadURL,file.getContentType(),file.getSize());
	}
	
	@GetMapping("/download/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws Exception{
		File files = null;
		files = fileService.getFile(fileId);
		 return  ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType(files.getFileType()))
	                .header(HttpHeaders.CONTENT_DISPOSITION,
	                        "attachment; filename=\"" + files.getFileName()
	                + "\"")
	                .body(new ByteArrayResource(files.getData()));
	}
	

}
