package com.plim.plimserver.global.domain.mail.domain;

import com.plim.plimserver.global.domain.mail.exception.AuthCodeMismatchException;
import com.plim.plimserver.global.domain.mail.exception.EmailExceptionMessage;
import com.plim.plimserver.global.domain.mail.exception.RequestBeforeCoolTimeException;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@NoArgsConstructor
@Table(name = "email_auth_code")
@Entity
public class EmailAuthCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Column(unique = true, nullable = false)
    private String email;

    @Getter
    @Setter
    @Column(name = "auth_code")
    private String authCode;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    private Timestamp createdDate;

    @Getter
    @UpdateTimestamp
    @Column(name = "modified_date")
    private Timestamp updatedDate;

    @Builder
    public EmailAuthCode(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }

    public void validateCode(String authCode) {
        if (!this.authCode.equals(authCode))
            throw new AuthCodeMismatchException(EmailExceptionMessage.AUTH_CODE_MISMATCH_EXCEPTION);
    }

}
