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
@RequestMapping("/project")
@PreAuthorize("hasAnyAuthority('PROJECT_MANAGER')")
public class ProjectController {
    private GenericDAO<Project> modelDAO;
    private FieldHelper fieldHelper;
    private Class interactive_class = Project.class;

    private String modelName = "project";
    public final ProjectRepository repository;
    public final RoleRepository roleRepository;
    private final AuditLogRepository auditLogRepository;
    private final PermissionRepository permissionRepository;
    private final ProjectRepository projectRepository;
    private final StatusRepository statusRepository;
    private final TaskRepository taskRepository;
    private final TaskParticipantRepository taskParticipantRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public ProjectController(FieldHelper fieldHelper, ProjectRepository repository, RoleRepository roleRepository, AuditLogRepository auditLogRepository, PermissionRepository permissionRepository, ProjectRepository projectRepository, StatusRepository statusRepository, TaskRepository taskRepository, TaskParticipantRepository taskParticipantRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {

        this.fieldHelper = fieldHelper;
        this.repository = repository;
        this.roleRepository = roleRepository;
        this.auditLogRepository = auditLogRepository;
        this.permissionRepository = permissionRepository;
        this.projectRepository = projectRepository;
        this.statusRepository = statusRepository;
        this.taskRepository = taskRepository;
        this.taskParticipantRepository = taskParticipantRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String index(Model model) {
        Iterable<Project> entitie = repository.findAll();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        model.addAttribute("tasks1", tasks);
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
        Iterable<Project> entitie = repository.findAll();

        List<Task> tasks = (List<Task>) taskRepository.findAll();
        model.addAttribute("entity", new Project("", null,1,tasks));


        model.addAttribute("tasks1", tasks);
        List<Status> statuses = (List<Status>) statusRepository.findAll();
        model.addAttribute("statuses", statuses);
        List<Project> projects = (List<Project>) projectRepository.findAll();
        model.addAttribute("projects", projects);
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);
        List<Role> roles = (List<Role>) roleRepository.findAll();
        model.addAttribute("roles", roles);
        List<Permission> permissions = (List<Permission>) permissionRepository.findAll();
        model.addAttribute("permissions", permissions);

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        model.addAttribute("fields", fields);
        model.addAttribute("modelName", modelName);

        return "CRUD/CRU";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("entity") Project entity, @RequestParam("tasks") List<Long> taskIds) {
        // Находим задачи по идентификаторам
        List<Task> tasks = (List<Task>) taskRepository.findAllById(taskIds);

        // Устанавливаем задачи в проект
        entity.setTasks(tasks);
        for (Task task : tasks) {
            task.setProject(entity);
        }

        // Сохраняем проект и связанные задачи
        repository.save(entity);

        return "redirect:/" + modelName;
    }


    @GetMapping("/{id}/show")
    public String show(@PathVariable("id") Long id, Model model) {
        Optional<Project> entityOptional = repository.findById(id);

        Project entity = entityOptional.get();

        List<String> fields = FieldHelper.getFieldNames(interactive_class);
        List<Object> fieldValues = fieldHelper.getFieldValues(entity);
        List<Status> statuses = (List<Status>) statusRepository.findAll();
        model.addAttribute("statuses", statuses);
        List<Project> projects = (List<Project>) projectRepository.findAll();
        model.addAttribute("projects", projects);
        List<Task> tasks = (List<Task>) taskRepository.findAll();
        model.addAttribute("tasks1", tasks);
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
        Optional<Project> entitie = repository.findById(id);
        Project entity = entitie.get();
        model.addAttribute("entity", entity);

        model.addAttribute("statuses", statusRepository.findAll());
        model.addAttribute("projects", projectRepository.findAll());
        model.addAttribute("tasks1", taskRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        model.addAttribute("permissions", permissionRepository.findAll());
        model.addAttribute("roles", roleRepository.findAll());
        model.addAttribute("fields", FieldHelper.getFieldNames(interactive_class));
        model.addAttribute("fieldHelper", fieldHelper);
        model.addAttribute("modelName", modelName);



        return "CRUD/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable("id") long id, @ModelAttribute("entity") Project entity, @RequestParam("tasks") List<Long> taskIds) {


        repository.findById(id).ifPresent(existingEntity -> {
            BeanUtils.copyProperties(entity, existingEntity, "id", "tasks");

            // Находим задачи по идентификаторам
            List<Task> tasks = (List<Task>) taskRepository.findAllById(taskIds);

            // Устанавливаем задачи в проект
            entity.setTasks(tasks);
            for (Task task : tasks) {
                task.setProject(entity);
            }
            repository.save(entity);

        });

        return "redirect:/" + modelName;
    }
}
