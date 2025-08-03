package Springboot.springboot.Response;

import Springboot.springboot.Dto.TransactionDto;
import Springboot.springboot.Dto.UserAuditDto;
import lombok.Data;

import java.util.List;

@Data
public class UserResponse {
    private String userName;
    private List<UserAuditDto> userAuditDtos;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<UserAuditDto> getUserAuditDtos() {
        return userAuditDtos;
    }

    public void setUserAuditDtos(List<UserAuditDto> userAuditDtos) {
        this.userAuditDtos = userAuditDtos;
    }
}
