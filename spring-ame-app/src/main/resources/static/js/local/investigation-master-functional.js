// =========================================(RK- save Lipid)========================================================== 


document.addEventListener("DOMContentLoaded", function() {
			setDefaultValuesCBC();
});

$(document).ready(
		function() {
			$('#save-lipid')
				.click(function(event) {
					event.preventDefault();
					
 
						
						var hashMap = new Map();

						var ameId = $("#ameId-lipid").val();
						var serumCcholesterol = $("#serumCcholesterol").val();
						var lDLCholesterol = $("#lDLCholesterol").val();
						var hDLCholesterol = $("#hDLCholesterol").val();
						var vLDLcholesterol = $("#vLDLcholesterol").val();
						var triglycerides = $("#triglycerides").val();
						var lDLhDLRatio = $("#lDLhDLRatio").val();
						var testCode = $("#testCode-lipid").val()
						var fileInfo = $("#fileInput-lipid")[0].files[0];
						if (fileInfo == null) {
							alert("Please select file To upload.......!");
							
								return false;
						}
						var formData = new FormData();
						formData.append('file',
							fileInfo);
						hashMap.set("ameId", ameId);
						hashMap.set("serumCcholesterol",serumCcholesterol);
						hashMap.set("lDLCholesterol",lDLCholesterol);
						hashMap.set("hDLCholesterol",hDLCholesterol);
						hashMap.set("vLDLcholesterol",vLDLcholesterol);
						hashMap.set("triglycerides",triglycerides);
						hashMap.set("lDLhDLRatio",lDLhDLRatio);
						hashMap.set("testCode",testCode);

						var jsonArray = [];
						hashMap.forEach(function(
							value, key) {
							var obj = {};
							obj[key] = value;
							jsonArray.push(obj);
						});

						// Convert the array to a JSON string
						var jsonData = JSON
							.stringify(jsonArray);
						formData.append("jsonData",
							jsonData);

						/* ===================header==================== */
						var token = $("meta[name='_csrf']").attr("content");
						var header = $("meta[name='_csrf_header']").attr("content");
						$('.error').text('');
                        $('#loading-overlay').show();
						$('body').addClass('loading');
						let isRequestCompleted=false;
						if(isRequestCompleted)
							return;
						else{
						
							$.ajax({
															type: 'POST',
															url: 'save-lipid',
															data: formData,
															data: formData,
															contentType: false,
															processData: false,
															beforeSend: function(
																xhr) {
																xhr
																	.setRequestHeader(
																		header,
																		token);
															},
															
															
															success: function(response) {
																if (response.isValid) {
																	alert("Data Saved Succcessfully!");
																	investigationlipidCloseModel();
	
							isRequestCompleted=true;
							$('#loading-overlay').hide();

															$('body').removeClass('loading');
															window.location.reload();
							}	else {
								$.each(response.errors,function(field,errorMessage) {
									$('#'+ field
									+ '_error')
									.text(errorMessage);
									});}
							},
							complete: function() {
								$('#loading-overlay').hide();
								$('body').removeClass('loading');
								},
								error: function(xhr,
								status,
								error) {
									alert("Please fill the form again somthing went WRONG!");
									console
							.error(error);
															}
														});	
							
							
							
						}
					});
		});
		
		
		
		
		
		
		
		// =========================================(RK-save CBC) ============================================================= 


$(document)
	.ready(
		function() {
			$('#save-cbc')
				.click(
					function(event) {
						event.preventDefault();


						var hashMap = new Map();

						var ameId = $("#ameId-cbc").val();
						
						var haemoglobin = $("#haemoglobin").val();
						
						var totalLeukocyteCount = $("#totalLeukocyteCount").val();
						
						var differentialLeukocyteCount = $("#differentialLeukocyteCount").val();
						
						var neutrophilsOrPolymorphs = $("#neutrophilsOrPolymorphs").val();
						
						var monocytes = $("#monocytes").val();
						
						var lymphocytesBTCells = $("#lymphocyte").val();
						
						var basophils = $("#basophils").val();
						
						var eosinophils = $("#eosinophils").val();
						
						var erythrocyteSedimentationRate = $("#erythrocyteSedimentationRate").val();
						
						var plateletCount = $("#plateletCount").val();
						
						var fileInfo = $("#fileInput-cbc")[0].files[0];
						var testCode = $("#testCode-cbc").val();

						if (fileInfo == null) {
							alert("Please select file To upload.......!");
							return false;
						}
						var formData = new FormData();
						formData.append('file', fileInfo);
						hashMap.set("ameId", ameId);
						hashMap.set("haemoglobin", haemoglobin);
						hashMap.set("totalLeukocyteCount", totalLeukocyteCount);
						hashMap.set("differentialLeukocyteCount", differentialLeukocyteCount);
						hashMap.set("neutrophilsOrPolymorphs", neutrophilsOrPolymorphs);
						hashMap.set("monocytes", monocytes);
						hashMap.set("lymphocytesBTCells", lymphocytesBTCells);
						hashMap.set("basophils", basophils);
						hashMap.set("eosinophils", eosinophils);
						hashMap.set("erythrocyteSedimentationRate", erythrocyteSedimentationRate);
						hashMap.set("plateletCount", plateletCount);
						hashMap.set("testCode", testCode);
						var jsonArray = [];
						hashMap.forEach(function(value, key) {
							var obj = {};
							obj[key] = value;
							jsonArray.push(obj);
						});

						// Convert the array to a JSON string
						var jsonData = JSON
							.stringify(jsonArray);
						formData.append("jsonData",
							jsonData);

						/* ===================header==================== */
						var token = $(
							"meta[name='_csrf']")
							.attr("content");
						var header = $(
							"meta[name='_csrf_header']")
							.attr("content");
						$('.error').text('');
						$('#loading-overlay').show();
						$('body').addClass('loading');
						
						let isRequestCompleted=false;
						if(isRequestCompleted)
							return;
						else{							$.ajax({
													type: 'POST',
													url: 'save-cbc',
													data: formData,
													data: formData,
													contentType: false,
													processData: false,
													beforeSend: function(
														xhr) {
														xhr
															.setRequestHeader(
																header,
																token);
													},
													success: function(
														response) {
														if (response.isValid) {
															alert("Data Saved Succcessfully!");
															investigationcbcCloseModel();
						                                		isRequestCompleted=true;
															$('#loading-overlay').hide();
															$('body').removeClass('loading');
															window.location.reload();

														} else {

															$.each(response.errors,
																function(field, errorMessage) {
																	$('#' + field + '_error').text(errorMessage);
																}
															);
														}

													},
													complete: function() {
														$('#loading-overlay').hide();
														$('body').removeClass('loading');
													},
													error: function(xhr, status, error) {

														alert("Please fill the form again somthing went WRONG!");
														console
															.error(error);
													}
												});
											}
					
					});
		});
		
		
		
		function setDefaultValuesCBC(){
				
					/*	var haemoglobin = $("#haemoglobin").val();
						
						var totalLeukocyteCount = $("#totalLeukocyteCount").val();
						
						var differentialLeukocyteCount = $("#differentialLeukocyteCount").val();
					*/	
						var neutrophilsOrPolymorphs = $("#neutrophilsOrPolymorphs").val();
						
						var monocytes = $("#monocytes").val();
						
						var lymphocytesBTCells = $("#lymphocyte").val();
						
						var basophils = $("#basophils").val();
						
						var eosinophils = $("#eosinophils").val();
						
						var erythrocyteSedimentationRate = $("#erythrocyteSedimentationRate").val();
						
						var plateletCount = $("#plateletCount").val();
						
			if (!neutrophilsOrPolymorphs) {
				$("#neutrophilsOrPolymorphs").val('50');

			}

			if (!monocytes) {
				$("#monocytes").val('5');

			}

			if (!lymphocytesBTCells) {
				$("#lymphocyte").val('40');

			}

			if (!basophils) {
				$("#basophils").val('5');

			}
			if (!eosinophils) {
				$("#eosinophils").val('5');

			}
			if (!erythrocyteSedimentationRate) {
				$("#erythrocyteSedimentationRate").val('7.00');

			}
			if (!plateletCount) {
				$("#plateletCount").val('300');

			}

						
						
						
						
						

			
		}



//============================================================================================================================================
$(document)
	.ready(
		function() {
			$('#save-lft')
				.click(
					function(event) {
						event.preventDefault();



						var hashMap = new Map();
						var formData = new FormData();

						var ameId = $("#lft-ameId")
							.val();
						var bilirubin = $(
							"#bilirubin").val();
						var directBilirubin = $(
							"#directBilirubin")
							.val();
						var indirectBilirubin = $("#indirectBilirubin").val();
						var sGOT = $("#sGOT").val();
						var sGPT = $("#sGPT").val();
						var aLP = $("#aLP").val();
						var totalProtein = $("#totalProtein").val();
						var ggpt = $("#ggpt").val();
						var albumin = $("#albumin").val();
						var globulin = $("#globulin").val();
						var AGRatio = $("#AGRatio").val();
						var testCode = $(
							"#lft-testCode")
							.val();
						var fileinfo = $("#fileInput-lft")[0].files[0];

						if (fileinfo == null) {
							alert("Please select file To upload.......!");
							return false;
						}

						formData.append('file', fileinfo);
						hashMap.set("ameId", ameId);
						hashMap.set("bilirubin", bilirubin);
						hashMap.set("directBilirubin", directBilirubin);
						hashMap.set("indirectBilirubin", indirectBilirubin);
						hashMap.set("sGOT", sGOT);
						hashMap.set("sGPT", sGPT);
						hashMap.set("aLP", aLP);
						hashMap.set("totalProtein", totalProtein);
						hashMap.set("ggpt", ggpt);
						hashMap.set("albumin", albumin);
						hashMap.set("globulin", globulin);
						hashMap.set("AGRatio", AGRatio);
						hashMap.set("testCode",
							testCode);

						var jsonArray = [];
						hashMap.forEach(function(
							value, key) {
							var obj = {};
							obj[key] = value;
							jsonArray.push(obj);
						});

						// Convert the array to a JSON string
						var jsonData = JSON
							.stringify(jsonArray);
						formData.append("jsonData",
							jsonData);

						/* ===================header==================== */
						var token = $(
							"meta[name='_csrf']")
							.attr("content");
						var header = $(
							"meta[name='_csrf_header']")
							.attr("content");
						$('.error').text('');

		$('#loading-overlay').show();
		$('body').addClass('loading');
		
		let isRequestCompleted=false;
								if(isRequestCompleted)
									return;
								else{										$.ajax({
											type: 'POST',
											url: 'save-lft',
											data: formData,
											data: formData,
											contentType: false,
											processData: false,
											beforeSend: function(xhr) {
												xhr
													.setRequestHeader(
														header, token);
											},

											success: function(
												response) {
												if (response.isValid) {
													alert("Data Saved Succcessfully!");
													investigationlftCloseModel();
							
													/*var tbodyCompletedInvestigationList = $('#completedInvestigationTable tbody');
													var tbodyInvestigationList = $("#investigationList tbody")
													var rowToDelete = document
														.getElementById("lft");

													var newRow = $('<tr>');

													newRow.append($('<td>').text("LIVER FUNCTION TEST"));
													newRow.append($('<td>').text("LIVER FUNCTION TEST"));
													newRow.append($('<td>').text("Completed"));

													// Append the new row to the table's tbody
													tbodyCompletedInvestigationList
														.append(newRow);
													if (rowToDelete) {
														rowToDelete.parentNode
															.removeChild(rowToDelete);
														//tbodyInvestigationList.removeChild(rowToDelete);

													}*/
													isRequestCompleted=true;
												
													$('#loading-overlay').hide();
													$('body').removeClass('loading');
													window.location.reload();
													
												}

												else {

													$.each(response.errors,
														function(
															field,
															errorMessage) {
															$('#' + field + '_error').text(errorMessage);
														});
												}
											},
											complete: function() {
												$('#loading-overlay').hide();
												$('body').removeClass('loading');
											},
											error: function(xhr, status, error) {

												alert("Please fill the form again somthing went WRONG!");
												console.error(error);
											}
										});}
		
		

											});
						});

							
							
//======================================================RK(save KFT)======================================================= 

$(document).ready(function() {
	$('#save-utmicro').click(function(event) {
		event.preventDefault();
		
	
		
		
		var hashMap = new Map();
		var formData = new FormData();
		 
		var ameId=$("#ameId-utmicro").val();
		var epithelialCells=$("#epithelialCells").val();
		var rbc=$("#rbc").val();
		var crystal=$("#crystal").val();
		var colour=$("#colour").val();
		var transparency=$("#transparency").val();
		var reaction=$("#reaction").val();
		var albuminCells=$("#albuminCells").val();
		var sugar=$("#sugar").val();
		var bileSalt=$("#bileSalt").val();
		var ketones=$("#ketones").val();
		var specificGravity=$("#specificGravity").val();
		var pusCells=$("#pusCells").val();
		var upt=$("#upt").val();
		var gender=$("#gender").val();
		
		if(gender!="Female"){
			upt="NA";
		}

		
		var testCode=$("#testCode-utmicro").val();
		var fileInfo=$("#fileInput-utmicro")[0].files[0];
		
/*==========NEW TEST FIELDS========*/
		
		var castCells=$("#castCells").val();

		
	    
	    if(fileInfo==null){
			alert("Please select file To upload.......!");
			return false;
				}
	   
	    formData.append('file',fileInfo);
		hashMap.set("ameId",ameId);
		
		
		hashMap.set("epithelialCells",epithelialCells);
		hashMap.set("rbc",rbc);
		hashMap.set("crystal",crystal);
		hashMap.set("colour",colour);
		hashMap.set("transparency",transparency);
		hashMap.set("reaction",reaction);
		hashMap.set("albuminCells",albuminCells);
		hashMap.set("sugar",sugar);
		hashMap.set("bileSalt",bileSalt);
		hashMap.set("ketones",ketones);
		hashMap.set("specificGravity",specificGravity);
		hashMap.set("pusCells",pusCells);
        hashMap.set("upt",upt);
        hashMap.set("castCells",castCells);
       

          hashMap.set("testCode",testCode);
        
        
        /*=======NEW TEST FIELDS=======  */
        
    
        
        
		
		
		var jsonArray = [];
	    hashMap.forEach(function (value, key) {
	        var obj = {};
	        obj[key] = value;
	        jsonArray.push(obj);
	    });

	    // Convert the array to a JSON string
	    var jsonData = JSON.stringify(jsonArray);
	    formData.append("jsonData", jsonData);

/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');


		$('#loading-overlay').show();
		$('body').addClass('loading');

		let isRequestCompleted = false;
		if (isRequestCompleted)
			return;
		else {										$.ajax({
											type: 'POST',
											url: 'save-utmicrophy',
											data:formData,
											 data: formData,
										        contentType: false,
										        processData: false,
											beforeSend: function(xhr) {
												xhr.setRequestHeader(header, token);
											},
											success: function(response) {
												if (response.isValid) {
												alert("Data Saved Succcessfully!");
												investigationutmicrophyCloseModel();
												
												
													isRequestCompleted=true;
													$('#loading-overlay').hide();
													$('body').removeClass('loading');
													window.location.reload();
													 }

												else {  
												 
													$.each(response.errors, function(field, errorMessage) {
														if(field.localeCompare("fileError")===0){
															alert(errorMessage);
								                            investigationutmicrophyCloseModel();

														}
														else{
														
														 $('#' + field + '_error').text(errorMessage);
														 }
														 });
														 
													}
												
								},
								complete: function() {
								    $('#loading-overlay').hide();
								    $('body').removeClass('loading');
								},
											error: function(xhr, status, error) {

												alert("Please fill the form again somthing went WRONG!");
												
												console.error(error);
											}
										});}

	});
});


//========================================================================Viral Maker========================================-->

	$(document).ready(function() {
		$('#save-vrl').click(function(event) {
			event.preventDefault();
			
		
			
			var hashMap = new Map();
			 var formData = new FormData();
			 
			var ameId=$("#vrl-ameId").val();
			var hiv1=$("#hiv1").val();
			var hiv2=$("#hiv2").val();
			var hbsAg=$("#hbsAg").val();
			var vdrl=$("#vdrl").val();
			var hcv=$("#hcv").val();
			var testCode=$("#vrl-testCode").val();
			var fileinfo=$("#fileInput-vrl")[0].files[0];
			
			if(fileinfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		   
			formData.append('file',fileinfo);
			hashMap.set("ameId",ameId);
			hashMap.set("hiv1",hiv1);
			hashMap.set("hiv2",hiv2);
			hashMap.set("hbsAg",hbsAg);
			hashMap.set("vdrl",vdrl);
			hashMap.set("hcv",hcv);
			hashMap.set("testCode",testCode);
			
			
			var jsonArray = [];
			hashMap.forEach(function (value, key) {
				var obj = {};
				obj[key] = value;
				jsonArray.push(obj);
			});
	
			// Convert the array to a JSON string
			var jsonData = JSON.stringify(jsonArray);
			formData.append("jsonData", jsonData);
	
	/* ===================header==================== */
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$('.error').text('');
	
	
		$('#loading-overlay').show();
		$('body').addClass('loading');
	
			let isRequestCompleted = false;
			if (isRequestCompleted)
				return;
			else {	
													$.ajax({
														type: 'POST',
														url: 'save-vrl',
														data:formData,
														 data: formData,
															contentType: false,
															processData: false,
														beforeSend: function(xhr) {
															xhr.setRequestHeader(header, token);
														},
														success: function(response) {
															if (response.isValid) {
															alert("Data Saved Succcessfully!");
															investigationvrlCloseModel();
														
														
																isRequestCompleted=true;
																$('#loading-overlay').hide();
																$('body').removeClass('loading');
																window.location.reload();
																 }

															else {  
															 
																$.each(response.errors, function(field, errorMessage) {
																	 $('#' + field + '_error').text(errorMessage);
																	 });
																}
															
											},
											complete: function() {
												$('#loading-overlay').hide();
												$('body').removeClass('loading');
											},
														error: function(xhr, status, error) {

															alert("Please fill the form again somthing went WRONG!");
															console.error(error);
														}
													});
											
										}

		});
	});
	
	
	
	//===========================================================================tp===============
	
	$(document).ready(function() {
		$('#save-tp').click(function(event) {
			event.preventDefault();
			
		
			var hashMap = new Map();
			 var formData = new FormData();
			 
			var ameId=$("#tp-ameId").val();
			var t3=$("#t3").val();
			var t4=$("#t4").val();
			var tsh=$("#tsh").val();
			var testCode=$("#tp-testCode").val();
			var fileinfo=$("#fileInput-tp")[0].files[0];
			
			if(fileinfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		   
			formData.append('file',fileinfo);
			hashMap.set("ameId",ameId);
			hashMap.set("t3",t3);
			hashMap.set("t4",t4);
			hashMap.set("tsh",tsh);
			hashMap.set("testCode",testCode);

 
			var jsonArray = [];
			hashMap.forEach(function (value, key) {
				var obj = {};
				obj[key] = value;
				jsonArray.push(obj);
			});
	
			// Convert the array to a JSON string
			var jsonData = JSON.stringify(jsonArray);
			formData.append("jsonData", jsonData);
	/* ===================header==================== */
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$('.error').text('');

			
		$('#loading-overlay').show();
		$('body').addClass('loading');
		
	let isRequestCompleted=false;
			if (isRequestCompleted)
				return;
			else {				$.ajax({
					type: 'POST',
					url: 'save-tp',
					data:formData,
					 data: formData,
						contentType: false,
						processData: false,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response.isValid) {
						alert("Data Saved Succcessfully!");
					investigationtpCloseModel();
					
						
							isRequestCompleted=true;
							$('#loading-overlay').hide();
							$('body').removeClass('loading');	
							window.location.reload();
							
							 }

						else {  
						 
							$.each(response.errors, function(field, errorMessage) {
								 $('#' + field + '_error').text(errorMessage);
								 });
							}
						
		},
		complete: function() {
			$('#loading-overlay').hide();
			$('body').removeClass('loading');
		},
					error: function(xhr, status, error) {

						alert("Please fill the form again somthing went WRONG!");
						console.error(error);
					}
				});	}
		

		});
	});
	
	
	
//======================================================Others Test======================================================= 

	function saveOther(e) {
			event.preventDefault();
			
		
			
			var codeName = e.name;
			
			var hashMap = new Map();
			var formData = new FormData();
			var ameId=$("#"+codeName+"-ameId").val();
			//var id=$("#"ecg-id").val();
			var testName=$("#"+codeName+"-testName").val();
			var testCode=$("#"+codeName+"-testCode").val();
			var fileinfo=$("#fileInput-"+codeName)[0].files[0];
			
			if(fileinfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		   
			formData.append('file',fileinfo);
			hashMap.set("ameId",ameId);
			//hashMap.set("id",id);
			hashMap.set("testName",testName);
			hashMap.set("testCode",codeName);

 
			var jsonArray = [];
			hashMap.forEach(function (value, key) {
				var obj = {};
				obj[key] = value;
				jsonArray.push(obj);
			});
	
			// Convert the array to a JSON string
			var jsonData = JSON.stringify(jsonArray);
			formData.append("jsonData", jsonData);
	/* ===================header==================== */
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");
			$('.error').text('');
			let isRequestCompleted=false;
		if (isRequestCompleted)
			return;
		else {				
					$.ajax({
						type: 'POST',
						url: 'save-other',
						data:formData,
						 data: formData,
							contentType: false,
							processData: false,
						beforeSend: function(xhr) {
							xhr.setRequestHeader(header, token);
						},
						success: function(response) {
							if (response.isValid) {
							alert("Data Saved Succcessfully!");
							if(codeName=='xray')
							{
								investigationxrayCloseModel()
							}
							if(codeName=='ecg'){
								investigationecgCloseModel()
								
							}
							if(codeName=='usg'){
								investigationusgCloseModel()
							}
							isRequestCompleted=true;
							$('#loading-overlay').hide();
							$('body').removeClass('loading');
							window.location.reload();
								
								
								 }

							else {  
							 
								$.each(response.errors, function(field, errorMessage) {
									 $('#' + field + '_error').text(errorMessage);
									 });
								}
							
			},
			complete: function() {
				$('#loading-overlay').hide();
				$('body').removeClass('loading');
				investigation+codeName+CloseModel();
	
			},
						error: function(xhr, status, error) {

							alert("Please fill the form again somthing went WRONG!");
							console.error(error);
						}
					});}

	}
	
	
	
	
		// =====================================================RK(save KFT)======================================================= 

		
			$(document)
					.ready(
							function() {
								$('#save-kft')
										.click(
												function(event) {
													event.preventDefault();


													var hashMap = new Map();
													var formData = new FormData();

													var ameId = $("#ameId-kft").val();
													var bloodUrea = $("#bloodUrea").val();
													var scrumCreatinine = $("#scrumCreatinine").val();
													var uricAcid = $("#uricAcid").val();
													var testCode = $("#testCode-kft").val();
													var fileInfo = $("#fileInput-kft")[0].files[0];

													if (fileInfo == null) {
														alert("Please select file To upload.......!");
														return false;
													}

													formData.append('file',fileInfo);
													hashMap.set("ameId", ameId);
													hashMap.set("bloodUrea",bloodUrea);
													hashMap.set("scrumCreatinine",scrumCreatinine);
													hashMap.set("uricAcid",uricAcid);
													hashMap.set("testCode",testCode);

													var jsonArray = [];
													hashMap.forEach(function(
															value, key) {
														var obj = {};
														obj[key] = value;
														jsonArray.push(obj);
													});

													// Convert the array to a JSON string
													var jsonData = JSON
															.stringify(jsonArray);
													formData.append("jsonData",
															jsonData);

													/* ===================header==================== */
													var token = $(
															"meta[name='_csrf']")
															.attr("content");
													var header = $(
															"meta[name='_csrf_header']")
															.attr("content");
													$('.error').text('');



		                                        $('#loading-overlay').show();
												$('body').addClass('loading');
												let isRequestCompleted=false;
												if (isRequestCompleted)
													return;
												else {				$.ajax({
					type: 'POST',
					url: 'save-kft',
					data:formData,
					 data: formData,
				        contentType: false,
				        processData: false,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response.isValid) {
						alert("Data Saved Succcessfully!");
						investigationkftCloseModel();
					
						isRequestCompleted=true;
						    $('#loading-overlay').hide();
							$('body').removeClass('loading');
							window.location.reload();
							 }

						else {  
						 
							$.each(response.errors, function(field, errorMessage) {
								 $('#' + field + '_error').text(errorMessage);
								 });
							}
						
		},
		complete: function() {
		    $('#loading-overlay').hide();
		    $('body').removeClass('loading');
		},
					error: function(xhr, status, error) {

						alert("Please fill the form again somthing went WRONG!");
						console.error(error);
					}
				});	}
	
	});
});



//==========================================================================================

$(document).ready(function() {
	$('#save-other-test').click(function(event) {
		event.preventDefault();
		
		
        var hashMap = new Map();
		var ameId=$("#ameId-other-test").val();
		var testName=$("#other-test-name").val().trim();
		var testCode=$("#other-test-code").val();
		var otherTestName=$("#otherTestNameId").val();
		var otherTestValue=$("#otherTestValueId").val();

		var fileInfo=$("#fileInput-other-test")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				  $('#loading-overlay').hide();
				    $('body').removeClass('loading');
				return false;
					}
		 var formData = new FormData();
		 formData.append('file',fileInfo);
		 hashMap.set("ameId",ameId);
		 hashMap.set("testName",testName);
		 hashMap.set("testCode",testCode);
		 hashMap.set("otherTestValue",otherTestValue);
		 hashMap.set("otherTestName",otherTestName);
			
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData); 
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');

		if (isRequestCompleted)
			return;
		else {				$.ajax({
					type: 'POST',
					url: 'validate-and-save-other-test',
					data:formData,
					 data: formData,
				        contentType: false,
				        processData: false,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response.isValid) {
							alert("Data Saved Succcessfully!");
							
							investigationOtherTestCloseModel();
							window.location.reload();
							
							
							/*var tbodyCompletedInvestigationList = $('#completedInvestigationTable tbody');
							var tbodyInvestigationList=$("#investigationList tbody")
							var rowToDelete=document.getElementById("utmicro");
							
							var newRow = $('<tr>');
							  
							newRow.append($('<td>').text(testName));
							newRow.append($('<td>').text(testName));
						    newRow.append($('<td>').text("Completed"));
							    
						    
						    tbodyCompletedInvestigationList.append(newRow);
						    if(rowToDelete){
							   rowToDelete.parentNode.removeChild(rowToDelete);
						    	//tbodyInvestigationList.removeChild(rowToDelete);
						    	}$('#loading-overlay').hide();$('body').removeClass('loading');
							    */
							isRequestCompleted	= true; 
							$('#loading-overlay').hide();
								    $('body').removeClass('loading');
									window.location.reload();
							}

						else {  
						 
							$.each(response.errors, function(field, errorMessage) {
								 $('#' + field + '_error').text(errorMessage);
								 });
							}
						
		},
		complete: function() {
		    $('#loading-overlay').hide();
		    $('body').removeClass('loading');
		},
					error: function(xhr, status, error) {

						alert("Please fill the form again somthing went WRONG!");
						console.error(error);
					}
				});	}
		

	});
});



//=============================================================Blood Sugar======================================================================
$(document).ready(function() {
	$('#save-BloodSugar').click(function(event) {
		event.preventDefault();
		
        var hashMap = new Map();
		var ameId=$("#ameId-bs").val();
		var bloodSugarPP=$("#bspp").val();
		var bloodSugarF=$("#bsf").val();
		var bloodSugarRandom=$("#bloodSugarRandom").val();
		var HbA1c=$("#HbA1c").val();
		var testCode=$("#testCode-bs").val();
		var fileInfo=$("#fileInput-bs")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		    var formData = new FormData();
		    formData.append('file',fileInfo);
			hashMap.set("ameId",ameId);
			hashMap.set("bloodSugarPP",bloodSugarPP);
			hashMap.set("bloodSugarF",bloodSugarF);
			hashMap.set("bloodSugarRandom",bloodSugarRandom);
			hashMap.set("HbA1c",HbA1c);
			hashMap.set("testCode",testCode);
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData);
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');

		$.ajax({
			type: 'POST',
			url: 'save-blood-sugar',
			data:formData,
			 data: formData,
		        contentType: false,
		        processData: false,
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: function(response) {
				if (response.isValid) {
					alert("Data Saved Succcessfully!");
					investigationbsCloseModel();
					window.location.reload();
			$('#loading-overlay').hide();
			$('body').removeClass('loading');
					    
						 }

				else {  
				 
					$.each(response.errors, function(field, errorMessage) {
						 $('#' + field + '_error').text(errorMessage);
						 });
					}
				
},
complete: function() {
    $('#loading-overlay').hide();
    $('body').removeClass('loading');
},
			error: function(xhr, status, error) {

				alert("Please fill the form again somthing went WRONG!");
				console.error(error);
			}
		});
	});
});

/*----------------------bspp-----------------------*/

$(document).ready(function() {
	$('#save-bspp').click(function(event) {
		event.preventDefault();
		
        var hashMap = new Map();
		var ameId=$("#ameId-bspp").val();
		var bloodSugarPP=$("#bspp-val").val();
	    var testCode="bspp";
		var fileInfo=$("#fileInput-bspp")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		    var formData = new FormData();
		    formData.append('file',fileInfo);
			hashMap.set("ameId",ameId);
			hashMap.set("bloodSugarPP",bloodSugarPP);
			hashMap.set("testCode",testCode);
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData);
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');

							let isRequestCompleted=false;
							if(isRequestCompleted)
								return;
							else{									$.ajax({
										type: 'POST',
										url: 'validate-and-save-blood-sugar-pp',
										data:formData,
										 data: formData,
									        contentType: false,
									        processData: false,
										beforeSend: function(xhr) {
											xhr.setRequestHeader(header, token);
										},
										success: function(response) {
											if (response.isValid) {
												alert("Data Saved Succcessfully!");
												investigationbsppCloseModel();
											
												/*var tbodyCompletedInvestigationList = $('#completedInvestigationTable tbody');
												var tbodyInvestigationList=$("#investigationList tbody")
												var rowToDelete=document.getElementById("bspp");
												
												var newRow = $('<tr>');
												  
												newRow.append($('<td>').text("BLOOD SUGAR-PP"));
												newRow.append($('<td>').text("BLOOD SUGAR-PP"));
											    newRow.append($('<td>').text("Completed"));
												    
											    // Append the new row to the table's tbody
											    tbodyCompletedInvestigationList.append(newRow);
											if(rowToDelete){
												  rowToDelete.parentNode.removeChild(rowToDelete);
											    	//tbodyInvestigationList.removeChild(rowToDelete);
											    	 
												    }*/
													isRequestCompleted=true;
												    $('#loading-overlay').hide();
												    $('body').removeClass('loading');
													window.location.reload();
												}

											else {  
											 
												$.each(response.errors, function(field, errorMessage) {
													 $('#' + field + '_error').text(errorMessage);
													 });
												}
											
							},
							complete: function() {
							    $('#loading-overlay').hide();
							    $('body').removeClass('loading');
							},
										error: function(xhr, status, error) {

											alert("Please fill the form again somthing went WRONG!");
											console.error(error);
										}
									});	}						
									

	});
});
/*----------------------bsf-----------------------*/

$(document).ready(function() {
	$('#save-bsf').click(function(event) {
		event.preventDefault();
		
        var hashMap = new Map();
		var ameId=$("#ameId-bsf").val();
		var bloodSugarF=$("#bsf-val").val();
	    var testCode="bsf";
		var fileInfo=$("#fileInput-bsf")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		    var formData = new FormData();
		    formData.append('file',fileInfo);
			hashMap.set("ameId",ameId);
			hashMap.set("bloodSugarF",bloodSugarF);
			hashMap.set("testCode",testCode);
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData);
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');
		let isRequestCompleted=false;
									if(isRequestCompleted)
										return;
									else{											$.ajax({
												type: 'POST',
												url: 'validate-and-save-blood-sugar-f',
												data:formData,
												 data: formData,
											        contentType: false,
											        processData: false,
												beforeSend: function(xhr) {
													xhr.setRequestHeader(header, token);
												},
												success: function(response) {
													if (response.isValid) {
														alert("Data Saved Succcessfully!");
														investigationbsfCloseModel();
													
														isRequestCompleted=true;
														    $('#loading-overlay').hide();
														    $('body').removeClass('loading');
															window.location.reload();
														}

													else {  
													 
														$.each(response.errors, function(field, errorMessage) {
															 $('#' + field + '_error').text(errorMessage);
															 });
														}
													
									},
									complete: function() {
									    $('#loading-overlay').hide();
									    $('body').removeClass('loading');
									},
												error: function(xhr, status, error) {

													alert("Please fill the form again somthing went WRONG!");
													console.error(error);
												}
											});	}

	});
});

/*----------------------bsrandom-----------------------*/
$(document).ready(function() {
	$('#save-bsrandom').click(function(event) {
		event.preventDefault();
		
        var hashMap = new Map();
		var ameId=$("#ameId-bsrandom").val();
		var bloodSugarRandom=$("#bsrandom-val").val();
	    var testCode="bsrandom";
		var fileInfo=$("#fileInput-bsrandom")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		    var formData = new FormData();
		    formData.append('file',fileInfo);
			hashMap.set("ameId",ameId);
			hashMap.set("bloodSugarRandom",bloodSugarRandom);
			hashMap.set("testCode",testCode);
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData);
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');
		let isRequestCompleted=false;
		if (isRequestCompleted)
			return;
		else {				$.ajax({
					type: 'POST',
					url: 'validate-and-save-blood-sugar-random',
					data:formData,
					 data: formData,
				        contentType: false,
				        processData: false,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response.isValid) {
							alert("Data Saved Succcessfully!");
							investigationbsrandomCloseModel();
								
								isRequestCompleted=true;
							    $('#loading-overlay').hide();
							    $('body').removeClass('loading');
								window.location.reload();
							}

						else {  
						 
							$.each(response.errors, function(field, errorMessage) {
								 $('#' + field + '_error').text(errorMessage);
								 });
							}
						
		},
		complete: function() {
		    $('#loading-overlay').hide();
		    $('body').removeClass('loading');
		},
					error: function(xhr, status, error) {

						alert("Please fill the form again somthing went WRONG!");
						console.error(error);
					}
				});}

	});
});

/*----------------------bsHbA1c-----------------------*/
$(document).ready(function() {
	$('#save-HbA1c').click(function(event) {
		event.preventDefault();
		
        var hashMap = new Map();
		var ameId=$("#ameId-HbA1c").val();
		var bloodSugarHbA1c=$("#bsHbA1c-val").val();
	    var testCode="bsHbA1c";
		var fileInfo=$("#fileInput-bsHbA1c")[0].files[0];
		
		 if(fileInfo==null){
				alert("Please select file To upload.......!");
				return false;
					}
		    var formData = new FormData();
		    formData.append('file',fileInfo);
			hashMap.set("ameId",ameId);
			hashMap.set("bloodSugarHbA1c",bloodSugarHbA1c);
			hashMap.set("testCode",testCode);
			
			
			
			
			var jsonArray = [];
		    hashMap.forEach(function (value, key) {
		        var obj = {};
		        obj[key] = value;
		        jsonArray.push(obj);
		    });

		    // Convert the array to a JSON string
		    var jsonData = JSON.stringify(jsonArray);
		    formData.append("jsonData", jsonData);
		
/* ===================header==================== */
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$('.error').text('');



		$('#loading-overlay').show();
		$('body').addClass('loading');
		let isRequestCompleted=false;
		if (isRequestCompleted)
					return;
				else {				$.ajax({
					type: 'POST',
					url: 'validate-and-save-blood-sugar-HbA1c',
					data:formData,
					 data: formData,
				        contentType: false,
				        processData: false,
					beforeSend: function(xhr) {
						xhr.setRequestHeader(header, token);
					},
					success: function(response) {
						if (response.isValid) {
							alert("Data Saved Succcessfully!");
							investigationbsHbA1cCloseModel();
								
							  isRequestCompleted=true;
							    $('#loading-overlay').hide();
							    $('body').removeClass('loading');
								window.location.reload();
							    
							}

						else {  
						 
							$.each(response.errors, function(field, errorMessage) {
								 $('#' + field + '_error').text(errorMessage);
								 });
							}
						
		},
		complete: function() {
		    $('#loading-overlay').hide();
		    $('body').removeClass('loading');
		},
					error: function(xhr, status, error) {

						alert("Please fill the form again somthing went WRONG!");
						console.error(error);
					}
				});}

	});
});


function calculateAGRatio(){
	var globulin=$("#globulin").val();
	var albumin=$("#albumin").val();
	
	if(albumin==" "){
		alert("Plaase Fill Albumin");
		return false;
		
	}else{
		var agRatio= (globulin/albumin).toFixed(2);
		document.getElementById("AGRatio").value=agRatio;
	}
	
}

 
					function validate(evt) {
						var theEvent = evt || window.event;

						// Handle paste
						if (theEvent.type === 'paste') {
							key = event.clipboardData.getData('text/plain');
						} else {
							// Handle key press
							var key = theEvent.keyCode || theEvent.which;
							key = String.fromCharCode(key);
						}
						var regex = /[0-9]|\./;
						if (!regex.test(key)) {
							theEvent.returnValue = false;
							if (theEvent.preventDefault)
								theEvent.preventDefault();
						}
					}
			

function saveSugar(){
	alert("dawawawawawawawawawaw");
}







		
