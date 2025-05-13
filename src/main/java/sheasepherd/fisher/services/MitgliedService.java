package sheasepherd.fisher.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sheasepherd.fisher.entitys.Mitglied;
import sheasepherd.fisher.repositorys.MitgliedRepository;

@Service
public class MitgliedService {

    @Autowired
    private MitgliedRepository mitgliedRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void save(Mitglied mitglied){
        mitglied.setPasswort(passwordEncoder.encode(mitglied.getPasswort()));
        mitgliedRepository.save(mitglied);
    }

    public Mitglied findByEmail(String email){
        return mitgliedRepository.findByEmail(email);
    }

}
