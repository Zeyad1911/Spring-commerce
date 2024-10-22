package sim.ecommerce.SecurityInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import sim.ecommerce.model.User;
import java.util.ArrayList;
import java.util.Collection;


public class UserInfo implements UserDetails {

    private final User user;


    public UserInfo(User user){
        this.user = user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getPassword() {
        return user.getHashed_password();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }
}
