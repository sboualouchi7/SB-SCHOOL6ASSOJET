package ma.salman.sbschoolassojet.services;

import lombok.RequiredArgsConstructor;
import ma.salman.sbschoolassojet.dto.admin.AdminRequest;
import ma.salman.sbschoolassojet.dto.admin.AdminResponse;
import ma.salman.sbschoolassojet.enums.Role;
import ma.salman.sbschoolassojet.exceptions.ResourceNotFoundException;
import ma.salman.sbschoolassojet.mappers.AdminMapper;
import ma.salman.sbschoolassojet.models.Admin;
import ma.salman.sbschoolassojet.repositories.AdminRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;
    private final AdminMapper adminMapper;
    private final PasswordEncoder passwordEncoder;

    public List<AdminResponse> getAllAdmins() {
        return adminRepository.findAll().stream()
                .map(adminMapper::toDto)
                .collect(Collectors.toList());
    }

    public AdminResponse getAdminById(Long id) {
        return adminRepository.findById(id)
                .map(adminMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Admin non trouvé avec l'ID: " + id));
    }

    @Transactional
    public AdminResponse createAdmin(AdminRequest request) {
        Admin admin = adminMapper.toEntity(request);
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setRole(Role.ADMIN);
        return adminMapper.toDto(adminRepository.save(admin));
    }

    @Transactional
    public AdminResponse updateAdmin(Long id, AdminRequest request) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Admin non trouvé avec l'ID: " + id));
        Role originalRole = admin.getRole();
        adminMapper.updateEntityFromDto(request, admin);
        admin.setRole(originalRole);
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        return adminMapper.toDto(adminRepository.save(admin));
    }

    @Transactional
    public void deleteAdmin(Long id) {
        if (!adminRepository.existsById(id)) {
            throw new ResourceNotFoundException("Admin non trouvé avec l'ID: " + id);
        }
        adminRepository.deleteById(id);
    }
}
