package org.example.up_itog_10_2024.Controllers;
import org.example.up_itog_10_2024.DAO.FieldHelper;
import org.example.up_itog_10_2024.DAO.GenericDAO;
import org.example.up_itog_10_2024.Models.Permission;
import org.example.up_itog_10_2024.Models.Role;
import org.example.up_itog_10_2024.Repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/permission")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class PermissionController {
    private GenericDAO<Permission> modelDAO;
    private FieldHelper fieldHelper;
    private final Class<Permission> interactive_class = Permission.class;

    private String modelName = "permission";
    @Autowired
    public PermissionRepository Repository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AuditLogRepository AuditLogRepository;
    @Autowired
    private RolePermissionsRepository rolePermissionsRepository;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private ProjectRepository ProjectRepository;
    @Autowired
    private StatusRepository StatusRepository;
    @Autowired
    private TaskRepository TaskRepository;
    @Autowired
    private TaskParticipantRepository TaskParticipantRepository;
    @Autowired
    private UserRepository UserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public PermissionController(FieldHelper fieldHelper) {

        this.fieldHelper = fieldHelper;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Permission> entitie = Repository.findAll();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Role> roles = (List<Role>) roleRepository.findAll();

            List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);
        model.addAttribute("roles", roles);
        model.addAttribute("entities", entitie);
        model.addAttribute("fields", fields);
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("modelName", modelName);
        return "CRUD/index";
    }
    @GetMapping("/new")
    public String newPerson(Model model) {
        Iterable<Permission> entitie = Repository.findAll();

        model.addAttribute("entity", new Permission("example_permision"));
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);
        return "CRUD/CRU";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("entity") Permission entity) {
        Repository.save(entity);
        return "redirect:/"+ modelName;
    }



    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Permission> entityOptional = Repository.findById(id);

        Permission entity = entityOptional.get();

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
        Repository.deleteById((long) id);
        return "redirect:/" + modelName;
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") long id, Model model) {
        Optional<Permission> entityOptional = Repository.findById(id);

        Permission entity = entityOptional.get();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Object> fieldValues = fieldHelper.getFieldValues(entity);


        List<Role> roles = (List<Role>) roleRepository.findAll();


        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("roles", roles);
        model.addAttribute("fieldValues", fieldValues);
        model.addAttribute("entity", entity);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);
        return "CRUD/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("entity") Permission entity) {
        Repository.findById(id).ifPresent(existingPerson -> {
            BeanUtils.copyProperties(entity, existingPerson, "id");
            Repository.save(existingPerson);
        });
        return "redirect:/" + modelName;
    }
}

