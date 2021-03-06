package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.entities.Image;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.repositories.ImagesRepository;
import com.searchmedicine.demo.repositories.ListReserverRepository;
import com.searchmedicine.demo.repositories.MedicineRepository;
import com.searchmedicine.demo.services.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


@Service
public class MedicineServiceImpl implements MedicineService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private MedicineRepository medicineRepository;

    @Autowired
    private ImagesRepository imagesRepository;

    @Autowired
    private  ListReserverRepository listReserverRepository;

    @Override
    public List<Medicine> getAllAvailableMedicine() {
        return medicineRepository.getAllByIsExchangeTrue();
    }

    @Override
    public Medicine getMedicine(Long id) {
        return medicineRepository.getById(id);
    }

    @Override
    public List<Medicine> getAllMedicine() {
        return medicineRepository.findAll();
    }

    @Override
    public List<Medicine> getMedicineTopView() {
        return medicineRepository.findTop10ByOrderByViewAmountDesc();
    }


    @Override
    public List<Medicine> getMedicineTopReserved() {
        String sql="select ph.medicine_id  from list_reserver ls inner join pharmacies_medicines ph on ls.pharmacy_medicine_id=ph.id group by ph.medicine_id  order by count(ph.id) desc";
        Query query= em.createNativeQuery(sql);
        List<Object[]> list=query.getResultList();

        List<Medicine> medicineList=new ArrayList<>();
        System.out.println(list);
        for(Object resultItem:list){
            Long variable = Long.valueOf(resultItem.toString());
            Medicine medicine=new Medicine();
            medicine=medicineRepository.findById(variable).get();
            medicineList.add(medicine);
        }
        return medicineList;
    }

    @Override
    public List<Medicine> getMedicineTopSearch() {
        return medicineRepository.findTop10ByOrderBySearchAmountDesc();
    }

    @Override
    public List<Medicine> getMedicineByFarmGroup(Long id) {
        if(id!=null){
            return medicineRepository.findAllByFarmGroupId(id);
        }
        return null;
    }

    @Override
    public Medicine saveMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    @Override
    public List<Image> getImageList(Long id) {
        return imagesRepository.findByMedicineId(id);
    }

    @Override
    public void deleteImagesAll(Long id) {
        imagesRepository.deleteImagesById(id);
    }

    @Override
    public Image addImage(Image image) {
        return imagesRepository.save(image);
    }

}
