package ua.epam.springapp.integration;

import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import ua.epam.springapp.repository.AccountRepository;
import ua.epam.springapp.repository.DeveloperRepository;
import ua.epam.springapp.repository.SkillRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@ContextConfiguration
public abstract class BaseSpringIT {

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected AccountRepository accountRepository;
    @Autowired
    protected DeveloperRepository developerRepository;
    @Autowired
    protected SkillRepository skillRepository;

    @Configuration
    @EnableWebMvc
    @ComponentScan("ua.epam.springapp")

    protected static class TestContextConfiguration {

        @Bean
        public MockMvc mockMvc(WebApplicationContext webApplicationContext) {
            return MockMvcBuilders
                    .webAppContextSetup(webApplicationContext)
                    .apply(SecurityMockMvcConfigurers.springSecurity())
                    .alwaysDo(print())
                    .build();
        }

        @Bean
        @Primary
        public AccountRepository accountRepository() {
            return Mockito.mock(AccountRepository.class);
        }

        @Bean
        @Primary
        public DeveloperRepository developerRepository() {
            return Mockito.mock(DeveloperRepository.class);
        }

        @Bean
        @Primary
        public SkillRepository skillRepository() {
            return Mockito.mock(SkillRepository.class);
        }
    }
}
