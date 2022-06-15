package com.esign.service.configuration.controller;

import com.esign.service.configuration.dto.pass.PasswordPolicyDto;
import com.esign.service.configuration.dto.pass.PasswordValidateDto;
import com.esign.service.configuration.entity.pass.PassEnforcementPolicyEntity;
import com.esign.service.configuration.entity.pass.PassPasswordHistoryEntity;
import com.esign.service.configuration.repository.pass.PasswordEnforcePolicyRepository;
import com.esign.service.configuration.repository.pass.PasswordHistoryRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/password-policy")
@Api(tags = "Password Policy")
@Slf4j
@CrossOrigin
public class PasswordPolicyController {

  @Autowired PasswordEnforcePolicyRepository passwordEnforcePolicyRepository;

  @Autowired PasswordHistoryRepository passwordHistoryRepository;

  @Autowired CacheManager cacheManager;

  @GetMapping(value = "/predefined")
  @ApiOperation(value = "Predefined Password policy with regular expression")
  @Cacheable("predefined")
  public Map<String, String> predefined() {
    Map<String, String> stringMap = new HashMap<>();
    List<PassEnforcementPolicyEntity> policyEntities =
        passwordEnforcePolicyRepository
            .findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_StatusAndPassPolicyByPolicyId_IsPredefined(
                1, "A", "A", "Y");
    String rule = "", regex = "";
    for (PassEnforcementPolicyEntity policyEntity : policyEntities) {
      // Minimum Uppercase Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 1) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(.*[A-Z]){" + policyEntity.getPolicyValue() + "})";
      }

      // Minimum Lowercase Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 2) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(.*[a-z]){" + policyEntity.getPolicyValue() + "})";
      }

      // Minimum Alphabetic Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 3) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(.*[a-zA-Z]){" + policyEntity.getPolicyValue() + "})";
      }

      // Minimum Numeric Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 4) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(.*[0-9]){" + policyEntity.getPolicyValue() + "})";
      }

      // Minimum Special Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 6) {
        rule +=
            String.format(
                    policyEntity.getPassPolicyByPolicyId().getPolicyShowText(),
                    policyEntity.getPolicyValue())
                + " !@#$% ||";
        regex += "(?=(.*[!@#$%]){" + policyEntity.getPolicyValue() + "})";
      }

      // Minimum Password Length
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 10) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(^(.){" + policyEntity.getPolicyValue() + ",}$))";
      }

      // Maximum Password Length
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 11) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(^(.){0," + policyEntity.getPolicyValue() + "}$))";
      }

      // Characters Required
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 15) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=(.*[" + policyEntity.getPolicyValue() + "]))";
      }

      // Characters Not Allowed
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 16) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        regex += "(?=.*^[^" + policyEntity.getPolicyValue() + "]*$)";
      }

      // Characters Allowed
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 17) {
        rule +=
            String.format(
                policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                policyEntity.getPolicyValue());
        String specialCharacters = policyEntity.getPolicyValue().replace("a-zA-Z0-9", "");
        regex.replace("!@#$%", specialCharacters);
        regex += "(?=.*[" + policyEntity.getPolicyValue() + "]$)";
      }

      // Alphabetic Character Must Start Password
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 18) {
        if ("Y".equalsIgnoreCase(policyEntity.getPolicyValue())) {
          rule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          regex = "^(?=[a-zA-Z])" + regex;
        }
      }
    }

    stringMap.put("REGEX", regex);
    stringMap.put(
        "RULE",
        new StringBuilder(rule)
            .replace(rule.lastIndexOf("||"), rule.lastIndexOf("||") + 2, "")
            .toString());
    return stringMap;
  }

  @PostMapping(value = "/validate")
  @ApiOperation(value = "Validate Password before save")
  public PasswordValidateDto validate(@RequestBody PasswordValidateDto passwordValidateDto) {
    List<PassEnforcementPolicyEntity> policyEntities =
        passwordEnforcePolicyRepository
            .findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_Status(
                1, "A", "A");
    String status = "PASS", breakrule = "", newpassword = passwordValidateDto.getPassword();
    for (PassEnforcementPolicyEntity policyEntity : policyEntities) {
      // Minimum Uppercase Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 1) {
        int counter = 0;
        for (int i = 0; i < newpassword.length(); i++) {
          if (Character.isUpperCase(newpassword.charAt(i))) counter++;
        }
        if (counter < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Minimum Lowercase Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 2) {
        int counter = 0;
        for (int i = 0; i < newpassword.length(); i++) {
          if (Character.isLowerCase(newpassword.charAt(i))) counter++;
        }
        if (counter < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Minimum Alphabetic Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 3) {
        int counter = 0;
        for (int i = 0; i < newpassword.length(); i++) {
          if (Character.isAlphabetic(newpassword.charAt(i))) counter++;
        }
        if (counter < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Minimum Numeric Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 4) {
        int counter = 0;
        for (int i = 0; i < newpassword.length(); i++) {
          if (isNumeric(String.valueOf(newpassword.charAt(i)))) counter++;
        }
        if (counter < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Minimum Special Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 6) {
        String[] items = new String[] {"!", "@", "#", "$", "%"};
        if (!Arrays.stream(items).parallel().anyMatch(newpassword::contains)) {
          breakrule +=
              String.format(
                      policyEntity.getPassPolicyByPolicyId().getPolicyShowText(),
                      policyEntity.getPolicyValue())
                  + " !@#$% ||";
          status = "FAIL";
        }
      }
      // Minimum Password Length
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 10) {
        if (newpassword.length() < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Maximum Password Length
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 11) {
        if (newpassword.length() > Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Minimum Unique Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 12) {
        if (newpassword.chars().distinct().count()
            < Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Maximum Repeated Characters
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 13) {
        if (maxRepeating(newpassword) > Integer.valueOf(policyEntity.getPolicyValue())) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Characters Required
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 15) {
        String[] array =
            Stream.of(policyEntity.getPolicyValue().toCharArray()).toArray(String[]::new);
        if (!Arrays.stream(array).parallel().anyMatch(newpassword::contains)) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Characters Not Allowed
      if (isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 16) {
        String[] array =
            Stream.of(policyEntity.getPolicyValue().toCharArray()).toArray(String[]::new);
        if (!Arrays.stream(array).parallel().anyMatch(newpassword::contains)) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Characters Allowed
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 17) {
        if (!newpassword.matches("(.*[" + policyEntity.getPolicyValue() + "])")) {
          breakrule +=
              String.format(
                  policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                  policyEntity.getPolicyValue());
          status = "FAIL";
        }
      }
      // Alphabetic Character Must Start Password
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 18) {
        if ("Y".equalsIgnoreCase(policyEntity.getPolicyValue())) {
          if (!Character.isAlphabetic(newpassword.charAt(0))) {
            breakrule +=
                String.format(
                    policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                    policyEntity.getPolicyValue());
            status = "FAIL";
          }
        }
      }
      // Can Include User's Last Name
      // Can Include User's First Name
      // Can Include User ID
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 21) {
        if ("Y".equalsIgnoreCase(policyEntity.getPolicyValue())) {
          if (passwordValidateDto.getUsername() != null) {
            if (newpassword.contains(passwordValidateDto.getUsername())) {
              if (!Character.isAlphabetic(newpassword.charAt(0))) {
                breakrule +=
                    String.format(
                        policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                        policyEntity.getPolicyValue());
                status = "FAIL";
              }
            }
          } else {
            breakrule +=
                String.format(
                    policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                    policyEntity.getPolicyValue());
            status = "FAIL";
          }
        }
      }
      // Disallow Previous Passwords
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 24) {
        List<PassPasswordHistoryEntity> entityList =
            passwordHistoryRepository.getAllByUserIdAndStatusOrderByCreatedDateDesc(
                passwordValidateDto.getUserid(), "A");
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        if (entityList != null && entityList.size() > 0) {
          for (int i = 0; i < Integer.valueOf(policyEntity.getPolicyValue()); i++) {
            int size = i + 1;
            PassPasswordHistoryEntity historyEntity = null;
            if (entityList.size() >= size) historyEntity = entityList.get(i);
            if (historyEntity != null
                && bCryptPasswordEncoder.matches(
                    passwordValidateDto.getPassword(), historyEntity.getPasswordHistory())) {
              breakrule +=
                  String.format(
                      policyEntity.getPassPolicyByPolicyId().getPolicyShowText() + "||",
                      policyEntity.getPolicyValue());
              status = "FAIL";
            }
          }
        }
      }
    }

    if (breakrule.contains("||"))
      passwordValidateDto.setDetail(
          new StringBuilder(breakrule)
              .replace(breakrule.lastIndexOf("||"), breakrule.lastIndexOf("||") + 2, "")
              .toString());
    passwordValidateDto.setStatus(status);
    policyEntities = null;
    return passwordValidateDto;
  }

  @GetMapping(value = "/enforce/{userid}")
  @ApiOperation(value = "Get password enforce to time for change or not(Warning,Expired,FirstTime)")
  @Cacheable("enforceWithUser")
  public Map<String, String> enforce(@PathVariable int userid) {
    Map<String, String> stringMap = new HashMap<>();
    List<PassEnforcementPolicyEntity> policyEntities =
        passwordEnforcePolicyRepository
            .findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_Status(
                1, "A", "A");
    int age = 0, warn = 0, expire = 0;
    for (PassEnforcementPolicyEntity policyEntity : policyEntities) {
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 14) {
        age = Integer.valueOf(policyEntity.getPolicyValue());
      }
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 22) {
        warn = Integer.valueOf(policyEntity.getPolicyValue());
      }
      if (!isEmptyString(policyEntity.getPolicyValue())
          && policyEntity.getPassPolicyByPolicyId().getPolicyId() == 23) {
        expire = Integer.valueOf(policyEntity.getPolicyValue());
      }
    }
    // Minimum Password Age (days)
    // Warn After (days)
    // Expire After (days)
    if (age > 0) {
      List<PassPasswordHistoryEntity> entityList =
          passwordHistoryRepository.getAllByUserIdAndStatusOrderByCreatedDateDesc(userid, "A");
      if (entityList != null && entityList.size() > 0) {
        PassPasswordHistoryEntity historyEntity = entityList.get(0);
        LocalDateTime lastChangeDate =
            LocalDateTime.ofInstant(
                historyEntity.getCreatedDate().toInstant(), ZoneId.of(ZoneId.SHORT_IDS.get("VST")));
        LocalDateTime today = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("VST")));
        LocalDateTime lastChangeDateWithWarn = lastChangeDate.plusDays(age + warn);
        LocalDateTime lastChangeDateWithExpire = lastChangeDate.plusDays(age + expire);
        if (today.isAfter(lastChangeDateWithWarn)) {
          stringMap.put("Status", "Warning");
          stringMap.put(
              "ExpireDate",
              "" + lastChangeDateWithExpire.format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        }
        if (today.isAfter(lastChangeDateWithExpire)) {
          stringMap.put("Status", "Expired");
          stringMap.put(
              "ExpireDate",
              "" + lastChangeDateWithExpire.format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
        }
      } else {
        stringMap.put("Status", "FirstTime");
        stringMap.put(
            "ExpireDate",
            ""
                + LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("VST")))
                    .format(DateTimeFormatter.ofPattern("dd/MM/YYYY")));
      }
    }

    policyEntities = null;
    return stringMap;
  }

  @PostMapping(value = "/confirm-change")
  @ApiOperation(value = "Save change password history")
  public PasswordValidateDto confirmChangePassword(
      @RequestBody PasswordValidateDto passwordValidateDto) {
    PassPasswordHistoryEntity historyEntity = new PassPasswordHistoryEntity();
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
    historyEntity.setUserId(passwordValidateDto.getUserid());
    historyEntity.setPasswordHistory(
        bCryptPasswordEncoder.encode(passwordValidateDto.getPassword()));
    historyEntity.setStatus("A");
    historyEntity.setCreatedDate(new Date());
    PassPasswordHistoryEntity save = passwordHistoryRepository.save(historyEntity);
    if (save.getPasswordHistoryId() > 0) {
      passwordValidateDto.setStatus("Success");
    }
    return passwordValidateDto;
  }

  @GetMapping(value = "/policy/{enforceid}")
  @ApiOperation(value = "Get Policy")
  public List<PasswordPolicyDto> passwordPolicy(@PathVariable int enforceid) {
    List<PasswordPolicyDto> passwordPolicyDtos = new ArrayList<>();
    List<PassEnforcementPolicyEntity> enforcement =
        passwordEnforcePolicyRepository
            .findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_Status(
                enforceid, "A", "A");

    for (PassEnforcementPolicyEntity passEnforcementPolicyEntity : enforcement) {
      PasswordPolicyDto passwordPolicy = new PasswordPolicyDto();
      passwordPolicy.setPolicyId(passEnforcementPolicyEntity.getEpId());
      passwordPolicy.setPolicyName(
          passEnforcementPolicyEntity.getPassPolicyByPolicyId().getPolicyName());
      passwordPolicy.setPolicyValue(passEnforcementPolicyEntity.getPolicyValue());
      passwordPolicy.setPolicyValueType(
          passEnforcementPolicyEntity.getPassPolicyByPolicyId().getPolicyValueType());
      passwordPolicyDtos.add(passwordPolicy);
    }
    return passwordPolicyDtos;
  }

  @PutMapping(value = "/policy/{enforceid}")
  @ApiOperation(value = "Update Policy")
  public List<PasswordPolicyDto> passwordPolicy(
      @PathVariable int enforceid, @RequestBody List<PasswordPolicyDto> passwordPolicyDtos) {

    List<PassEnforcementPolicyEntity> enforcement =
        passwordEnforcePolicyRepository
            .findByPassEnforcementByEnforcementId_EnforceIdAndStatusAndPassPolicyByPolicyId_Status(
                enforceid, "A", "A");

    for (PassEnforcementPolicyEntity passEnforcementPolicyEntity : enforcement) {
      passwordPolicyDtos.forEach(
          x -> {
            if (x.getPolicyId() == passEnforcementPolicyEntity.getEpId()) {
              passEnforcementPolicyEntity.setPolicyValue(x.getPolicyValue());
              passEnforcementPolicyEntity.setUpdatedDate(new Date());
            }
          });
    }
    passwordEnforcePolicyRepository.saveAll(enforcement);
    cacheManager.getCacheNames().stream()
        .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    return passwordPolicyDtos;
  }

  private boolean isEmptyString(String str) {
    return str == null || "".equals(str.trim()) || "0".equals(str.trim());
  }

  private int maxRepeating(String str1) {
    final int chars[] = new int[256];

    int max = 0;
    for (final char c : str1.toCharArray()) {
      chars[c]++;
      if (chars[c] > chars[max]) // find most repetitive symbol in word
      max = c;
    }
    return chars[max];
  }

  private boolean isNumeric(String s) {
    return s != null && s.matches("[-+]?\\d*\\.?\\d+");
  }
}
