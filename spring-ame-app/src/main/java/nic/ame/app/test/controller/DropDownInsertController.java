package nic.ame.app.test.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import nic.ame.app.master.ref.entity.RefHearingDropDown;
import nic.ame.app.master.ref.entity.repo.RefHearingDropDownRepository;

@RestController
@RequestMapping("/drop-down")
public class DropDownInsertController {
	
	private RefHearingDropDownRepository refHearingDropDownRepository;
	
	public DropDownInsertController(RefHearingDropDownRepository refHearingDropDownRepository) {
		super();
		this.refHearingDropDownRepository = refHearingDropDownRepository;
	}




	@PostMapping("/hearing")
	public ResponseEntity<?> saveHearing(RefHearingDropDown hearingDropDown){
		refHearingDropDownRepository.save(hearingDropDown);
		return ResponseEntity.status(HttpStatus.CREATED).body(hearingDropDown);
	}
	
}
