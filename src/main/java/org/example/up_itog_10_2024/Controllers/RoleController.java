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
@RequestMapping("/role")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class RoleController {
    private GenericDAO<Role> modelDAO;
    private FieldHelper fieldHelper;
    private Class interactive_class = Role.class;

    private String modelName = "role";
    @Autowired
    public RoleRepository repository;
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
    public RoleController(FieldHelper fieldHelper) {

        this.fieldHelper = fieldHelper;
    }

    @GetMapping
    public String index(Model model) {
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("entities", repository.findAll());

        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("tasks1", taskRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("fields", FieldHelper.getFieldNames(interactive_class));
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("modelName", modelName);
        return "CRUD/index";
    }

    @GetMapping("/new")
    public String newPerson(Model model) {
        Iterable<Role> entitie = repository.findAll();

        model.addAttribute("entity", new Role("Example", (Set<Permission>) permissionRepository.getPermissionById(1)));
        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("tasks1", taskRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("fields", FieldHelper.getFieldNames(interactive_class));
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("modelName", modelName);
        return "CRUD/CRU";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("entity") Role entity) {
        repository.save(entity);
        return "redirect:/" + modelName;
    }


    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Role> entityOptional = repository.findById(id);

        Role entity = entityOptional.get();

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
        Optional<Role> entitie = repository.findById(id);
        Role entity = entitie.get();
        model.addAttribute("entity", entity);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);
        model.addAttribute("roles", roles);
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);



        List<Permission> allPermissions = (List<Permission>) permissionRepository.findAll();

        model.addAttribute("role", roles);
        model.addAttribute("allPermissions", allPermissions);
        return "CRUD/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @RequestParam("newPermissions") Set<Long> newPermissions, @ModelAttribute("entity") Role entity) {






        repository.findById(id).ifPresent(existingEntity -> {
            BeanUtils.copyProperties(entity, existingEntity, "id");
            Set<Permission> updatedPermissions = newPermissions.stream()
                    .map(permissionId -> permissionRepository.findById(permissionId).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
            existingEntity.setPermissions(updatedPermissions);
            roleRepository.save(existingEntity);
        });
        return "redirect:/" + modelName;
    }



}