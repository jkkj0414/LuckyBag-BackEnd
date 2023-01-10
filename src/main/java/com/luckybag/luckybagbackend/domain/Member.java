package com.luckybag.luckybagbackend.domain;

import com.luckybag.luckybagbackend.domain.DTO.MemberDTO;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private String memberId;

    @Column(name = "user_password", nullable = false)
    private String memberPassword;

    @OneToOne(mappedBy = "member")
    private LuckyBag luckyBag;

    @Column(name = "nickname", nullable = false)
    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER)//값 타입 컬렉션을 매핑할 때 사용한다.
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Column
    @Convert(converter = BooleanToYNConverter.class)
    private boolean hasLuckyBag;
    public void updateHasLuckyBag(boolean hasLuckyBag) {
        this.hasLuckyBag = hasLuckyBag;
    }

    public MemberDTO toDTO() {
        return MemberDTO.builder()
                .id(id)
                .hasLuckyBag(hasLuckyBag)
                .nickname(nickname)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return memberPassword;
    }

    @Override
    public String getUsername() {
        return memberId;
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

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.memberPassword = passwordEncoder.encode(memberPassword);
    }
}