package ma.salman.sbschoolassojet.controllers;
import jakarta.validation.Valid;
import ma.salman.sbschoolassojet.dto.admin.AdminRequest;
import ma.salman.sbschoolassojet.dto.admin.AdminResponse;
import ma.salman.sbschoolassojet.dto.common.ApiResponse;
import ma.salman.sbschoolassojet.services.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admins")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<AdminResponse>>> getAllAdmins() {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Admins récupérés avec succès",
                adminService.getAllAdmins(),
                null
        ));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminResponse>> getAdminById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Admin récupéré avec succès",
                adminService.getAdminById(id),
                null
        ));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminResponse>> createAdmin(@Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Admin créé avec succès",
                adminService.createAdmin(request),
                null
        ));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<AdminResponse>> updateAdmin(
            @PathVariable Long id,
            @Valid @RequestBody AdminRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Admin mis à jour avec succès",
                adminService.updateAdmin(id, request),
                null
        ));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long id) {
        adminService.deleteAdmin(id);
        return ResponseEntity.ok(new ApiResponse<>(
                true,
                "Admin supprimé avec succès",
                null,
                null
        ));
    }
}
