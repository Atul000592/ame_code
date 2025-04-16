
function assignDealingHand(e) {
	

   var forcePersonalIdDealingHand=e.id;
  // var boardId=e.previousElementSibling.value;
  
  const boardId = document.getElementById("boardId_Id").value;

   
	var modal3 = document.getElementById("assign-dealing-hand");

	// Get the button that opens the modal
	//var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("modal3");

	// When the user clicks the button, open the modal 

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal3.style.display = "none";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal3) {
			modal3.style.display = "none";
		}
	}
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({

		type: "post",
		url: "get-personal-details-for-role-assignment-dealing-hand",
		cache: false,
		dataType: 'json',

		data: { forcePersonalId: forcePersonalIdDealingHand,boardId:boardId },
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
             if(response.statusCode=='0'){
				document.getElementById("message").innerHTML=response.message;
				document.getElementById("assign_btn").style.display = "none";
				
				
			 }
		else{
			
			document.getElementById("irlaNo").innerHTML = response.forcePersonalDto.forceId;
			document.getElementById("name").innerHTML = response.forcePersonalDto.name;
			document.getElementById("rank").innerHTML = response.forcePersonalDto.rank;
			document.getElementById("forceName").innerHTML = response.forcePersonalDto.forceName;
			document.getElementById("unitName").innerHTML = response.forcePersonalDto.unitName;
			document.getElementById("forcePersonalIdAssignRole").value= response.forcePersonalId;
			document.getElementById("boardIdId_Id").innerHTML= response.boardId;
			document.getElementById("roleIdId").value= response.roleCodeId;
			document.getElementById("roleIdIdDealingHand").value= response.roleCodeId;
			document.getElementById("boardIdDealingHand").value= response.boardId;
            createDepartmentDropDown(response.departmentList);

            modal3.style.display = "block";
}
		},
		error: function() {
			alert('Error while request..');
		}

	});
	

}


function closeModel() {
	var closeButton = document.getElementById("assign-dealing-hand");

	closeButton.style.display = "none";
}




/*=========================create rolelist dropDown============================ */

function assign_newRole() {

	 var forcePersonalIdAssignRole = $("forcePersonalIdAssignRole").val();
     
     var roleId = $('#roleTypeSelect :selected').val();
     var permissionRoleId = $('#permissionList :selected').val();
     
    document.getElementById("roleIdId").value=roleId;
    document.getElementById("permissionRoleId").value=roleId;
 
    return false;
    
     	
     	}
     	
     	
     	
     
     
//===========================================================================New Role Created By bda=============================================================//


/*=======================create Department dropDown=========================*/

function createDepartmentDropDown(departmentList){
	
	
			var dropdown2 = document.getElementById("departmentId");
			dropdown2.innerHTML = "";
			for (let i = 0; i < departmentList.length; i++) {
				const option = document.createElement("option");
				option.text = departmentList[i].forceName;
				option.value = departmentList[i].forceNo;
				dropdown2.appendChild(option);
	}
}


 //==================Get Designation List=====================//
    function getDesignationList(e) {

    var forceNo = e.value;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({

		type: "post",
		url: "create-rank-drop-down-by-department",
		cache: false,
		dataType: 'json',

		data: { forceNo: forceNo },
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			//createDepartmentDropDown(response.departmentList)

			createRankDropDown(response.rankList);




		},
		error: function() {
			alert('Error while request..');
		}

	});
}



/*========================= On department Change load unit========================== */

function createRankDropDown(departmentList) {


	var dropdown3 = document.getElementById("rankDropDown");
	dropdown3.innerHTML = "";
	for (let i = 0; i < departmentList.length; i++) {
		const option = document.createElement("option");
		option.text = departmentList[i].rankFullName;
		option.value = departmentList[i].id;
		dropdown3.appendChild(option);
	}
}



	/*----------------------------------------getgetForcePersonnelList(this)-------------------------------------------*/

function getForcePersonnelList(e){

const department=$("#departmentId").val();
const designation=e.value;

const dataToSend={
	               department:department,
                   designation:designation
                 }

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
	
	
    $.ajax({
			type: 'POST',
			url: 'get-force-personnel-list-for-role-assigning',
			data:JSON.stringify(dataToSend),
			contentType: 'application/json',
			beforeSend: function(xhr) {
				xhr.setRequestHeader(header, token);
			},
			success: function(response) {
				if (response.forcePersonnelList) {
					console.log(response.forcePersonnelList);
					
					const orderByDropDown = document.getElementById("orderByDropDown");
					orderByDropDown.innerHTML='';
					response.forcePersonnelList.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name; 
                option.textContent = item.forceId+"-"+item.name+"-"+item.designation+"-"+item.unitName; 
                orderByDropDown.appendChild(option);
            });
					
					
					
					
		}
		
		else{
			
			
					console.log(response.personnelOthersList);
					
					const orderByDropDown = document.getElementById("orderByDropDown");
					orderByDropDown.innerHTML='';
					response.personnelOthersList.forEach(item => {
                const option = document.createElement('option');
                option.value = item.name; 
                option.textContent = item.name+"-"+item.designation; 
                orderByDropDown.appendChild(option);
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



$(document).ready(function() {
	$('#boardId').on('change', function() {		
	    const boardId=$("#boardId").val();
	    
	    if(boardId!=="0"){
	    alert("boardId has been changed by you Please select force And unit .......!");
		document.getElementById("boardId_Id").value=boardId;}

     })
});

$(document).ready(function() {
	$('#forceName_id').on('change', function() {
		var forceId = $(this).val();
		
	    var boardId=$("#boardId").val();
	    
	    if(boardId==" "){
			alert("Please select BoardId.......!");
			return false;
		}

		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type: 'Post',
			url: 'get-unit-by-forceNo-board',

			data:
			{
				forceId: forceId,boardId:boardId
			},
			beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
			success: function(data) {
				createUnitTable(data.unitList);
				createAttachUnitTable(data.unitList);
				document.getElementById("boardId_Id").value=data.boardId;
			},
			error: function(e) {
				console.log("ERROR : ", e);

			}
		});
	});
});



function createUnitTable(data) {
	const dropdown = document.getElementById("unit_Id");
	dropdown.innerHTML = "";
	const option = document.createElement("option");
	option.text = 'Select';
	option.value = 0;
	dropdown.appendChild(option);
	for (let i = 0; i < data.length; i++) {
		const option = document.createElement("option");
		option.text = data[i].unitName;
		option.value = data[i].unitId;
		// !-- option.setAttribute("data", data[i].forceId)-->
		dropdown.appendChild(option);
	}
}

function createAttachUnitTable(data) {
	const dropdown = document.getElementById("attch_unit");
	dropdown.innerHTML = "";
	const option = document.createElement("option");
	option.text = 'Select';
	option.value = 0;
	dropdown.appendChild(option);
	for (let i = 0; i < data.length; i++) {
		const option = document.createElement("option");
		option.text = data[i].unitName;
		option.value = data[i].unitId;
		// !-- option.setAttribute("data", data[i].forceId)-->
		dropdown.appendChild(option);
	}
}
     	
     	
     	
     	
     	



//=======get forcePersonal details by unit==================//
$(document).ready(function() {
$('#unit_Id').on('change',
	function() {
		var unitId = $(this).val();
		var forceName_id = $("#forceName_id").val();
		var boardId=$("#boardId").val();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

	

			$.ajax({
				type: "post",
				url: "get-force-personal-by-unit-force-id-dh",
				cache: false,
				dataType: 'json',

				data: ({ queryData: JSON.stringify({ unitNo: unitId, forceNo: forceName_id ,boardId:boardId}) }),

				beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
				success: function(response) {
                 
					if(response.personalResponeseAjax.length ==0){
						
						
						document.getElementById("modal-body").innerHTML="no data found in the given unit";
						showModal();
						
					}else{
						
						document.getElementById("boardId_Id").value=response.boardId;
					
                 	if ($.fn.DataTable.isDataTable("#tab-personal")) {
						$("#tab-personal").dataTable().fnDestroy();
						console.log("DataTable destroyed");
					}

					// Initialize DataTable with new data
					$("#tab-personal").DataTable({
						"scrollX": true, // Enable horizontal scrolling
						"scrollY": "100%", // Enable vertical scrolling with a fixed height
						"data": response.personalResponeseAjax, // Assuming response.data contains your new data
						"columns": [

							{"data": 'forceName'},
							{"data": 'irlaNumber'},
							{"data": 'name'},
							{"data": 'designation'},
							{"data": 'gender'},

							{"data": 'unitName'},
							{
								"data": null,
								"render": function (data, type, full, meta) {
									return '<input type="button" class="btn btn-primary" value="Assign" onclick="assignDealingHand(this)" id="' + data.forcePersonalId + '">';
									
								}
							}
						]
					});
					
					}
					
				},
				error: function() {
					alert('Error while request..');
				}
			});
		
	});

});
//=======get forcePersonal details by Attach Unit==================//
$(document).ready(function() {
$('#attch_unit').on('change',
	function() {
		var unitId = $(this).val();
		var forceName_id = $("#forceName_id").val();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

	

			$.ajax({
				type: "post",
				url: "get-force-personal-by-attach-unit-force-id-dh",
				cache: false,
				dataType: 'json',

				data: ({ queryData: JSON.stringify({ unitNo: unitId, forceNo: forceName_id }) }),

				beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
				success: function(response) {


					if ($.fn.DataTable.isDataTable("#tab-personal")) {
						$("#tab-personal").dataTable().fnDestroy();
						console.log("DataTable destroyed");
					}
					// Initialize DataTable with new data
										$("#tab-personal").DataTable({
											"scrollX": true, // Enable horizontal scrolling
											"scrollY": "100%", // Enable vertical scrolling with a fixed height
											"data": response.personalResponeseAjax, // Assuming response.data contains your new data
											"columns": [

												{"data": 'forceName'},
												{"data": 'irlaNumber'},
												{"data": 'name'},
												{"data": 'designation'},
												{"data": 'gender'},
												{"data": 'unitName'},
												{
													"data": null,
													"render": function (data, type, full, meta) {
														return '<input type="button" class="btn btn-primary" value="Assign" onclick="assignDealingHand(this)" id="' + data.forcePersonalId + '">';
														
													}
												}
											]
										});
				},
				error: function() {
					alert('Error while request..');
				}
			});
		
	});


});
     	
  
     	
     	
