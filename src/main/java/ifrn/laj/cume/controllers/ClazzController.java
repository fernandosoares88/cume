package ifrn.laj.cume.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.models.Clazz;
import ifrn.laj.cume.repositories.ClazzRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/classes")
public class ClazzController {

	@Autowired
	private ClazzRepository cr;

	@GetMapping("/form")
	@PreAuthorize("hasRole('ADMIN')")
	public String form(Clazz clazz) {
		return "classes/form";
	}

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public String save(@Valid Clazz clazz, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			return form(clazz);
		}

		cr.save(clazz);
		attributes.addFlashAttribute("msg", "Turma cadastrada");

		return "redirect:/classes";
	}

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView list() {
		ModelAndView md = new ModelAndView("classes/list");
		md.addObject("clazzes", cr.findAll());
		return md;
	}

	@GetMapping("/edit/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ModelAndView edit(@PathVariable Long id) {
		ModelAndView md = new ModelAndView("classes/form");

		Optional<Clazz> opt = cr.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/classes");
			return md;
		}

		md.addObject("clazz", opt.get());
		return md;
	}

}
