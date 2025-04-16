package nic.ame.app.user.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.servlet.support.RequestContextUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import nic.ame.app.admin.model.ContactType;
import nic.ame.app.admin.model.ContactUs;
import nic.ame.app.admin.model.Rank;

import nic.ame.app.admin.repository.ContactTypeRepository;
import nic.ame.app.admin.repository.ContactUsRepo;
import nic.ame.app.admin.repository.ForceRepo;
import nic.ame.app.admin.repository.RankMasterRepository;
import nic.ame.app.admin.repository.RankRepo;
import nic.ame.app.master.dto.ForcePersonnelDto;
import nic.ame.app.master.dto.PersonnelOthersDto;
import nic.ame.app.master.dto.UserRoleDto;
import nic.ame.app.master.model.Force;
import nic.ame.app.master.model.IsdMaster;
import nic.ame.app.master.model.StdMaster;
import nic.ame.app.master.repository.IsdMasterRepository;
import nic.ame.app.master.repository.StdMasterRepository;
import nic.ame.app.master.service.ForcePersonalService;
import nic.ame.app.master.service.LoginUserDetails;
import nic.ame.app.master.service.UserRoleService;
import nic.ame.app.user.service.ContactService;
import nic.ame.constant.MessageResponse;

@Controller
public class ContactUsController {

	@Autowired
	private UserRoleService userRoleService;

	@Autowired
	private LoginUserDetails loginUserDetails;

	@Autowired
	private ContactService contactService;

	@Autowired
	private IsdMasterRepository isdMasterRepository;

	@Autowired
	private StdMasterRepository stdMasterRepository;

	@Autowired
	private ContactUsRepo contactUsRepo;

	@Autowired
	private ContactTypeRepository contactTypeRepository;

	@Autowired
	private RankRepo rankRepo;

	@Autowired
	private ContactTypeRepository contacRepository;

	@Autowired
	private ForceRepo forceRepo;

	@GetMapping("add-contact-us")
	public String createContact(Model model, HttpServletRequest httpServletRequest) {
		HttpSession httpSession = httpServletRequest.getSession();

		String message = null;
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(httpServletRequest);
		if (inputFlashMap != null) {
			message = (String) inputFlashMap.get("message");

		}
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
//		List<UserRoleDto> userRoleDtos = userRoleService.getListOfUserWithRoles(loginForcePersonalId);
//		model.addAttribute("userRoleDtosList", userRoleDtos);
		int rCode = (int) httpSession.getAttribute("rCode");
		List<Force> departmentList = forceRepo.findAll();
		model.addAttribute("departmentList", departmentList);
		List<StdMaster> stdMasters = this.stdMasterRepository.findAll();
		model.addAttribute("stdList", stdMasters);
		List<IsdMaster> isdMasters = this.isdMasterRepository.findAll();
		model.addAttribute("isdList", isdMasters);
		List<ContactType> contactTypes = this.contactTypeRepository.findAll();
		model.addAttribute("contactTypes", contactTypes);
		String loginForceName = (String) httpSession.getAttribute("loginForceName");
		String userType = (String) httpSession.getAttribute("userType");
		model.addAttribute("loginForceName", loginForceName);
		model.addAttribute("userType", userType);
		model.addAttribute("message", message);

		return "user-management-template/force-admin-user/contact-us";
	}

	@RequestMapping(value = "/getDesignations", method = RequestMethod.POST)
	ResponseEntity<?> createRankDropDownByDepartment(@RequestParam("forceId") Integer forceId) {

		List<Rank> rankList = rankRepo.findByForceId(forceId);
		Map<String, Object> map = new HashMap<>();
		map.put("rankList", rankList);
		return ResponseEntity.status(HttpStatus.OK).body(map);

	}

	@PostMapping("get-force-id")
	public ResponseEntity<?> getforceId(@RequestBody Map<String, String> data) {
		Integer forceNo = Integer.parseInt(data.get("department"));
		Integer designation = Integer.parseInt(data.get("designation"));

		Map<String, Object> response = new HashMap<>();
		Map<String, String> errors = new HashMap<>();

		if (!forceNo.equals("99") && !forceNo.equals("8")) {
			List<ForcePersonnelDto> forcePersonnelList = contactService
					.getForcePersonnelListByForceNoAndDesignation(forceNo, designation);
			response.put("forcePersonnelList", forcePersonnelList);
			response.put("isValid", true);
			return ResponseEntity.ok(response);
		}

		else {

			List<PersonnelOthersDto> personnelOthersList = contactService
					.getPersonalOthersByForceNoAndDesignation(forceNo, designation);

			response.put("personnelOthersList", personnelOthersList);
			return ResponseEntity.ok(response);
		}

	}

	@RequestMapping(value = "/save-contact-us", method = RequestMethod.POST)
	public ResponseEntity<?> saveContactUs(@RequestBody ContactUs contactUs) {
		this.contactService.saveContact(contactUs);
		return ResponseEntity.ok().body(new MessageResponse("Form submitted successfully!"));
	}

	@RequestMapping(value = "/edit-contact-us", method = RequestMethod.GET)
	public String showContactUsPage(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		List lists = new ArrayList<>();
		List<ContactUs> contacts = this.contactUsRepo.findAll();

		for (ContactUs contact : contacts) {
			Force force = forceRepo.findByForceNo(Integer.parseInt(contact.getDepartment()));
			Rank rank = rankRepo.findById(Integer.parseInt(contact.getDesignation())).get();
			ContactType contactTye = contactTypeRepository.findById(Long.parseLong(contact.getContactType())).get();
			contact.setDepartment(force.getForceName());
			contact.setDesignation(rank.getRankFullName());
			contact.setContactType(contactTye.getValue());
			contact.setName(contact.getName());
			contact.setEmail(contact.getEmail());
			contact.setId(contact.getId());
			contact.setMobileNo(contact.getMobileNo());
			lists.add(contact);

		}
		model.addAttribute("list", lists);
		return "user-management-template/force-admin-user/edit-contact-us";
	}

	@GetMapping("/edit-contact-us-form")
	public String showEditForm(@RequestParam("id") long id, Model model, HttpSession httpSession) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		ContactUs contactUs = contactService.showEditForm(id);
		model.addAttribute("contactUs", contactUs);
		return "user-management-template/force-admin-user/edit-contact-form-page";
	}

	@RequestMapping(value = "/update-contact-us", method = RequestMethod.POST)
	public ResponseEntity<?> editContactUs(@ModelAttribute ContactUs contactUs, HttpSession httpSession, Model model) {
		String loginForcePersonalId = (String) httpSession.getAttribute("forcepersonalId");
		model.addAttribute("loginUserDetails", loginUserDetails.getLoginUserDetails(loginForcePersonalId));
		this.contactService.editContact(contactUs);
		return ResponseEntity.ok().body(new MessageResponse("Form Update successfully!"));
	}

}
