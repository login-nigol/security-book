package security_book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = {"security_book.services.jwt", "security_book.configuration"})
public class SecurityBookApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityBookApplication.class, args);
	}

}
