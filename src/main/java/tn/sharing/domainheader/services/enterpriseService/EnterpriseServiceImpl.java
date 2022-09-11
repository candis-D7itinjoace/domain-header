package tn.sharing.domainheader.services.enterpriseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.sharing.domainheader.repositories.EnterpriseRepo;

@Service
public class EnterpriseServiceImpl implements EnterpriseService{

    @Autowired
    private EnterpriseRepo enterpriseRepo;

    @Override
    public boolean checkIfEnterpriseExists(Long enterpriseId) {

        return enterpriseRepo.findById(enterpriseId).isPresent();
    }
}
