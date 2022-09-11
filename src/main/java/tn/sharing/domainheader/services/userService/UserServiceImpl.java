package tn.sharing.domainheader.services.userService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.sharing.domainheader.dto.AddOwnerModel;
import tn.sharing.domainheader.entities.*;
import tn.sharing.domainheader.repositories.EnterpriseRepo;
import tn.sharing.domainheader.repositories.RoleRepo;
import tn.sharing.domainheader.repositories.UserRepo;
import tn.sharing.domainheader.repositories.VerificationTokenRepo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EnterpriseRepo enterpriseRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VerificationTokenRepo verificationTokenRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Override
    public AppUser addOwner(AddOwnerModel ownerModel) {


        if(ownerModel.getPassword().equals(ownerModel.getMatchingPassword())){
            Enterprise enterprise = new Enterprise();
            enterprise.setName(ownerModel.getEnterpriseModel().getName());
            enterprise.setDescription(ownerModel.getEnterpriseModel().getDescription());
            enterprise.setPhoneNumber(ownerModel.getEnterpriseModel().getPhoneNumber());
            enterpriseRepo.save(enterprise);

            List<Role> roles = new ArrayList<>();
            roles.add(roleRepo.findByRoleName(ERole.ROLE_OWNER));
            AppUser owner = new AppUser();
            owner.setEmail(ownerModel.getEmail());
            owner.setEnterprise(enterprise);
            owner.setFirstName(ownerModel.getFirstName());
            owner.setLastName(ownerModel.getLastName());
            owner.setRoles(roles);
            owner.setPassword(passwordEncoder.encode(ownerModel.getPassword()));
            return userRepo.save(owner);
        }
       return null;
    }

    @Override
    public AppUser findUserByEmail(String email) throws Exception {

        try {
            return userRepo.findByEmail(email);
        }catch (UsernameNotFoundException exception){
            throw new Exception("Username Not Found Exception", exception);
        }
    }

    @Override
    public void saveVerificationTokenForUser(AppUser user, String token) {

        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepo.save(verificationToken);

    }

    @Override
    public String validateVerificationTokenForUser(String token) {
        VerificationToken verificationToken = verificationTokenRepo.findByToken(token);
        if(verificationToken == null) {
            return "invalid";
        }
        Calendar cal = Calendar.getInstance();
        if(verificationToken.getExpirationTime().getTime() - cal.getTime().getTime() <= 0){
            verificationTokenRepo.delete(verificationToken);
            return "expired";
        }
        AppUser user = verificationToken.getUser();
        user.setEnabled(true);
        userRepo.save(user);


        return "account enabled";
    }

    @Override
    public VerificationToken generateNewTokenForUser(String oldToken) {
        VerificationToken token = verificationTokenRepo.findByToken(oldToken);
        token.setToken(UUID.randomUUID().toString());
        return verificationTokenRepo.save(token);
    }

    @Override
    public boolean checkIfUserExists(Long userId) {

        return userRepo.findById(userId).isPresent();

    }

//    @Override
//    public Page<AppUser> getEmployeeList(int pageSize, int pageNumber) {
//
//        Pageable pageable = PageRequest.of(pageNumber, pageSize);
//        List<Role>  roles = new ArrayList<>();
//        Role role = new Role();
//        role.setRoleName(ERole.ROLE_OWNER);
//        roles.add(role);
//        Page<AppUser> employeeList = userRepo.findByRoles(roles, pageable);
//        return Page.empty();
//    }

}