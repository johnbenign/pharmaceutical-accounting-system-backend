package com.johnbenign.repository.diagnose;

import org.springframework.data.jpa.repository.JpaRepository;

import com.johnbenign.dto.diagnose.DiagnoseDTO;

public interface DiagnoseRepository extends JpaRepository<DiagnoseDTO, String>
{

}
