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
                .antMatchers("/css/**", "/img/**", "/js/**").permitAll()
				//.antMatchers(HttpMethod.GET, "/").hasAnyRole("ADMIN","USER")
				//.antMatchers("/funcionario/**").hasAnyRole("ADMIN","USER")
				//.antMatchers("/socio/**").hasAuthority("SOCIO")
				//.antMatchers("/admin/**").hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.GET,"/funcionario/cofres/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/categorias/cadastro").hasRole("ADMIN")
				.antMatchers("/funcionario/cofres/cadastro").hasRole("ADMIN")
				.antMatchers("/funcionario/fornecedores/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/movimentos/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/pendencias/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/periodos/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/usuarios/cadastro").hasRole("ADMIN")
                .antMatchers("/funcionario/{idMovimento}/cadastro").hasRole("ADMIN")
				.antMatchers("/deletar/{id}").hasRole("ADMIN")
				.antMatchers("/atualizar/{id}").hasRole("ADMIN")
				//.antMatchers("/funcionario/usuarios").hasRole("ADMIN")
				.antMatchers("/funcionario/usuarios/adicionar").hasRole("ADMIN")
				.antMatchers("/funcionario/usuarios/getusuario/{id}").hasRole("ADMIN")
				//.antMatchers(HttpMethod.GET,"/funcionario/relatorios/atraso").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/funcionario/usuarios/logar").defaultSuccessUrl("/",true)
				.failureUrl("/funcionario/usuarios/logar").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/funcionario/usuarios/logar").permitAll();
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder ());
	}	

	/*@Override
	public void configure(WebSecurity web) throws Exception{
		web.ignoring().antMatchers("/css/**", "/img/**", "/js/**");
	}*/
}
