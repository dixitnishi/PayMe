package com.walletapplication.payme.security;

import com.walletapplication.payme.model.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;


@Getter
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private String accountNumber;

    private String name;

    private String email;

    private String password;

    private double balance;

    public static UserDetailsImpl build(Account account) {
        return new UserDetailsImpl(
                account.getAccountNumber(),
                account.getName(),
                account.getEmail(),
                account.getPassword(),
                account.getBalance()
        );
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
