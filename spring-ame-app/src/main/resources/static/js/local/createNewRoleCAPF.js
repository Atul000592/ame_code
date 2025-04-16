

//===================assigning role capf and other admins===================================//

function createNewRoleModelCapfAdmin(e) {
	//var row = $(e).closest('.memberData');
	var forcePersonalId = e.id;

    console.info(forcePersonalId);
	var modal2 = document.getElementById("createRoleModeId");

	// Get the button that opens the modal
	//var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("modal2");

	// When the user clicks the button, open the modal 

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal2.style.display = "none";
		document.getElementById("myDiv").innerHTML="";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			modal2.style.display = "none";
			document.getElementById("myDiv").innerHTML="";
		}
	}
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({

		type: "post",
		url: "get-personal-details-for-role-assignment",
		cache: false,
		dataType: 'json',

		data: { forcePersonalId: forcePersonalId },
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {


			document.getElementById("irlaNo").innerHTML = response.forcePersonalDto.forceId;
			document.getElementById("name").innerHTML = response.forcePersonalDto.name;
			document.getElementById("rank").innerHTML = response.forcePersonalDto.rank;
			document.getElementById("forceName").innerHTML = response.forcePersonalDto.forceName;
			document.getElementById("unit-name").innerHTML = response.forcePersonalDto.unitName;
			document.getElementById("forcePersonalIdAssignRole").value = response.forcePersonalId;
			
			createDepartmentDropDown(response.departmentList);
			
			if(response.masterRoleList!=null){
		      crateDropDownRoleList(response.masterRoleList);
              createDynaminCheck(response.refRoles);
			}
          else{
			  alert("NOT AUTHORIZED TO ASSIGN ANYROLE........!");
			  
			  
			  closeModel();
		  }
		},
		error: function() {
			alert('Error while request..');
		}

	});
	modal2.style.display = "block";

}


function closeModel() {
	var closeButton = document.getElementById("createRoleModeId");

	closeButton.style.display = "none";
}




/*=========================create rolelist dropDown============================ */

function crateDropDownRoleList(masterRoleList) {

	var dropdown = document.getElementById("roleTypeSelect");
	dropdown.innerHTML = "";
	for (let i = 0; i < masterRoleList.length; i++) {
		const option = document.createElement("option");
		option.text = masterRoleList[i].roleName;
		option.value = masterRoleList[i].roleId;
		dropdown.appendChild(option);
	}

}

/*create permission Role List*/




/*=====================================create new role forceAndCapfAdmin popup js code=============================*/
function createNewRoleModelForceCapfAdmin(e) {
	//var row = $(e).closest('.memberData');
	//var forcePersonalId=row[0].cells[0].firstElementChild.attributes.value.nodeValue;


	var modal2 = document.getElementById("createRoleModeForceCapfAdminId");

	// Get the button that opens the modal
	//var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("modal2");

	// When the user clicks the button, open the modal 

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		modal2.style.display = "none";
		document.getElementById("myDiv").innerHTML="";
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			modal2.style.display = "none";
			document.getElementById("myDiv").innerHTML="";
		}
	}
	createRoleAssignTable(e);

	modal2.style.display = "block";

}


/*======================close model file===========================*/
function closeModel1() {
	var closeButton = document.getElementById("createRoleModeForceCapfAdminId");
document.getElementById("myDiv").innerHTML="";
	closeButton.style.display = "none";
}

/*================================create role assign table ================================*/


function createRoleAssignTable(response) {
	var trHTML = '';

	$.each(response, function(i, o) {
		trHTML += '<tr>' +
			'<td>' + o.forceName +
			'</td><td>' + o.irlaNo +
			'</td><td>' + o.name +
			'</td><td>' + o.rank +
			'</td><td>' + o.unitName +
			'<td></td>' + o.role +
			'</td>' +
			'</tr>';
	});

	$('#myTable_t1').append(trHTML);

}







//========================================multiple checkBox checked==================================//

function getCheckedCheckboxesFor(roleTypeId) {
	var roleId = $('#roleTypeSelect :selected').val();
	var checkboxes = document.querySelectorAll('input[name="' + roleTypeId + '"]:checked'), values = [];
	Array.prototype.forEach.call(checkboxes, function(el) {
		values.push(el.value);

	});
	document.getElementById("permissionId").value = values;
	document.getElementById("roleIdId").value = roleId;
	//var department = $('#departmentId :selected').val();
	var rankDropDown = $('#rankDropDown :selected').val();
	document.getElementById("designationId").value = rankDropDown;
	// document.getElementById("departmentId_Id").value=department;
	return values;

	$("#form_id").submit();
}



/*=======================create Department dropDown=========================*/

function createDepartmentDropDown(departmentList) {


	var dropdown2 = document.getElementById("departmentId");
	dropdown2.innerHTML = "";
	const defaultOption = document.createElement("option");
   defaultOption.text = "Select Department";  // Text to display
  defaultOption.value = "0";            // Value of the option (can be empty or some default value)
  dropdown2.appendChild(defaultOption);
	for (let i = 0; i < departmentList.length; i++) {
		const option = document.createElement("option");
		option.text = departmentList[i].forceName;
		option.value = departmentList[i].forceNo;
		dropdown2.appendChild(option);
	}
}




/*=================ajax call ==========================*/


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
	// Create a default option
   const defaultOption = document.createElement("option");
   defaultOption.text = "Select Rank/Designation";  // Text to display
  defaultOption.value = "0";            // Value of the option (can be empty or some default value)
  dropdown3.appendChild(defaultOption);
	
	for (let i = 0; i < departmentList.length; i++) {
		const option = document.createElement("option");
		option.text = departmentList[i].rankFullName;
		option.value = departmentList[i].id;
		dropdown3.appendChild(option);
	}
}




//==============================create check box list dynamic================================================================//

function createDynaminCheck(response) {

	var checkBoxList = response;
	var myDiv = document.getElementById("myDiv");

	for (let index = 0; index < checkBoxList.length; index++) {
		var checkbox = document.createElement('input');
		checkbox.type = "checkbox";
		checkbox.name = "roleTypeId";
		checkbox.value = checkBoxList[index].roleId;


		// creating label for checkbox
		var label = document.createElement('label');

		// assigning attributes for 
		// the created label tag 
		label.htmlFor = "id";

		// appending the created text to 
		// the created label tag 
		label.appendChild(document.createTextNode(checkBoxList[index].roleName));

		// appending the checkbox
		// and label to div
		myDiv.appendChild(checkbox);
		myDiv.appendChild(label);
	}

}


//===========================================================================New Role Created By bda=============================================================//


function createNewRoleModelBybda(e) {
	var row = $(e).closest('.memberData');
	var forcePersonalId = row[0].cells[0].firstElementChild.attributes.value.nodeValue;


	var modal2 = document.getElementById("createRoleModeId");

	// Get the button that opens the modal
	//var btn = document.getElementById("myBtn");

	// Get the <span> element that closes the modal
	var span = document.getElementsByClassName("modal2");

	// When the user clicks the button, open the modal 

	// When the user clicks on <span> (x), close the modal
	span.onclick = function() {
		
		modal2.style.display = "none";
		document.getElementById("myDiv").innerHTML="";
	
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			
			modal2.style.display = "none";
			document.getElementById("myDiv").innerHTML="";
	
		}
	}
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({

		type: "post",
		url: "get-personal-details-for-role-assignment-bda",
		cache: false,
		dataType: 'json',

		data: { forcePersonalId: forcePersonalId },
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {

			crateDropDownRoleList(response.masterRoleList);

			document.getElementById("irlaNo").innerHTML = response.forcePersonalDto.irlaNumber;
			document.getElementById("name").innerHTML = response.forcePersonalDto.name;
			document.getElementById("rank").innerHTML = response.forcePersonalDto.rank;
			document.getElementById("forceName").innerHTML = response.forcePersonalDto.forceName;
			document.getElementById("unit-name").innerHTML = response.forcePersonalDto.unitName;
			document.getElementById("forcePersonalIdAssignRole").value = response.forcePersonalId;

		},
		error: function() {
			alert('Error while request..');
		}

	});
	modal2.style.display = "block";

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














function closeModel() {
	var closeButton = document.getElementById("createRoleModeId");
	document.getElementById("myDiv").innerHTML="";
	closeButton.style.display = "none";
	window.location = window.location;
}







