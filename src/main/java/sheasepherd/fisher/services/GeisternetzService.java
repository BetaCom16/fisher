package sheasepherd.fisher.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sheasepherd.fisher.entitys.Geisternetz;
import sheasepherd.fisher.repositorys.GeisternetzRepository;

import java.util.List;

@Service
public class GeisternetzService {

    @Autowired
    private GeisternetzRepository geisternetzRepository;

    public List<Geisternetz> zeigeAlleGeisternetze(){
        return geisternetzRepository.findAll();
    }

}
