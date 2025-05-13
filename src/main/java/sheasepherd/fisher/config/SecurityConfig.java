package sheasepherd.fisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import sheasepherd.fisher.entitys.Mitglied;
import sheasepherd.fisher.services.MitgliedService;

@Configuration
public class SecurityConfig {

    private final MitgliedService mitgliedService;

    public SecurityConfig(MitgliedService mitgliedService){
        this.mitgliedService = mitgliedService;
    }

    @Bean
    public UserDetailsService userDetailsService(){
        return email -> {
            Mitglied mitglied = mitgliedService.findByEmail(email);
            if(mitglied == null) throw new UsernameNotFoundException("User not found");
            return org.springframework.security.core.userdetails.User
                    .withUsername(mitglied.getEmail())
                    .password(mitglied.getPasswort())
                    .roles("USER")
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/melden", "/gemeldeteGeisternetze",
                                "/aktuelles", "/werdeMitglied", "/kontakt",
                                "/test", "/register", "/h2-console/**",
                                "/css/**", "/js/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .defaultSuccessUrl("/welcome", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        //Für H2 Testzwecke
        http.headers().frameOptions().sameOrigin();
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}
