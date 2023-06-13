package ifrn.laj.cume.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.dtos.LunchRequestDTO;
import ifrn.laj.cume.models.LunchRequest;
import ifrn.laj.cume.repositories.LunchRequestRepository;

@Controller
@RequestMapping("/lunchrequests")
public class LunchRequestController {

	@Autowired
	private LunchRequestRepository lrr;

	@GetMapping("/form")
	@PreAuthorize("hasRole('STUDENT')")
	public String form() {
		return "lunchrequests/form";
	}

	@PostMapping
	@PreAuthorize("hasRole('STUDENT')")
	public String save(LunchRequestDTO lunchRequestDTO, RedirectAttributes attributes) {
		System.out.println(lunchRequestDTO);
		if (!lunchRequestDTO.isConfirmation()) {
			attributes.addFlashAttribute("msg", "Você deve selecionar o botão de confirmação!");
			return "redirect:lunchrequests/form";
		}

		LunchRequest newRequest = new LunchRequest();
		newRequest.setDateTimeOfRequest(LocalDateTime.now());
		newRequest.setLunchRequestDate(LocalDate.now());

		lrr.save(newRequest);

		return "redirect:lunchrequests";
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ASAES', 'COORD')")
	public ModelAndView lunchRequestsForToday() {
		ModelAndView md = new ModelAndView("lunchrequests/list");
		md.addObject("lunchRequests", lrr.findAll());
		return md;
	}

}
