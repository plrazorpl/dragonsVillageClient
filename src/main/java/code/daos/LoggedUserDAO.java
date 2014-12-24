package code.daos;

import dragonsVillage.dtos.LoginUserDTO;

public class LoggedUserDAO {
    private LoginUserDTO loginUserDTO;

    public LoginUserDTO getLoginUserDTO() {
        return loginUserDTO;
    }

    public void setLoginUserDTO(LoginUserDTO loginUserDTO) {
        this.loginUserDTO = loginUserDTO;
    }
}
