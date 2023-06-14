package ifrn.laj.cume.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.dtos.PublicServerDTO;
import ifrn.laj.cume.models.PublicServer;
import ifrn.laj.cume.models.Role;
import ifrn.laj.cume.models.User;
import ifrn.laj.cume.repositories.PublicServerRepository;
import ifrn.laj.cume.repositories.RoleRepository;
import ifrn.laj.cume.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/publicservers")
public class PublicServersController {

	@Autowired
	private UserRepository ur;
	@Autowired
	private PublicServerRepository psr;
	@Autowired
	private RoleRepository rr;

	@GetMapping("/form")
	@PreAuthorize("hasRole('ADMIN')")
	public String form(PublicServerDTO publicServerDTO) {
		return "publicservers/form";
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String save(@Valid PublicServerDTO publicServerDTO, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return form(publicServerDTO);
		}

		Optional<User> optUser = ur.findByRegistration(publicServerDTO.getRegistration());
		if (optUser.isPresent()) {
			attributes.addFlashAttribute("msg", "Matrícula já cadastrada");
			return "redirect:publicservers/form";
		}

		PublicServer newServer = new PublicServer();
		newServer.setName(publicServerDTO.getName());
		newServer.setRegistration(publicServerDTO.getRegistration());
		newServer.setPassword(new BCryptPasswordEncoder().encode("123"));

		psr.save(newServer);

		attributes.addFlashAttribute("msg", "Servidor cadastrado");

		return "redirect:publicservers";
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView list() {
		ModelAndView md = new ModelAndView("publicservers/list");

		md.addObject("publicServers", psr.findAll());

		return md;
	}

	@GetMapping("/{registration}")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView selectPublicServer(@PathVariable String registration) {
		Optional<PublicServer> optPublicServer = psr.findByRegistration(registration);

		if (optPublicServer.isEmpty()) {
			return new ModelAndView("redirect:/publicservers");
		}

		Iterable<Role> roles = rr.findAllByOrderByNameAsc();

		ModelAndView mv = new ModelAndView("publicservers/details");
		mv.addObject("publicServer", optPublicServer.get());
		mv.addObject("roles", roles);

		return mv;
	}

	@PostMapping("/{registration}")
	@PreAuthorize("hasRole('ADMIN')")
	public String savePublicServerRoles(PublicServer publicServerForm) {
		
		System.out.println("Chamou o método");

		Optional<PublicServer> optPublicServer = psr.findByRegistration(publicServerForm.getRegistration());
		if (optPublicServer.isEmpty()) {
			return "redirect:/publicservers";
		}

		PublicServer publicServer = optPublicServer.get();

		publicServer.setRoles(publicServerForm.getRoles());

		System.out.println("Usuario form: " + publicServerForm);
		System.out.println("Usuario editado: " + optPublicServer);

		psr.save(publicServer);

		return "redirect:/publicservers/" + publicServerForm.getRegistration();
	}

}
