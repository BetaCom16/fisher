package sheasepherd.fisher.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import sheasepherd.fisher.entitys.Mitglied;
import sheasepherd.fisher.services.MitgliedService;

@Configuration
@EnableWebSecurity
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
                    .roles(mitglied.getRolle())
                    .build();
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/bestaetigung", "/gemeldeteGeisternetze",
                                "/aktuelles", "/werdeMitglied", "/kontakt", "/test",
                                "/login", "/register", "/h2-console/**", "/css/**",
                                "/js/**").permitAll()
                        .requestMatchers("/bergung", "/meineBergungen", "/netz-als-geborgen", "/netz-freigeben").hasRole("Berger")
                        .requestMatchers(
                                new AntPathRequestMatcher("/images/**"),
                                new AntPathRequestMatcher("/css/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());


        //Für H2 Testzwecke
        http.headers().frameOptions().sameOrigin();
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}