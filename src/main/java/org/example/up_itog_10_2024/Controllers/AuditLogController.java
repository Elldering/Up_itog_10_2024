package org.example.up_itog_10_2024.Controllers;
import org.example.up_itog_10_2024.DAO.FieldHelper;
import org.example.up_itog_10_2024.DAO.GenericDAO;
import org.example.up_itog_10_2024.Models.*;
import org.example.up_itog_10_2024.Repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auditlog")
@PreAuthorize("hasAnyAuthority('USER') or hasAnyAuthority('PROJECT_MANAGER')or hasAnyAuthority('ADMIN')")
public class AuditLogController {
    private GenericDAO<AuditLog> modelDAO;
    private FieldHelper fieldHelper;
    private Class interactive_class = AuditLog.class;

    private String modelName = "auditlog";
    @Autowired
    public AuditLogRepository repository;
    @Autowired
    public RoleRepository roleRepository;
    @Autowired
    private AuditLogRepository auditLogRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private StatusRepository statusRepository;
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskParticipantRepository taskParticipantRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuditLogController(FieldHelper fieldHelper) {

        this.fieldHelper = fieldHelper;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<AuditLog> entitie = repository.findAll();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);

        List<User> users = (List<User>) userRepository.findAll();


        model.addAttribute("users", users);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("entities", entitie);
        model.addAttribute("fields", fields);
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("modelName", modelName);
        return "CRUD/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        Iterable<AuditLog> entitie = repository.findAll();


        model.addAttribute("entity", new AuditLog("", null));
        List<User> users = (List<User>) userRepository.findAll();


        model.addAttribute("users", users);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);
        model.addAttribute("roles", roles);
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);
        return "CRUD/CRU";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("entity") AuditLog entity) {
        repository.save(entity);
        return "redirect:/" + modelName;
    }


    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<AuditLog> entityOptional = repository.findById(id);

        AuditLog entity = entityOptional.get();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Object> fieldValues = fieldHelper.getFieldValues(entity);

        model.addAttribute("fieldValues", fieldValues);
        model.addAttribute("entity", entity);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);

        return "CRUD/show";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        repository.deleteById((long) id);
        return "redirect:/" + modelName;
    }

    //    @GetMapping("/{id}/edit")
//    public String edit(@PathVariable("id") long id, Model model) {
//        Optional<Role> entityOptional = Repository.findById(id);
//
//        Role entity = entityOptional.get();
//
//        List<String> fields = FieldHelper.getFieldNames(interactive_class);
//        List<Object> fieldValues = fieldHelper.getFieldValues(entity);
//
//
//        List<Role> roles = (List<Role>) roleRepository.findAll();
//
//        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
//        model.addAttribute("permissions", permissions);
//        model.addAttribute("fieldHelper", fieldHelper);
//        model.addAttribute("roles", roles);
//        model.addAttribute("fieldValues", fieldValues);
//        model.addAttribute("entity", entity);
//        model.addAttribute("fields", fields);
//        model.addAttribute("modelName", modelName);
//        return "CRUD/edit";
//    }
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        Optional<AuditLog> entitie = repository.findById(id);
        AuditLog entity = entitie.get();
        model.addAttribute("entity", entity);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);
        model.addAttribute("roles", roles);
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);



        List<Permission> allPermissions = (List<Permission>) permissionRepository.findAll();

        model.addAttribute("role", roleRepository.findAll());
        model.addAttribute("allPermissions", allPermissions);
        return "CRUD/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("entity") AuditLog entity) {
        repository.findById(id).ifPresent(existingPerson -> {
            BeanUtils.copyProperties(entity, existingPerson, "id");
            repository.save(existingPerson);
        });
        return "redirect:/" + modelName;
    }
}