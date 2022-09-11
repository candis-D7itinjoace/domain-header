package tn.sharing.domainheader.services.userService;

import org.springframework.data.domain.Page;
import tn.sharing.domainheader.dto.AddOwnerModel;
import tn.sharing.domainheader.entities.AppUser;
import tn.sharing.domainheader.entities.VerificationToken;

public interface UserService {
    AppUser addOwner(AddOwnerModel ownerModel);

    AppUser findUserByEmail(String email) throws Exception;

    void saveVerificationTokenForUser(AppUser user, String token);

    String validateVerificationTokenForUser(String token);

    VerificationToken generateNewTokenForUser(String oldToken);

    boolean checkIfUserExists(Long userId);

//    Page<AppUser> getEmployeeList(int pageSize, int pageNumber);
}
