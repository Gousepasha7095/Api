package Springboot.springboot.Dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAuditDto {
    private Long id;
    private String userName;
    private LocalDateTime loginTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(LocalDateTime loginTime) {
        this.loginTime = loginTime;
    }
}
