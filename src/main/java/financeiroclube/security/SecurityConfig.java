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
				//.antMatchers("/**").hasAnyRole("ADMIN","USER")
				//.antMatchers("/socio/**").hasAuthority("SOCIO")
				//.antMatchers("/admin/**").hasAuthority("ADMIN")
                //.antMatchers(HttpMethod.GET,"/cofres/cadastro").hasRole("ADMIN")
                .antMatchers("/categorias/cadastro").hasRole("ADMIN")
				.antMatchers("/cofres/cadastro").hasRole("ADMIN")
				.antMatchers("/fornecedores/cadastro").hasRole("ADMIN")
                .antMatchers("/movimentos/cadastro").hasRole("ADMIN")
                .antMatchers("/pendencias/cadastro").hasRole("ADMIN")
                .antMatchers("/periodos/cadastro").hasRole("ADMIN")
                .antMatchers("/usuarios/cadastro").hasRole("ADMIN")
                //.antMatchers("/{idMovimento}/cadastro").hasRole("ADMIN")
                .antMatchers("/categorias/excluir").hasRole("ADMIN")
				.antMatchers("/cofres/excluir").hasRole("ADMIN")
				.antMatchers("/fornecedores/excluir").hasRole("ADMIN")
				.antMatchers("/movimentos/excluir").hasRole("ADMIN")
				.antMatchers("/pendencias/excluir").hasRole("ADMIN")
				.antMatchers("/periodos/excluir").hasRole("ADMIN")
				//Usuarios
	            .antMatchers("/usuarios/adicionar").hasRole("ADMIN")
	            .antMatchers("/usuarios/editusuario/{id}").hasRole("ADMIN")
	            .antMatchers("/usuarios/deletar/{id}").hasRole("ADMIN")
				//.antMatchers(HttpMethod.GET,"/relatorios/atraso").hasRole("ADMIN")
				.anyRequest().authenticated()
				.and().formLogin().loginPage("/usuarios/logar").defaultSuccessUrl("/",true)
				.failureUrl("/usuarios/logar").permitAll()
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/usuarios/logar").permitAll();
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
