package com.johnbenign.controller.diagnose;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.johnbenign.dto.diagnose.DiagnoseDTO;
import com.johnbenign.dto.diagnose.DiagnoseResponse;
import com.johnbenign.service.diagnose.DiagnoseService;

@RestController
@CrossOrigin(origins="http://localhost:3000x")
@RequestMapping("/pharm/diagnose")
public class DiagnoseController 
{
	private static final Logger logger = Logger.getLogger(DiagnoseController.class.getName());
	
	@Autowired
	private DiagnoseService service;
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> diagnose(@RequestBody DiagnoseDTO diagnoseDTO)
	{
		logger.info(" --- logging in the controller --- ");
		
		DiagnoseResponse response = service.diagnose(diagnoseDTO);
		
		if(response.getResult())
		{
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		
		return ResponseEntity.badRequest().body(response.getErrorMsg());
	}
}
