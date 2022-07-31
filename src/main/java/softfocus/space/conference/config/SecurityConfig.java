package softfocus.space.conference.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.PortMapperImpl;
import org.springframework.security.web.PortResolverImpl;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import softfocus.space.conference.module.member.MemberRepository;
import softfocus.space.conference.module.member.auth.OAuthMemberService;
import softfocus.space.conference.module.member.auth.LoginSuccessHandler;

import java.util.Collections;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final OAuthMemberService oAuthMemberService;

    private final MemberRepository memberRepository;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {

        PortMapperImpl portMapper = new PortMapperImpl();
        portMapper.setPortMappings(Collections.singletonMap("8080","8080"));
        PortResolverImpl portResolver = new PortResolverImpl();
        portResolver.setPortMapper(portMapper);
        LoginUrlAuthenticationEntryPoint entryPoint = new LoginUrlAuthenticationEntryPoint(
                "/loginPage");
        entryPoint.setPortMapper(portMapper);
        entryPoint.setPortResolver(portResolver);


        http.csrf().disable();
        http.headers().frameOptions().sameOrigin().and()
                .exceptionHandling()
                .authenticationEntryPoint(entryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                .antMatchers(
                        "/login/**",
                        "/loginPage",
                        "/js/**",
                        "/socket/**",
                        "/web-socket",
                        "/web-socket/**",
                        "/content/**"
                )
                .permitAll()
                .anyRequest()
//                .authenticated()
                .permitAll()
                .and()
                .oauth2Login(oauth ->
                        oauth.loginPage("/loginPage")
                                .successHandler(successHandler())
                                .userInfoEndpoint()
                                .userService(oAuthMemberService)
                );

        return http.build();
    }


    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return new LoginSuccessHandler(memberRepository);
    }
}
