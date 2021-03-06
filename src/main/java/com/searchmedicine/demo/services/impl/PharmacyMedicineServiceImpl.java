package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.MedicineDto;
import com.searchmedicine.demo.dto.PharmacyMedicineDto;
import com.searchmedicine.demo.entities.ListReserver;
import com.searchmedicine.demo.entities.ListWaiter;
import com.searchmedicine.demo.entities.Medicine;
import com.searchmedicine.demo.entities.Pharmacy;
import com.searchmedicine.demo.entities.PharmacyMedicine;
import com.searchmedicine.demo.entities.UserMedicine;
import com.searchmedicine.demo.entities.Users;
import com.searchmedicine.demo.entities.email.EmailSender;
import com.searchmedicine.demo.repositories.ListWaiterRepository;
import com.searchmedicine.demo.repositories.MedicineRepository;
import com.searchmedicine.demo.repositories.PharmacyMedicineRepository;
import com.searchmedicine.demo.repositories.UserMedicineRepository;
import com.searchmedicine.demo.services.PharmacyMedicineService;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PharmacyMedicineServiceImpl implements PharmacyMedicineService {

  private final PharmacyMedicineRepository pharmacyMedicineRepository;
  private final UserMedicineRepository userMedicineRepository;
  private final ListWaiterServiceImpl listWaiterServiceImpl;
  private final MedicineRepository medicineRepository;
  private final EntityManager entityManager;
  @Autowired(required = false)
  private EmailSender emailSender;
  private  final ListWaiterRepository listWaiterRepository;

  @Override
  public List<MedicineDto> getAllPharmacyUserMedicine(Long id, String type, Boolean isAsc, Users users) {
    Medicine medicine = medicineRepository.findById(id).orElse(null);
    List<MedicineDto> medicineDtoList = new ArrayList<>();
    boolean length = false;
    if(medicine != null) {
      String sortBy = "ph.price";
      if (isAsc == null || isAsc) {
        sortBy += " asc";
      } else {
        sortBy += " desc";
      }
      List<PharmacyMedicine> pharmacyMedicines = entityManager.createQuery(
              "SELECT ph FROM PharmacyMedicine ph "
                      + " WHERE ph.medicine.id =" + id
                      + " ORDER BY " + sortBy)
              .getResultList();
      if (pharmacyMedicines.size() > 0) length = true;
      if ("??????".equals(type)|| "all".equals(type)|| "????????????".equals(type) ) {
        for (PharmacyMedicine ph : pharmacyMedicines) {
          MedicineDto medicineDTO = new MedicineDto();
          medicineDTO.setId(ph.getId());
          medicineDTO.setMedicine(ph.getMedicine());
          medicineDTO.setAddress(ph.getPharmacy().getAddress());
          medicineDTO.setOwner(ph.getPharmacy().getName());
          medicineDTO.setPrice(ph.getPrice());
          medicineDTO.setType("pharmacy");
          medicineDtoList.add(medicineDTO);
        }
      }
      List<UserMedicine> userMedicines = userMedicineRepository.findAllByMedicine_Id(id);
      if (userMedicines.size() > 0) length = true;
      if ( "??????".equals(type)|| "all".equals(type)||  "????????????????????????".equals(type) ) {
        for (UserMedicine u : userMedicines) {
          MedicineDto medicineDTO = new MedicineDto();
          medicineDTO.setType("user");
          medicineDTO.setId(u.getId());
          medicineDTO.setMedicine(u.getMedicine());
          medicineDTO.setAddress(u.getAddress());
          medicineDTO.setOwner(u.getUser().getFullName());
          medicineDTO.setPrice(0.0);
          medicineDtoList.add(medicineDTO);
        }
      }
      MedicineDto medicineDTO = new MedicineDto();
      medicineDTO.setEmptyMed(length);
      if(!length && users != null){
        List<ListWaiter> listWaiters = listWaiterServiceImpl.getWaiterByUserIdAndMedicineId(users.getId(),medicine.getId());
        if(listWaiters.size() >0) medicineDTO.setWaiter(true);
      }

      medicineDtoList.add(medicineDTO);

      int amount = medicine.getSearchAmount();
      amount +=1;
      medicine.setSearchAmount(amount);
      medicineRepository.save(medicine);
    }

    return medicineDtoList;
  }

  public void sendNotification(PharmacyMedicine pharmacyMedicine){
    List<ListWaiter> listWaiters = listWaiterServiceImpl.getNotification(pharmacyMedicine.getMedicine().getId());
    String setSubject = "Notification about medicine";
    System.out.println("*******************************************");
    System.out.println("Send notification");

    if(listWaiters!=null){
      for(ListWaiter lw: listWaiters) {
        lw.setIsAppear(true);
        emailSender.send(lw.getUsers().getEmail(),
                buildEmail(lw.getUsers().getFullName(), pharmacyMedicine.getMedicine(), pharmacyMedicine.getPharmacy()),
                setSubject);
        listWaiterRepository.save(lw);
      }
    }
    //emailSender.send(getUser().getEmail(),buildEmail(getUser().getFullName(), pharmacyMedicine.getMedicine(), pharmacyMedicine.getPharmacy() ), setSubject);
  }

  public PharmacyMedicineDto getPharmacyMedicine(Long id){
    PharmacyMedicineDto pharmacyMedicineDto = new PharmacyMedicineDto();
    PharmacyMedicine pharmacyMedicine = pharmacyMedicineRepository.findById(id).orElse(null);

    pharmacyMedicineDto.setPharmacyName(pharmacyMedicine.getPharmacy().getName());
    pharmacyMedicineDto.setAddress(pharmacyMedicine.getPharmacy().getAddress().getName() );
    pharmacyMedicineDto.setWorkStartTime(pharmacyMedicine.getPharmacy().getWorkStartTime());
    pharmacyMedicineDto.setWorkEndTime(pharmacyMedicine.getPharmacy().getWorkEndTime());
    pharmacyMedicineDto.setWhatsappNumber(pharmacyMedicine.getPharmacy().getWhatsappNumber());
    pharmacyMedicineDto.setOffPhone(pharmacyMedicine.getPharmacy().getOffPhone());
    pharmacyMedicineDto.setOffPhone(pharmacyMedicine.getPharmacy().getPhoneNumber());
    pharmacyMedicineDto.setMedicineName(pharmacyMedicine.getMedicine().getName());
    pharmacyMedicineDto.setCount(pharmacyMedicine.getCount());
    pharmacyMedicineDto.setPrice(pharmacyMedicine.getPrice());
    pharmacyMedicineDto.setInstructions(pharmacyMedicine.getMedicine().getInstructions());
    pharmacyMedicineDto.setIndications(pharmacyMedicine.getMedicine().getIndications());
    pharmacyMedicineDto.setCompanyName(pharmacyMedicine.getMedicine().getCompany().getName());
    pharmacyMedicineDto.setDescription(pharmacyMedicine.getMedicine().getDescription());
    pharmacyMedicineDto.setUrl(pharmacyMedicine.getMedicine().getUrl());

    Medicine medicine = medicineRepository.findById(pharmacyMedicine.getMedicine().getId()).orElse(null);
    int amount = medicine.getViewAmount();
    amount +=1;
    medicine.setViewAmount(amount);
    medicineRepository.save(medicine);

    return pharmacyMedicineDto;
  }

  @Override
  public PharmacyMedicine getPharmacyMedicineById(Long id) {
    return pharmacyMedicineRepository.findById(id).orElse(null);
  }

  private String buildEmail(String name, Medicine medicine, Pharmacy pharmacy){
    return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
        "\n" +
        "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
        "\n" +
        "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
        "        \n" +
        "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
        "          <tbody><tr>\n" +
        "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
        "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td style=\"padding-left:10px\">\n" +
        "                  \n" +
        "                    </td>\n" +
        "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
        "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">?????????????????????? ?? ?????????????????????? ??????????????????</span>\n" +
        "                    </td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "              </a>\n" +
        "            </td>\n" +
        "          </tr>\n" +
        "        </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
        "      <td>\n" +
        "        \n" +
        "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
        "                  <tbody><tr>\n" +
        "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
        "                  </tr>\n" +
        "                </tbody></table>\n" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
        "    </tr>\n" +
        "  </tbody></table>\n" +
        "\n" +
        "\n" +
        "\n" +
        "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
        "    <tbody><tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
        "        \n" +
        "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> ?????????????? ???? ???????????????? ??????????????????. ???? ???????????????? ?????? , <b>"+ medicine.getName() +" </b> ?????????????????? ?????????????????? ??  ???????????? <b> "+pharmacy.getName()+"</b>  </p>\n <p>??????????: "+pharmacy.getAddress().getName()+
        " \n ?????????? ????????????????: " + pharmacy.getPhoneNumber() +
        "\n Whatsapp ??????????: " + pharmacy.getWhatsappNumber() +
        "\n ?????????? ????????????: "+ pharmacy.getWorkStartTime() + " - " + pharmacy.getWorkEndTime() +
        "</p> \n<p>???? ??????????????</p>" +
        "        \n" +
        "      </td>\n" +
        "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
        "    </tr>\n" +
        "    <tr>\n" +
        "      <td height=\"30\"><br></td>\n" +
        "    </tr>\n" +
        "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
        "\n" +
        "</div></div>";
  }
}
