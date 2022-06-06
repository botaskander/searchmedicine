package com.searchmedicine.demo.services.impl;

import com.searchmedicine.demo.dto.ResponseDTO;
import com.searchmedicine.demo.entities.*;
import com.searchmedicine.demo.entities.email.EmailSender;
//import com.searchmedicine.demo.entities.email.EmailService;
import com.searchmedicine.demo.entities.views.*;
import com.searchmedicine.demo.repositories.*;
import com.searchmedicine.demo.services.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.searchmedicine.demo.entities.views.MessageTypes.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final FarmGroupRepository farmGroupRepository;
    private final MedicineRepository medicineRepository;
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private final CountryRepository countryRepository;
    private final PharmacyRepository pharmacyRepository;
    private final CompanyRepository companyRepository;
    private final ListReserverRepository listReserverRepository;
    private final ListWaiterRepository listWaiterRepository;
    private final PharmacyRequestRepository pharmacyRequestRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMedicineRepository userMedicineRepository;

    @Autowired(required = false)
    private EmailSender emailSender;

    @SneakyThrows
    @Override
    public List<FarmGroup> getAllFarmGroups() {
        return farmGroupRepository.findAll();
    }

    @Override
    public Response saveFarmGroup(FarmGroup farmGroup) {
        String resMessage = farmGroup.getId() == null ?
                MessageTypes.ADD_FARM_GROUP_SUCCESS_MSG : EDIT_SUCCESS_MSG;
        try {
            farmGroupRepository.save(farmGroup);
        } catch (Exception e) {
            log.error(MessageTypes.SAVE_ERROR + "фарм группы", e);
            return new Response(1, MessageTypes.SAVE_ERROR + "фарм группы: " + e.getMessage());
        }
        return Response.builder()
                .responseMessage(resMessage)
                .responseCode(0)
                .build();
    }

    @Override
    public Response deleteFarmGroup(Long id) {
        try {
            farmGroupRepository.deleteById(id);
        } catch (Exception e) {
            log.error(MessageTypes.DELETE_ERROR + "фарм группы", e);
            return new Response(1, MessageTypes.DELETE_ERROR + "фарм группы: " + e.getMessage());
        }
        return Response.builder()
                .responseMessage(MessageTypes.DELETE_FARM_GROUP_SUCCESS_MSG)
                .responseCode(0)
                .build();
    }

    @Override
    public FarmGroup getFarmGroup(Long id) {
        return farmGroupRepository.findById(id).orElse(null);
    }

    @Override
    public Medicine getMedicine(Long id) {
        if (id != null) {
            return medicineRepository.getById(id);
        }
        return null;
    }

    @Override
    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    @Override
    public ResponseDTO saveMedicine(Medicine medicine) {
        Medicine medicine1;
        String resMessage = medicine.getId() == null ? "Лекарство успешно добавлено!" : "Лекарство обновлено ";
        try {
            if (medicine.getFarmGroup() == null) {
                return new ResponseDTO(new Response(1, " Ошибка в группе: Пустая группа"), null);
            }
            val farmGroup = farmGroupRepository.getById(medicine.getFarmGroup().getId());
            medicine.setFarmGroup(farmGroup);
            if (medicine.getId() == null) {
                medicine.setAddedDate(LocalDateTime.now());
                medicine.setViewAmount(0);
                medicine.setSearchAmount(0);
            }
            medicine.setAddedDate(LocalDateTime.now());
            medicine1 = medicineRepository.save(medicine);
        } catch (Exception e) {
            log.error("Ошибка при сохранении лекарства", e);
            return new ResponseDTO(new Response(1, "Ошибка при сохранении лекарства: " + e.getMessage()), null);

        }
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setResponse(new Response(0, resMessage));
        responseDTO.setMedicine(medicine1);
        return responseDTO;
    }

    @Override
    public Response deleteMedicine(Long id) {
        try {
            medicineRepository.deleteById(id);
        } catch (Exception e) {
            log.error(MessageTypes.DELETE_ERROR + "лекарства", e);
            return new Response(1, MessageTypes.DELETE_ERROR + "лекарства: " + e.getMessage());
        }
        return Response.builder()
                .responseMessage(MessageTypes.DELETE_MEDICINE_SUCCESS_MSG)
                .responseCode(0)
                .build();
    }

    @Override
    public Country getCountry(Long id) {
        return countryRepository.getById(id);
    }

    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Response saveCountry(Country country) {
        String resMessage = country.getId() == null ?
                MessageTypes.ADD_COUNTRY_SUCCESS_MSG : EDIT_SUCCESS_MSG;
        try {
            countryRepository.save(country);
        } catch (Exception e) {
            log.error(MessageTypes.SAVE_ERROR + "страны", e);
            return new Response(1, MessageTypes.SAVE_ERROR + "страны: " + e.getMessage());
        }
        return new Response(0, resMessage);
    }

    @Override
    public Response deleteCountry(Long id) {
        try {
            countryRepository.deleteById(id);
        } catch (Exception e) {
            log.error(MessageTypes.DELETE_ERROR + "страны", e);
            return new Response(1, MessageTypes.DELETE_ERROR + "страны: " + e.getMessage());
        }
        return Response.builder()
                .responseMessage(MessageTypes.DELETE_COUNTRY_SUCCESS_MSG)
                .responseCode(0)
                .build();
    }

    @SneakyThrows
    @Override
    public Company getCompany(Long id) {
        return companyRepository.getById(id);
    }

    @SneakyThrows
    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Response saveCompany(Company company) {
        String resMessage = company.getId() == null ?
                MessageTypes.ADD_COMPANY_SUCCESS_MSG : EDIT_SUCCESS_MSG;
        try {
            if (company.getCountry() == null) {
                return new Response(1, " Ошибка : Пустая страна");
            }
            val country = countryRepository.getById(company.getCountry().getId());
            company.setCountry(country);
            companyRepository.save(company);
        } catch (Exception e) {
            log.error(MessageTypes.SAVE_ERROR + "компании", e);
            return new Response(1, MessageTypes.SAVE_ERROR + "компании: " + e.getMessage());
        }
        return new Response(0, resMessage);
    }

    @Override
    public Response deleteCompany(Long id) {
        try {
            companyRepository.deleteById(id);
        } catch (Exception e) {
            log.error(MessageTypes.DELETE_ERROR + "компании", e);
            return new Response(1, MessageTypes.DELETE_ERROR + "компании: " + e.getMessage());
        }
        return Response.builder()
                .responseMessage(MessageTypes.DELETE_COMPANY_SUCCESS_MSG)
                .responseCode(0)
                .build();
    }

    @Override
    public ListReserver getListReserver(Long id) {
        return listReserverRepository.getById(id);
    }

    @Override
    public List<ListReserver> getAllListReservers() {
        return listReserverRepository.findAll();
    }

    @Override
    public Response saveListReserver(ListReserver listReserver) {
        return null;
    }

    @Override
    public Response deleteListReserver(Long id) {
        return null;
    }

    @Override
    public ListWaiter getListWaiter(Long id) {
        return listWaiterRepository.getById(id);
    }

    @Override
    public List<ListWaiter> getAllListWaiters() {
        return listWaiterRepository.findAll();
    }

    @Override
    public Response saveListWaiter(ListWaiter listWaiter) {
        return null;
    }

    @Override
    public Response deleteListWaiter(Long id) {
        return null;
    }

    @SneakyThrows
    @Override
    public Users getUser(Long id) {
        return usersRepository.getById(id);
    }

    @SneakyThrows
    @Override
    public List<Users> getAllUsers(String roleCode) {

        if (StringUtils.hasText(roleCode)) {
            val role = rolesRepository.findByRole(roleCode).get();
            return usersRepository.findAll().stream().filter(user ->
                    user.getRoles().contains(role) == true
            ).collect(Collectors.toList());
        }

        return usersRepository.findAll();
    }

    @SneakyThrows
    @Override
    public AdminHomeInfo getAdminHomeUserInfo() {

        val users = usersRepository.findAllByOrderByRegisterDateDesc();
        val lastMonthUsers = users.stream()
                .filter(user -> user.getRegisterDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        val pharmacies = pharmacyRepository.findAllByOrderByLastUpdateDateDesc();

        val medicines = medicineRepository.findAllByOrderByViewAmountAscSearchAmountAsc();
        List<ChartLine> chartLineList = new ArrayList<>();
        for (int i = 0; i < medicines.size(); i++) {
            if (i == 10) {
                break;
            }
            chartLineList.add(ChartLine.builder()
                    .medicineName(medicines.get(i).getName() + "(" + medicines.get(i).getCompany().getName() + ")")
                    .searchAmount(medicines.get(i).getSearchAmount())
                    .viewAmount(medicines.get(i).getViewAmount())
                    .build());
        }
        val lastMonthMedicines = medicines.stream()
                .filter(medicine -> medicine.getAddedDate().isAfter(LocalDateTime.now().minusMonths(1)))
                .collect(Collectors.toList());

        val exchanges = userMedicineRepository.findAll();

        LocalDateTime ldt = LocalDateTime.now().minusMonths(1);
        Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
        val lastMonthExchanges= exchanges.stream()
                .filter(exchange-> exchange.getAddedDate().after(out))
                .collect(Collectors.toList());

        return AdminHomeInfo.builder()
                .chartLineList(chartLineList)
                .lastMonthUsers(lastMonthUsers.size())
                .totalUsers(users.size())
                .lastMonthExchanges(lastMonthExchanges.size())
                .totalExchanges(exchanges.size())
                .lastMonthMedicines(lastMonthMedicines.size())
                .totalMedicines(medicines.size())
                .lastRegisteredUsers(users.size() > 3 ? users.subList(0, 3) : users)
                .lastUpdatedPharmacies(pharmacies.size() > 4 ? pharmacies.subList(0, 4) : pharmacies)
                .build();
    }

    @Override
    public Response saveUser(Users user) {
        return null;
    }

    @Override
    public Response deleteUsers(Long id) {
        return null;
    }

    @Override
    public Roles getRole(Long id) {
        return rolesRepository.getById(id);
    }

    @Override
    public List<Roles> getAllRoles(String roleCode) {

        return rolesRepository.findAll();
    }

    @Override
    public Response saveRole(Roles role) {
        return null;
    }

    @Override
    public Response deleteRoles(Long id) {
        return null;
    }

    @SneakyThrows
    @Override
    public List<RegisterRequests> getAllPharmaciesRequests() {
        return pharmacyRequestRepository.getAllByOrderByRegisterDateDescIsSeenAsc();
    }

    @Override
    public Response acceptPharmacyRequest(AcceptPharmacyRequest request) {
        String errorMSG = ACCEPT_REQUEST_ERROR_MSG;
        int resCode = 0;
        val pharmacyRequest = pharmacyRequestRepository.getById(request.getRequestId());
        try {
            List<Roles> roles = new ArrayList<>();
            val role = rolesRepository.findByRole("ROLE_PHARMACY").orElse(new Roles());
            roles.add(role);
            if (pharmacyRequest != null) {
                usersRepository.save(Users.builder()
                        .email(pharmacyRequest.getEmail())
                        .fullName(pharmacyRequest.getFullName())
                        .isArc(false)
                        .enabled(true)
                        .password(passwordEncoder.encode(pharmacyRequest.getStaticPassword()))
                        .registerDate(LocalDateTime.now())
                        .roles(roles)
                        .build());
            }
        } catch (Exception e) {
            errorMSG += "Ошибка при регистрации аптеки в систему; ";
            log.error(errorMSG, e);
            resCode = 1;
            return new Response(resCode, resCode == 0 ? ACCEPT_REQUEST_SUCCESS_MSG : errorMSG);
        }

        try {
            val user = usersRepository.findByEmail(pharmacyRequest.getEmail());
            pharmacyRepository.save(Pharmacy.builder()
                    .address(pharmacyRequest.getAddress())
                    .isWork(true)
                    .lastUpdateDate(LocalDateTime.now())
                    .name(pharmacyRequest.getName())
                    .offPhone(pharmacyRequest.getPhoneNumber())
                    .phoneNumber(pharmacyRequest.getPhoneNumber())
                    .whatsappNumber(pharmacyRequest.getWhatsappNumber())
                    .user(user)
                    .workEndTime(pharmacyRequest.getWorkEndTime())
                    .workStartTime(pharmacyRequest.getWorkStartTime())
                    .build());
        } catch (Exception e) {
            errorMSG += " Ошибка при добавлении аптеки в базу; ";
            log.error(errorMSG, e);
            resCode = 1;
            return new Response(resCode, resCode == 0 ? ACCEPT_REQUEST_SUCCESS_MSG : errorMSG);

        }

        try {
            pharmacyRequest.setIsDone(true);
            pharmacyRequest.setIsSeen(true);
            pharmacyRequestRepository.save(pharmacyRequest);
            emailSender.send(pharmacyRequest.getEmail(),
                    buildEmailBody(pharmacyRequest.getEmail(), pharmacyRequest.getStaticPassword()),
                    "Регистрация в SEARCHMEDICINE");

        } catch (Exception e) {
            resCode = 1;
            log.error(errorMSG + " e", e);
            return new Response(resCode, resCode == 0 ? ACCEPT_REQUEST_SUCCESS_MSG : errorMSG);
        }
        return new Response(resCode, resCode == 0 ? ACCEPT_REQUEST_SUCCESS_MSG : errorMSG);


    }

    private String buildEmailBody(String email, String password) {
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
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Уведомление о регистрации в SEARCHMEDICINE</span>\n" +
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
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Здравствуйте! Вы успешно зарегистрировались в нашей системе.</p>" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"><b>Ваши учетные данные для входа (пароль можно менять после авторизации): </b>  логин -" + email + ", пароль: " + password + "</p>" +
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

    @Override
    public Response changeSeenStatusOfPharmacyRequest(Long requestId){
        val request = pharmacyRequestRepository.getById(requestId);
        System.out.println(request);
        if(request!=null){
            request.setIsSeen(true);
            pharmacyRequestRepository.save(request);
            return new Response(0,EDIT_SUCCESS_MSG);
        }
        return new Response(1,SAVE_ERROR);
    }
}

