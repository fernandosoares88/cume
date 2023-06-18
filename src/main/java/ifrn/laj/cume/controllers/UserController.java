package ifrn.laj.cume.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.laj.cume.dtos.UserChangePasswordDTO;
import ifrn.laj.cume.models.User;
import ifrn.laj.cume.repositories.UserRepository;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserRepository ur;

	@GetMapping("/changepassword")
	@PreAuthorize("isAuthenticated()")
	public String formChangePass(UserChangePasswordDTO userChangePasswordDTO) {
		return "users/changepassword";
	}

	@PostMapping("/changepassword")
	@PreAuthorize("isAuthenticated()")
	public String changePass(@Valid UserChangePasswordDTO userDTO, BindingResult result,
			RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			return formChangePass(userDTO);
		}
		
		User principal = getPrincipal();
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if(!encoder.matches(userDTO.getActualPass(), principal.getPassword())) {
			attributes.addFlashAttribute("msg", "Senha atual incorreta.");
			return "redirect:/users/changepassword";
		}
		
		if(!userDTO.getNewPass().equals(userDTO.getConfirmPass())) {
			attributes.addFlashAttribute("msg", "A nova senha deve ser igual a confirmação.");
			return "redirect:/users/changepassword";
		}
		
		principal.setPassword(encoder.encode(userDTO.getNewPass()));
		ur.save(principal);

		attributes.addFlashAttribute("msg", "Senha alterada.");
		
		return "redirect:/users/changepassword";
	}

	private User getPrincipal() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails user = (UserDetails) authentication.getPrincipal();
		return ur.findByRegistration(user.getUsername()).get();
	}

}
