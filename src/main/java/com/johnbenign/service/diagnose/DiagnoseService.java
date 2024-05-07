package com.johnbenign.service.diagnose;

import com.johnbenign.dto.diagnose.DiagnoseDTO;
import com.johnbenign.dto.diagnose.DiagnoseResponse;

public interface DiagnoseService
{
	public DiagnoseResponse diagnose(DiagnoseDTO diagnoseDTO);
}
