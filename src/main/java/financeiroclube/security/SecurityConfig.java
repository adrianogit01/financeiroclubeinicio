package financeiroclube.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementsUserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().disable().
			authorizeRequests()
				.antMatchers(HttpMethod.GET, "/").hasAnyRole("ADMIN","USER")
				//.antMatchers("/funcionario/**").hasAnyRole("ADMIN","USER")
				//.antMatchers("/socio/**").hasAuthority("SOCIO")
				//.antMatchers("/admin/**").hasAuthority("ADMIN")
				.antMatchers(HttpMethod.GET,"/funcionario/cofres/cadastro").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/funcionario/movimentos/cadastro").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/funcionario/{idMovimento}/cadastro").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/deletar/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/atualizar/{id}").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/usuario/users").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST,"/usuario/adicionar").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET,"/usuario/getusuario/{id}").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/usuario/logar").defaultSuccessUrl("/",true)
				.failureUrl("/usuario/logar").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/usuario/logar").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder ());
	}	

	@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**");
	}
}
