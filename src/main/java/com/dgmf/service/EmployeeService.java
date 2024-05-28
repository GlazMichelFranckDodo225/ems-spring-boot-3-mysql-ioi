package com.dgmf.service;

import com.dgmf.dto.EmployeeDtoRequest;
import com.dgmf.dto.EmployeeDtoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDtoResponse> getAllEmployees();
    void saveEmployee(EmployeeDtoRequest employeeDtoRequest);
    EmployeeDtoResponse getEmployeeById(Long employeeDtoRequestId);
    void deleteEmployeeById(Long employeeDtoRequestId);
    Page<EmployeeDtoResponse> findPaginated(
            int pageNo, int pageSize, String sortField, String sortDirection
    );
}
