package ifrn.laj.cume;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CumeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CumeApplication.class, args);
//		System.out.println(new BCryptPasswordEncoder().encode("123"));
	}

}
