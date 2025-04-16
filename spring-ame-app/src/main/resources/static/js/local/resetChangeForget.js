/*function refreshCaptcha() {
			
			
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
            $.ajax({
                url: 'refresh-captcha',
                
                beforeSend: function (xhr) {

						xhr.setRequestHeader(header, token);
					},
                type: 'GET',
                success: function(data) {
                    if (data === 'success') {
                        // Refresh the captcha image by updating the src attribute
                         var d = new Date();
                          $("#captcha-img").attr("src", "/captcha?" + d.getTime());
                       // document.getElementById('captcha-img').src = '/captcha?' + new Date().getTime();
                    } else {
                        console.error('Error refreshing captcha');
                    }
                },
                error: function(xhr, status, error) {
                    console.error('Error refreshing captcha:', error);
                }
            });
        }
        
        */
        
        function refreshCaptcha() {
    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: 'refresh-captcha', // Adjust this URL based on your server-side endpoint
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        type: 'GET',
        success: function(data) {
            if (data === 'success') {
                // Refresh the captcha image by updating the src attribute
                var d = new Date();
                $("#captcha-img").attr("src", "/captcha?" + d.getTime());
                // Alternatively, you can use vanilla JavaScript:
                // document.getElementById('captcha-img').src = '/captcha?' + new Date().getTime();
            } else {
                console.error('Error refreshing captcha: Unexpected response');
            }
        },
        error: function(xhr, status, error) {
            console.error('Error refreshing captcha:', error);
        }
    });
}

        
        
        function changePasswordEncryptionKey() {
			
			var token = $("meta[name='_csrf']").attr("content");
		  var header = $("meta[name='_csrf_header']").attr("content");
				
$.ajax({
    url: 'change-password-encryption-key',
    type: 'POST',
    contentType: 'application/json',
 beforeSend: function (xhr) {

						xhr.setRequestHeader(header, token);
					},
    success: function(response) {
		document.getElementById("hexKey").value=response.hexKey;
		document.getElementById("ivHex").value=response.ivHex;
		document.getElementById("loginForcePersonalId").value=response.loginForcePersonalId;
        // Handle success response here
        console.log('Response:', response);
    },
    error: function(xhr, status, error) {
        // Handle error response here
        console.error('Error:', error);
    }
});
}
//============================================================

$(document).ready(
		function() {
			$('#regForm-reset-password')
			.submit(
				function(event) {
					event.preventDefault();
					$('#loading-overlay').show();
					$('body').addClass('loading');
					var loginForcePersonalId = $("#loginForcePersonalId").val();
					var oldPassword = $("#old-password").val();
					var newPassword = $("#new-password").val();
					var confirmPassword = $("#confirm-password").val();
					validatePassword();
					    oldPassword=encryptedValue(oldPassword);
						newPassword=encryptedValue(newPassword);
					    confirmPassword=encryptedValue(confirmPassword);
					
                        var data = {
							loginForcePersonalId : loginForcePersonalId,
							oldPassword : oldPassword,
							newPassword : newPassword,
							confirmPassword : confirmPassword
							}
							/* ===================header==================== */
								var token = $("meta[name='_csrf']").attr("content");
							    var header = $("meta[name='_csrf_header']").attr("content");
									$('.error').text('');$.ajax({
										type : 'POST',
										url : 'validate-and-change-password',
										data : JSON.stringify(data),
										contentType : 'application/json',
										beforeSend : function(xhr) {
											xhr.setRequestHeader(header,token);
											},
											success : function(response) {
												if (response.isValid) {
													alert("Password Change Successfully, Please Login Again!")
												
														document.getElementById("sign-out-form").submit();		}

															else {$.each(response.errors,
															function(field,errorMessage) 
															{$('#'+ field+ '_error').text(errorMessage);
															});
																				}

																			},
																			complete : function() {
																				$(
																						'#loading-overlay')
																						.hide();
																				$(
																						'body')
																						.removeClass(
																								'loading');
																			},
																			error : function(
																					xhr,
																					status,
																					error) {

																				alert("Please fill the form again somthing went WRONG!");
																				console
																						.error(error);
																			}
																		});
															});
										});


										function validatePassword() {
											const oldpassword = document.getElementById("old-password").value;
											const password = document.getElementById("new-password").value;
											const confirmPassword = document.getElementById("confirm-password").value;
								
											// Criteria for password validation
											const minLength = 8;
											const hasUpperCase = /[A-Z]/.test(password);
											const hasLowerCase = /[a-z]/.test(password);
											const hasNumber = /[0-9]/.test(password);
											const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);
											const passwordsMatch = password === confirmPassword;
								
											// Update the hints
											updateHint("lengthHint", password.length >= minLength);
											updateHint("upperCaseHint", hasUpperCase);
											updateHint("lowerCaseHint", hasLowerCase);
											updateHint("numberHint", hasNumber);
											updateHint("specialCharHint", hasSpecialChar);
											updateHint("matchHint", passwordsMatch);
										}
										function updateHint(hintId, isValid) {
											const hintElement = document.getElementById(hintId);
											if (isValid) {
												hintElement.classList.remove("invalid");
												hintElement.classList.add("valid");
											} else {
												hintElement.classList.remove("valid");
												hintElement.classList.add("invalid");
											}
										}
										function togglePasswordVisibility(passwordFieldId) {
											const passwordField = document.getElementById(passwordFieldId);
											if (passwordField.type === "password") {
												passwordField.type = "text";
											} else {
												passwordField.type = "password";
											}
										}
function fetchAndShowProfile() {
    // Fetch user details from the server
    fetch('api-user-details')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            // Populate the modal with fetched data
            document.getElementById('profileName').textContent = data.name || 'N/A';
            document.getElementById('profileDesignation').textContent = data.designation || 'N/A';
            document.getElementById('profileEmail').textContent = data.emailId || 'N/A';
            document.getElementById('profileMobile').textContent = data.mobileNumber || 'N/A';
            document.getElementById('profileGender').textContent = data.gender || 'N/A';
			document.getElementById('profileMaritalStatus').textContent = data.maritalStatus || 'N/A';
            document.getElementById('attachUnit').textContent = data.attachUnitName || 'N/A';
			document.getElementById('unit').textContent = data.unitName || 'N/A';
			document.getElementById('forceId').textContent = data.forceId || 'N/A';
			document.getElementById('joiningDate').textContent = formatDate(data.joiningDate);
            document.getElementById('dob').textContent = formatDate(data.dob);

			
        })
        .catch(error => console.error('Error fetching profile:', error));
}

function formatDate(dateString) {
    if (!dateString) return 'N/A';
    
    // Create a Date object from the string
    const date = new Date(dateString);
    
    // Extract the year, month, and day
    const year = date.getUTCFullYear().toString().slice(0); // Last two digits of the year
    const month = String(date.getUTCMonth() + 1).padStart(2, '0'); // Months are zero-based
    const day = String(date.getUTCDate()).padStart(2, '0');

    // Format as yy-mm-dd
    return `${day}-${month}-${year}`;
}


     

