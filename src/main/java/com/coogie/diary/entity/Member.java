package com.coogie.diary.entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userId;
    private String password;
    private String phone;
    private String nickname;

    @CreationTimestamp
    private LocalDateTime createdDateTime;
    @UpdateTimestamp
    private LocalDateTime updatedDateTime;

    /**회원등록시 정보를 정형화 한다.
     *
     * password = encondig
     * userId = lowcase
     * phone = replace '-' -> ''
     *
     * @param password
     * @return Member
     */
    public Member objectSetPattern(String password){
        this.userId = this.userId.toLowerCase();
        this.phone = this.phone.replaceAll("-", "");
        this.password = password;
        return this;
    }
}
