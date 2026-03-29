package com.isaac.br.selfcheckoutmercado.service.imp;

import com.isaac.br.selfcheckoutmercado.dto.LoginDTO;
import com.isaac.br.selfcheckoutmercado.dto.LoginResponseDTO;
import com.isaac.br.selfcheckoutmercado.enums.LogAudi;
import com.isaac.br.selfcheckoutmercado.exceptions.NegadoException;
import com.isaac.br.selfcheckoutmercado.exceptions.NotFoundException;
import com.isaac.br.selfcheckoutmercado.model.AuditLog;
import com.isaac.br.selfcheckoutmercado.model.Employee;
import com.isaac.br.selfcheckoutmercado.model.Terminal;
import com.isaac.br.selfcheckoutmercado.repository.EmployeeRepository;
import com.isaac.br.selfcheckoutmercado.repository.TerminalRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private TerminalRepository terminalRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AudiService audiService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public LoginResponseDTO authenticate(LoginDTO loginDTO) {
        Employee employee = employeeRepository
                .findByBadgeId(loginDTO.badgeId())
                .orElseThrow(() -> new NotFoundException("Employee not found"));

        if (!employee.isActive()) {
            throw new NegadoException("Access denied");
        }

        if (employee.getLockedUntil() != null &&
            employee.getLockedUntil().isAfter(LocalDateTime.now())) {
            throw new NegadoException("Account blocked");
        }

        System.out.println("=========="+passwordEncoder.matches("1234", employee.getPinHash()));

        if (!passwordEncoder.matches(loginDTO.pin(), employee.getPinHash())){
            employee.setFailedAttempts(employee.getFailedAttempts() + 1);

            if (employee.getFailedAttempts() >= 5) {
                employee.setLockedUntil(LocalDateTime.now().plusMinutes(10));
                employee.setFailedAttempts(0);
            }
            employeeRepository.save(employee);
            throw new NegadoException("PIN invalid");
        }

        employee.setFailedAttempts(0);
        employeeRepository.save(employee);

        Terminal terminal = terminalRepository.findByTerminalCode(loginDTO.terminal_code())
                .orElseThrow(() -> new NotFoundException("Terminal not found"));

        String token = jwtService.generateToken(employee,terminal.getId());
        AuditLog log = new AuditLog
                (employee.getId(),
                 terminal.getId(),
                 LogAudi.LOGIN,
                 LocalDateTime.now(),
                 null);
        audiService.log(log);
        return new LoginResponseDTO(token);

    }


}
