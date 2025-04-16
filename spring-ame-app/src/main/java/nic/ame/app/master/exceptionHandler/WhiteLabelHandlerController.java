package nic.ame.app.master.exceptionHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import nic.ame.app.master.service.LoginUserDetails;

@Controller
public class WhiteLabelHandlerController implements ErrorController {

	@Autowired
	private LoginUserDetails loginUserDetails;
	
	@RequestMapping("/error")
	public String handleError(HttpServletRequest request,Model model) {
	    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

	    if (status != null) {
	        Integer statusCode = Integer.valueOf(status.toString());
	    
	        if(statusCode == HttpStatus.NOT_FOUND.value()) {
	        	model.addAttribute("errorCode","400");
	        	model.addAttribute("errorTag","Page Not Found");
	        	model.addAttribute("errorMessage","The page you are looking for might have been removed, had its name changed, or is temporarily unavailable" );

	            return "UserMenu/error";
	        }
	        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
	        	model.addAttribute("errorCode", "500");
	        	model.addAttribute("errorTag","INTERNAL_SERVER_ERROR " );
	        	model.addAttribute("errorMessage","The server encountered an internal error or misconfiguration and was unable to complete your request");

	            return "UserMenu/error";	        }
	    
	          else if(statusCode==HttpStatus.FORBIDDEN.value()) {
	        	  model.addAttribute("errorCode", "403");
	        	model.addAttribute("errorTag","Access Denied " );
	        	model.addAttribute("errorMessage","You don't have permission to access the requested resource on this server");

	        	 return "UserMenu/error";
	          }
	          else if(statusCode==HttpStatus.UNAUTHORIZED.value()) {
	        	  model.addAttribute("errorCode", "401");
        	      model.addAttribute("errorTag","Unauthorized  " );
        	       model.addAttribute("errorMessage","Authentication is required and has failed or has not yet been provided");

        	 return "UserMenu/error";
	    }
	        
	    }

	    return "UserMenu/error";
	}
	@GetMapping("dashboard-users")
	public String dashboardusers() {
		
return "redirect:/dashboard-user";

		
	}
}
