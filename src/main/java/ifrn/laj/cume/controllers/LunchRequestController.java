package ifrn.laj.cume.controllers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.dtos.LunchRequestDTO;
import ifrn.laj.cume.models.LunchRequest;
import ifrn.laj.cume.models.Student;
import ifrn.laj.cume.repositories.LunchRequestRepository;
import ifrn.laj.cume.repositories.StudentRepository;

@Controller
@RequestMapping("/lunchrequests")
public class LunchRequestController {
	
	private static final int REQUEST_HOUR_LIMIT = 16;

	@Autowired
	private LunchRequestRepository lrr;
	@Autowired
	private StudentRepository sr;

	@GetMapping("/form")
	@PreAuthorize("hasRole('STUDENT')")
	public ModelAndView form() {
		LocalDate today = LocalDate.now();
		LocalDate tomorow = today.plusDays(1);
		LocalDateTime now = LocalDateTime.now();

		Student principal = getPrincipal();

		Optional<LunchRequest> optLunchRequest = lrr.findByStudentAndLunchRequestDate(principal, tomorow);
		if (optLunchRequest.isPresent()) {
			return new ModelAndView("redirect:/lunchrequests/" + optLunchRequest.get().getId());
		}

		ModelAndView md = new ModelAndView("lunchrequests/form");
		if (now.getHour() >= REQUEST_HOUR_LIMIT) {
			md.setViewName("lunchrequests/timeisover");
		}
		Optional<LunchRequest> todayLunchRequest = lrr.findByStudentAndLunchRequestDate(principal, today);
		if (todayLunchRequest.isPresent()) {
			md.addObject("todayLunchRequest", todayLunchRequest.get());
		}
		return md;
	}

	@PostMapping
	@PreAuthorize("hasRole('STUDENT')")
	public String save(LunchRequestDTO lunchRequestDTO, RedirectAttributes attributes) {
		System.out.println(lunchRequestDTO);

		LocalDate today = LocalDate.now();
		LocalDate tomorow = today.plusDays(1);
		LocalDateTime now = LocalDateTime.now();

		if (now.getHour() >= REQUEST_HOUR_LIMIT) {
			return "redirect:/lunchrequests/form";
		}

		Student principal = getPrincipal();

		Optional<LunchRequest> optLunchRequest = lrr.findByStudentAndLunchRequestDate(principal, tomorow);

		if (optLunchRequest.isPresent()) {
			return "redirect:/lunchrequests/" + optLunchRequest.get().getId();
		}

		if (!lunchRequestDTO.isConfirmation()) {
			attributes.addFlashAttribute("msg", "Você deve selecionar o botão de confirmação!");
			return "redirect:/lunchrequests/form";
		}

		LunchRequest newRequest = new LunchRequest();
		newRequest.setDateTimeOfRequest(now);
		newRequest.setLunchRequestDate(tomorow);
		newRequest.setStudent(principal);

		lrr.save(newRequest);

		return "redirect:/lunchrequests/form";
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('STUDENT')")
	public ModelAndView detailLunchRequest(@PathVariable Long id) {

		Optional<LunchRequest> opt = lrr.findById(id);
		if (opt.isEmpty()) {
			return new ModelAndView("redirect:/");
		}

		Student principal = getPrincipal();
		LunchRequest lunchRequest = opt.get();

		if (!principal.getRegistration().equals(lunchRequest.getStudent().getRegistration())) {
			return new ModelAndView("redirect:/");
		}

		ModelAndView md = new ModelAndView("lunchrequests/details");
		md.addObject("lunchRequest", lunchRequest);
		Optional<LunchRequest> todayLunchRequest = lrr.findByStudentAndLunchRequestDate(principal, LocalDate.now());
		if (todayLunchRequest.isPresent()) {
			md.addObject("todayLunchRequest", todayLunchRequest.get());
		}
		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() < REQUEST_HOUR_LIMIT) {
			md.addObject("canCacel", true);
		}
		return md;
	}

	@PostMapping("/{id}/cancel")
	@PreAuthorize("hasRole('STUDENT')")
	public String cancelLunchRequest(@PathVariable Long id) {

		LocalDateTime now = LocalDateTime.now();
		if (now.getHour() > REQUEST_HOUR_LIMIT) {
			return "redirect:/lunchrequests/" + id;
		}
		
		Optional<LunchRequest> opt = lrr.findById(id);
		if (opt.isEmpty()) {
			return "redirect:/";
		}

		Student principal = getPrincipal();
		LunchRequest lunchRequest = opt.get();

		if (!principal.getRegistration().equals(lunchRequest.getStudent().getRegistration())) {
			return "redirect:/";
		}

		lrr.delete(lunchRequest);

		return "redirect:/lunchrequests/form";
	}

	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'ASAES', 'COORD')")
	public ModelAndView lunchRequestsForToday(LocalDate lunchRequestDate) {
		
		if(lunchRequestDate == null) {
			lunchRequestDate = LocalDate.now();
		}
		
		ModelAndView md = new ModelAndView("lunchrequests/list");
		md.addObject("lunchRequests", lrr.findAllByLunchRequestDateOrderByStudentName(lunchRequestDate));
		md.addObject("lunchRequestDate", lunchRequestDate);
		return md;
	}

	private Student getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return sr.findByRegistration(user.getUsername()).get();
	}

}
