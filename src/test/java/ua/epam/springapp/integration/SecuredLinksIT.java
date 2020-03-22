package ua.epam.springapp.integration;

import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SecuredLinksIT extends BaseSpringIT {

    @Test
    public void shouldGetSecuredResourceUser() throws Exception {
        mockMvc.perform(get("/user").with(user("user").password("user").roles("USER"))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(forwardedUrl("/WEB-INF/view/user.jsp"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSecuredResourceAdmin() throws Exception {
        mockMvc.perform(get("/admin").with(user("admin").password("admin").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(forwardedUrl("/WEB-INF/view/admin.jsp"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetSecuredResourceAdminToUser() throws Exception {
        mockMvc.perform(get("/user").with(user("admin").password("admin").roles("ADMIN"))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(forwardedUrl("/WEB-INF/view/user.jsp"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldGetRedirectUserToLoginPage() throws Exception {
        mockMvc.perform(get("/user")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldGetRedirectAdminToLoginPage() throws Exception {
        mockMvc.perform(get("/admin")
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(redirectedUrl("http://localhost/login"))
                .andExpect(status().isFound());
    }

    @Test
    public void shouldGetForbiddenResponseAnotherAuthority() throws Exception {
        mockMvc.perform(get("/admin").with(user("user").password("user").roles("USER"))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldGetForbiddenResponseAuthorityToLoginPage() throws Exception {
        mockMvc.perform(get("/login").with(user("user").password("user").roles("USER"))
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden());
    }
}
