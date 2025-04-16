$(document).ready(function() {
    $('#final-details-page').submit(function(event) {
        event.preventDefault(); 

      
	var modal2 = document.getElementById("final_details_model");

	// Get the button that opens the modal
	//var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("modal2");

	// When the user clicks the button, open the modal 

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal2.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			modal2.style.display = "none";
		}
	}
	var ameId=$("#ameId").val();
	
       /*var formData = $(this).serialize();*/
     //  console.log(">>>>>>>>>>>>>>>>>>>>>>>>>>>>",formData);
     /* =================Appendages================= */
    /*  var upperLimbShapeId=$('#upperLimbShapeId').text();
     var lowerLimbShapeId=$('#lowerLimbShapeId').text();
     var spineShapeId=$('#spineShapeId').text(); */
     
     /* ===================Ear==================== */
    /*  var leftEyeShapeId = $('#leftEyeShapeId').text();
     var rightEyeShapeId = $('#rightEyeShapeId').text();
      */
     /* ===================Hearing==================== */
    /*  var hearingShapeId=$('#hearingShapeId').text(); */
     
     /* ===================Physical==================== */
     /* var physicalShapeId=$('#physicalShapeId').text(); */
     
     /* ===================Psychology==================== */
   /*   var psycologicalShapeId=$("#psycologicalShapeId").text(); */
     
     /* ===================Final Shape Awarded==================== */
   /*  var finalShapeAwardedId=$("#finalShapeAwardedId").text(); */
     
     /* ===================header==================== */
      var token = $("meta[name='_csrf']").attr("content");
     var header = $("meta[name='_csrf_header']").attr("content"); 
     
     
     
     
      
        $.ajax({
            type: 'POST',
            url: 'final-details-submit',
            data:
	ameId
	,
            contentType: 'application/json',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function(response) {
                if (response.isValid) {
                    
                    $('#final-details-page').unbind('submit').submit();
                    
                    
                    
                    } 
                    
                    else {
                	
                                   
                   let dynamicErrorsHeader=$('<h3>').text("Please provide the following:");
                                    
                   let  checkUpListTestErrorsHeader=$('<h3>').text("Please provide the following investigation report:");
                                    
                   let categoryRemarkErrorsHeader=$('<h3>').text("Please provide the following:");
                                    
                  
						let dynamicErrors=$('<h6 id="dynamic-errors">');
						let checkUpListTestErrors=$('<h6 id="testNameListConcat_error">');
						let categoryRemarkErrors=$('<h6 id="category-remark-errors">');
						let header = $('<h3>').text("Please provide the following: ").append('<br>');
                    	 $('#alert-modal .modal-title').text('MESSAGE');
			           
			                 $('#alert-modal .modal-dialog').addClass('modal-lg');
			                 
			            $('#alert-modal .modal-body i').addClass('fas fa-exclamation-circle fa-lg animate__animated animate__shakeX')
		                                               .css('color', 'red');
		                	$('#alert-modal .modal-body #final-submit-div').append(header);
						 $('#alert-modal .modal-body #final-submit-div').append(dynamicErrors);
							$('#alert-modal .modal-body #final-submit-div').append(checkUpListTestErrors);
							$('#alert-modal .modal-body #final-submit-div').append(categoryRemarkErrors);
							  $("#cross-close-button").attr('onclick', 'eraser()');
				            $("#okay-close-button").attr('onclick', 'eraser()');
			            $('#alert-modal').modal({
			                backdrop: 'static',
			                keyboard: false
			              });
			            $('#alert-modal').modal('show');
			            
                    $.each(response.errors, function(field, errorMessage) {
						
						 if(field.localeCompare("testNameListConcat")===0)	{
							
						$('#' + field + '_error').text(errorMessage + " is remaining!");
						}
						else if(field.localeCompare("categoryRemarkList")===0){
							
							var dynamicErrorsBody = $('#dynamic-errors');
						    var newSpanForMessage = $('<span>').text(errorMessage).append('<br>');
						     
						    dynamicErrorsBody.append(newSpanForMessage);
						}	
						else{
							var categoryRemarkErrors = $('#category-remark-errors');
						    var newSpanForMessage = $('<span>').text(errorMessage).append('<br>');
						    categoryRemarkErrors.append(newSpanForMessage);
						}
						
						
                    	/*if(field.localeCompare("testNameListConcat")===0)	{
							
						$('#' + field + '_error').text(errorMessage + " is remaining!");
						}	
            else if(field.localeCompare("categoryRemarkList")===0){
            
							$('#' + field + '_error').text(errorMessage); 
             
           
                       
						
						if(response.errors.categoryRemarkList.length>0){
						var dynamicErrorsBody = $('#dynamicErrors');
					//	var newSpanForHeader = $('<b>').text("Please provide the Remark for the following: ").append('<br>');
						var newSpanForMessage = $('<span>').text(errorMessage).append('<br>');
					//	 dynamicErrorsBody.append(newSpanForHeader);
						 dynamicErrorsBody.append(newSpanForMessage);
						}
						else{
							var dynamicErrorsBody = $('#dynamicErrors');
						    var newSpan = $('<span>').text(errorMessage).append('<br>');
						    dynamicErrorsBody.append(newSpan);
						   // var newSpanForHeader = $('<b>').text("Please provide the following: ").append('<br>');
						    var newSpanForMessage = $('<span>').text(errorMessage).append('<br>');
						   // dynamicErrorsBody.append(newSpanForHeader);
						    dynamicErrorsBody.append(newSpanForMessage);
						}
						
						newRow.append($('<span>').text(errorMessage));
					   
					    
					    
					    }
						
						 $('#' + field).addClass('error');
						  $('#' + field).addClass('error').attr('placeholder',errorMessage); 

						

*/



					}
					
					);

					modal2.style.display = "block";
				}
			},
			error: function(xhr, status, error) {

				alert("Please fill the form again somthing went Wrong!");
				
			}
		});
	});
});



/*======================close model file===========================*/
function closeModel() {
	var closeButton = document.getElementById("final_details_model");

	closeButton.style.display = "none";
}

function eraser(){
	
	$("#final-submit-div").text('');

}







/*====================================Final Details Comment Adding and Deleting methods=======================================*/



function getSubCategoryType (e) {

				var categoryType = e.value;
				var ameId=document.getElementById("ameId").value;
 
 
        if(categoryType==0){
			 document.getElementById("formContainer").style.display = "none";
			 document.getElementById("commentId").value='';
			return false;
		}
 document.getElementById("commentId").value='';
              
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$.ajax({
					type: 'Post',
					url: 'get-sub-category-value-by-category',

					data: {categoryType: categoryType,ameId:ameId}, beforeSend: function (xhr) {

						xhr.setRequestHeader(header, token);
					},

					success: function (response) {

						document.getElementById("category").value=response.subCategoryType;
						
						document.getElementById("categoryTypeId").value=response.categoryType;
						
						document.getElementById("subCategory").value=response.subCategoryType;
						
						document.getElementById("formContainer").style.display = "block";
					},
					error: function (e) {



						console.log("ERROR : ", e);

					}
				});
			}

			
		/*==================Add To Down Category Comment====================*/

function addCategoryComment() {



	var categoryTypeValue = document.getElementById("categoryTypeId").value;
	var subCategoryValue = document.getElementById("subCategory").value;
	var comment = document.getElementById("commentId").value;
	var ameId = document.getElementById("ameId").value;


    if(comment === null || comment === undefined || comment === ''){
		alert("Please Provide Comment for the Category....");
		return false;
	}
	if(categoryTypeValue === null || categoryTypeValue === undefined || categoryTypeValue === ''){
		alert("Please Provide Comment for the Category....");
		return false;
	}
	if(subCategoryValue === null || subCategoryValue === undefined || subCategoryValue === ''){
		alert("Please Provide Category....");
		return false;
	}
	

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type: 'Post',
		url: 'add-final-comment-for-down-category',

		data: {
			categoryTypeValue: categoryTypeValue,
			ameId: ameId,
			subCategoryValue: subCategoryValue,
			comment: comment
		},
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},

		success: function(response) {
			
			
			var table = document.getElementById("commentDatatableId");


			while (table.rows.length > 0) {
				table.deleteRow(0);
			}


			createDynamicCommnetTable(response.CommentTable);
createDynamicCategoryTypeDropDown();


			document.getElementById("formContainer").style.display = "none";

		},
		error: function(e) {



			console.log("ERROR : ", e);

		}
	});

}

		/*============================Creating Dynamic Table For Comment===============================*/
		
function createDynamicCommnetTable(response){
	var trHTML = '';
	$("#commentDatatableId").text("");
	$.each(response, function(i, o) {
		trHTML += '<tr class="commentDatatable">' +
			'</td><td>' + o.categoryTypeName +
			'</td><td>' + o.subCategoryType +
			'</td><td>' + o.comment +
			'<td> <input type="button" value="Delete" name="'+o.id+'" onclick="deleteComment(this)" class="btn btn-danger btn-sm">'
			'</td></tr>';
	});

	$('#commentDatatableId').append(trHTML);
}		
		
		
		//=======================Delete comment================================//
		
		
	function deleteComment(e){
		
		
		var id=e.name;
		var ameId=document.getElementById("ameId").value;
		
	
		
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
				
				
				$.ajax({
					type: 'get',
					url: 'delete-final-comment-for-down-category',
                    cache: false,
		            dataType: 'json',
					data: {id:id,ameId:ameId},
					beforeSend: function (xhr) {

						xhr.setRequestHeader(header, token);
					},

					success: function (response) {	
						
						
				var table=document.getElementById("commentDatatableId");
				
				
				
					 createDynamicCommnetTable(response.CommentTable);
                     createDynamicCategoryTypeDropDown();
						
					},
					error: function (e) {



						console.log("ERROR : ", e);

					}
				});
            
            
            
	}
	
		function createDynamicCategoryTypeDropDown(){
			
		var ameId=document.getElementById("ameId").value;
		var forcePersonnelId=$("#forcePersonnelId").val();
	
		var token = $("meta[name='_csrf']").attr("content");
	    var header = $("meta[name='_csrf_header']").attr("content");
		var dataToSend= {ameId:ameId,
					forcePersonnelId:forcePersonnelId};		
				
				$.ajax({
					type: 'Post',
					url: 'get-category-type-list-for-remark',

				data:JSON.stringify(dataToSend),
			contentType: 'application/json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},

					success: function (response) {
						if (response.isValid) {
				var dropdown = document.getElementById("categoryType");
	           dropdown.innerHTML = "";
	           const option = document.createElement("option");
		      option.text = "Select";
		      option.value = "Select";
		        dropdown.appendChild(option);
	          for (let i = 0; i < response.categoryType.length; i++) {
		          const option = document.createElement("option");
		      option.text = response.categoryType[i].value;
		      option.value = response.categoryType[i].cValue;
		      dropdown.appendChild(option);
	}
	}
	else{
		         $('#alert-modal .modal-title').text('ERROR');
			            $('#alert-modal .modal-body h3').text('Server not responding please sign-in again');
			            
			            $('#alert-modal .modal-body i').addClass('fas fa-exclamation-circle fa-lg animate__animated animate__shakeX')
		                                               .css('color', 'red');
			            $('#alert-modal').modal({
			                backdrop: 'static',
			                keyboard: false
			              });
			            $('#alert-modal').modal('show');
	}


},
					error: function(xhr, status, error) {
                      $('#alert-modal .modal-title').text('ERROR');
			            $('#alert-modal .modal-body h3').text('Server not responding please sign-in again');
			            
			            $('#alert-modal .modal-body i').addClass('fas fa-exclamation-circle fa-lg animate__animated animate__shakeX')
		                                               .css('color', 'red');
			            $('#alert-modal').modal({
			                backdrop: 'static',
			                keyboard: false
			              });
			            $('#alert-modal').modal('show');
					}
				});
            
            
	
	
}
		

	














