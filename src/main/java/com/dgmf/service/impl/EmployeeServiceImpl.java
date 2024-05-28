package com.dgmf.service.impl;

import com.dgmf.dto.EmployeeDtoRequest;
import com.dgmf.dto.EmployeeDtoResponse;
import com.dgmf.entity.Employee;
import com.dgmf.mapper.EmployeeMapper;
import com.dgmf.repository.EmployeeRepository;
import com.dgmf.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<EmployeeDtoResponse> getAllEmployees() {
        List<Employee> employees = employeeRepository.findAll();

        return employees.stream()
                .map(employeeMapper::mapToEmployeeDtoResponse)
                .toList();
    }

    @Override
    public EmployeeDtoResponse saveEmployee(EmployeeDtoRequest employeeDtoRequest) {
        Employee savedEmployee = employeeRepository
                .save(employeeMapper.mapToEmployee(employeeDtoRequest));

        return employeeMapper.mapToEmployeeDtoResponse(savedEmployee);
    }

    @Override
    public EmployeeDtoResponse getEmployeeById(Long employeeDtoRequestId) {
        Employee employee = employeeRepository
                .findById(employeeDtoRequestId)
                .orElseThrow(() -> new RuntimeException(
                        "Employee Not Found with the Given Id : " +
                                employeeDtoRequestId
                    )
        );

        return employeeMapper.mapToEmployeeDtoResponse(employee);
    }

    @Override
    public EmployeeDtoResponse updateEmployeeById(
            Long employeeDtoRequestId, EmployeeDtoRequest employeeDtoRequest
    ) {
        Employee employee = employeeRepository
                .findById(employeeDtoRequestId)
                .orElseThrow(() -> new RuntimeException(
                                "Employee Not Found with the Given Id : " +
                                        employeeDtoRequestId
                        )
                );

        employee.setFirstName(employeeDtoRequest.getFirstName());
        employee.setLastName(employeeDtoRequest.getLastName());
        employee.setEmail(employeeDtoRequest.getEmail());
        employee.setPassword(employeeDtoRequest.getPassword());
        employee.setDateOfBirth(employeeDtoRequest.getDateOfBirth());

        Employee savedEmployee = employeeRepository.save(employee);

        return employeeMapper.mapToEmployeeDtoResponse(savedEmployee);
    }

    @Override
    public void deleteEmployeeById(Long employeeDtoRequestId) {
        Employee employee = employeeRepository
                .findById(employeeDtoRequestId)
                .orElseThrow(() -> new RuntimeException(
                                "Employee Not Found with the Given Id : " +
                                        employeeDtoRequestId
                        )
                );

        employeeRepository.delete(employee);
    }

    @Override
    public Page<EmployeeDtoResponse> findPaginated(
            int pageNo, int pageSize, String sortField, String sortDirection
    ) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        Page<Employee> employeeResponsePages = employeeRepository.findAll(
                pageable
        );

        return employeeResponsePages
                .map(employeeMapper::mapToEmployeeDtoResponse);
    }
}
