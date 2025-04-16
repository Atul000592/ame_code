package nic.ame.app.esign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nic.ame.app.master.model.go.repository.AmeFinalReportFileDirGoRepository;

@Controller
public class ControllerReadEsignPdf {
	
	@Autowired
	private AmeFinalReportFileDirGoRepository  ameFinalReportFileDirGoRepository;
	
	
	@PostMapping("/read-esigned-pdf-by-bm")
	public String readEsignedPdfByBm(@RequestParam("ameId") String ameId,@RequestParam("forcepersonalId") String forcepersonalId,Model model) {
		String filePath=ameFinalReportFileDirGoRepository.findByAmeId(ameId).get(0).getFilePath();
		
		model.addAttribute("filePath", filePath);
		model.addAttribute("ameId",ameId);
		model.addAttribute("forcepersonalId",forcepersonalId);

		return "po-template/view/read-esigned-pdf-by-po";
	}

}
