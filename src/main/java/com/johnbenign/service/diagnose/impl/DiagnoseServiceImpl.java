package com.johnbenign.service.diagnose.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johnbenign.dto.diagnose.DiagnoseDTO;
import com.johnbenign.dto.diagnose.DiagnoseResponse;
import com.johnbenign.repository.diagnose.DiagnoseRepository;
import com.johnbenign.service.diagnose.DiagnoseService;
import net.bytebuddy.utility.RandomString;

@Service
public class DiagnoseServiceImpl implements DiagnoseService
{
	private static final Logger logger = Logger.getLogger(DiagnoseServiceImpl.class.getName());
	
	@Autowired
	private DiagnoseResponse response;
	
	@Autowired
	private DiagnoseRepository repository;
	
	//List<String> prescriptions = null;

	@Override
	public DiagnoseResponse diagnose(DiagnoseDTO diagnoseDTO)
	{
		logger.info(" --- about to diagnose --- ");
		
		diagnoseDTO.setDiagnoseId(RandomString.make());
//		diagnoseMalariaAndTyphoid(diagnoseDTO);
		
		diagnoseCholeraAndMalaria(diagnoseDTO);
		
		if(response.getResult())
		{
			saveRecord(diagnoseDTO);
			return response;
		}
		
		diagnoseTuberculosis(diagnoseDTO);
		
		if(response.getResult())
		{
			saveRecord(diagnoseDTO);
			return response;
		}
		
		return response;
	}

	private void diagnoseTuberculosis(DiagnoseDTO diagnoseDTO)
	{
		logger.info(" --- diagnosing for tuberculosis --- ");
		
		//prescriptions = new ArrayList<>();
		
		if(diagnoseDTO.getCough().equals("Y") && diagnoseDTO.getCoughBloodOrMucus().equals("Y") && diagnoseDTO.getChestPainWhenCoughingOrBreathing().equals("y"))
		{
			logger.info(" --- you probably have tuberculosis --- ");
			
				response.setResult(true);
				String prescriptions = "Pyrazinamide or Isoniazid";
				response.setResponse("Tuberculosis");
				
				response.setPrescriptions(prescriptions);
				
				return;
	
		}
		else
		{
			logger.info(" unknown diagnosis");
			
			response.setErrorMsg("unable to diagnose ailment");
			
			response.setResult(false);
		}
	}

	private void diagnoseCholeraAndMalaria(DiagnoseDTO diagnoseDTO)
	{
		logger.info(" ---  diagnosing for malaria and cholera --- ");
		
//		prescriptions = new ArrayList<>();
		
		if(diagnoseDTO.getHeadache().equals("Y") && diagnoseDTO.getFever().equals("Y"))
		{
			if(diagnoseDTO.getThirst().equals("Y") && diagnoseDTO.getRapidHeartRate().equals("Y"))
			{
				logger.info(" --- you probably have cholera --- ");
				
				response.setResult(true);
				String prescriptions = "Doxycycline or Azithromycin";
				
				response.setResponse("Cholera");
				
				response.setPrescriptions(prescriptions);
				
				return;
			}
			
			logger.info("You probably have Malaria and Typhoid");
			
			response.setResult(true);
			String prescriptions = "Panadol and Lumartem";
			
			response.setResponse("Malaria");
			
			response.setPrescriptions(prescriptions);
			
			return;
	
		}
		else
		{
			logger.info(" unknown diagnosis");
			
			response.setErrorMsg("unable to diagnose ailment");
			
			response.setResult(false);
		}
	}

//	private void diagnoseMalariaAndTyphoid(DiagnoseDTO diagnoseDTO)
//	{
//		prescriptions = new ArrayList<>();
//		
//		if(diagnoseDTO.getHeadache().equals("Y") && diagnoseDTO.getFever().equals("Y"))
//		{
//			response.setResult(true);
//			prescriptions.add("Panadol");
//			prescriptions.add("Lumartem or Artesunate");
//			
//			response.setResponse("Malaria");
//		}
//		else
//		{
//			logger.info(" unknown diagnosis");
//			
//			response.setErrorMsg("unable to diagnose ailment");
//			
//			response.setResult(false);
//		}
//	}

	private void saveRecord(DiagnoseDTO diagnoseDTO)
	{
		repository.saveAndFlush(diagnoseDTO);
	}
	
	public List<DiagnoseDTO> getAllDiagnosis()
	{
		List<DiagnoseDTO> diagnosis = repository.findAll();
		
		return diagnosis;
	}
	
}
