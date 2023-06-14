package ifrn.laj.cume.controllers;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.dtos.StudentDTO;
import ifrn.laj.cume.models.Role;
import ifrn.laj.cume.models.Student;
import ifrn.laj.cume.models.User;
import ifrn.laj.cume.repositories.ClazzRepository;
import ifrn.laj.cume.repositories.RoleRepository;
import ifrn.laj.cume.repositories.StudentRepository;
import ifrn.laj.cume.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/students")
public class StudentController {
	
	@Autowired
	private StudentRepository sr;
	@Autowired
	private UserRepository ur;
	@Autowired
	private RoleRepository rr;
	@Autowired
	private ClazzRepository cr;
	
	@GetMapping("/form")
	@PreAuthorize("hasAnyRole('ADMIN', 'ASAES', 'COORD')")
	public ModelAndView form(StudentDTO studentDTO) {
		ModelAndView md = new ModelAndView("students/form");
		md.addObject("clazzs", cr.findAllByOrderByName());
		return md;
	}
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ASAES', 'COORD')")
	public ModelAndView save(@Valid StudentDTO studentDTO, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return form(studentDTO);
		}
		
		Optional<User> optUser = ur.findByRegistration(studentDTO.getRegistration());
		if(optUser.isPresent()) {
			attributes.addFlashAttribute("msg", "Matrícula já cadastrada");
			return new ModelAndView("redirect:students/form");
		}
		
		Student newStudent = new Student();
		newStudent.setName(studentDTO.getName());
		newStudent.setRegistration(studentDTO.getRegistration());
		newStudent.setClazz(studentDTO.getClazz());
		newStudent.setPassword(new BCryptPasswordEncoder().encode("123"));
		
		Role role = rr.findByName("ROLE_STUDENT").get();
		ArrayList<Role> roles = new ArrayList<Role>();
		roles.add(role);
		newStudent.setRoles(roles);
		
		sr.save(newStudent);
		
		attributes.addFlashAttribute("msg", "Aluno cadastrado");
		
		return new ModelAndView("redirect:students");
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ASAES', 'COORD')")
	public ModelAndView list() {
		ModelAndView md = new ModelAndView("students/list");
		md.addObject("students", sr.findAllByOrderByName());
		return md;
	}

}
