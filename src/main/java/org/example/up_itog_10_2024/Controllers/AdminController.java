package org.example.up_itog_10_2024.Controllers;

import org.example.up_itog_10_2024.DAO.FieldHelper;
import org.example.up_itog_10_2024.DAO.GenericDAO;
import org.example.up_itog_10_2024.Models.Permission;
import org.example.up_itog_10_2024.Models.Role;
import org.example.up_itog_10_2024.Models.User;
import org.example.up_itog_10_2024.Repositories.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.ui.Model;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasAnyAuthority('ADMIN')")
public class AdminController {
    private GenericDAO<User> modelDAO;
    private FieldHelper fieldHelper;
    private Class interactive_class = User.class;

    private String modelName = "admin";
    @Autowired
    public UserRepository Repository;
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
    public AdminController(FieldHelper fieldHelper) {

        this.fieldHelper = fieldHelper;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<User> entitie = Repository.findAll();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
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
        Iterable<User> entitie = Repository.findAll();

        model.addAttribute("entity", new User( "", "0", true, "", true, roleRepository.getRoleById(1)));
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);
        return "CRUD/CRU";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("entity") User person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        Repository.save(person);
        return "redirect:/"+ modelName;
    }



    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<User> entityOptional = Repository.findById(id);

        User entity = entityOptional.get();

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
        Optional<User> entityOptional = Repository.findById(id);

        User entity = entityOptional.get();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Object> fieldValues = fieldHelper.getFieldValues(entity);


        List<Role> roles = (List<Role>) roleRepository.findAll();

        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("roles", roles);
        model.addAttribute("fieldValues", fieldValues);
        model.addAttribute("entity", entity);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);
        return "CRUD/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("entity") User entity, @RequestParam("newPassword") String newPassword) {
        Repository.findById(id).ifPresent(existingPerson -> {
            if (newPassword != null && !newPassword.isEmpty()) {
                existingPerson.setPassword(passwordEncoder.encode(newPassword));
            }

            BeanUtils.copyProperties(entity, existingPerson, "id", "password");
            Repository.save(existingPerson);
        });
        return "redirect:/" + modelName;
    }


}

