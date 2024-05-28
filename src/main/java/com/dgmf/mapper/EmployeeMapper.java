package com.dgmf.mapper;

import com.dgmf.dto.EmployeeDtoRequest;
import com.dgmf.dto.EmployeeDtoResponse;
import com.dgmf.entity.Employee;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {
    private final ModelMapper modelMapper;

    public Employee mapToEmployee(EmployeeDtoRequest employeeDtoRequest) {
        return modelMapper.map(employeeDtoRequest, Employee.class);
    }

    public EmployeeDtoResponse mapToEmployeeDtoResponse(Employee employee) {
        return modelMapper.map(employee, EmployeeDtoResponse.class);
    }
}
