function createNewRoleModel(e) {
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
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			modal2.style.display = "none";
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
			document.getElementById("forcePersonalIdAssignRole").value= response.forcePersonalId;
			crateDropDownRoleList(response.masterRoleList);
			crateDropDownPermissionList(response.refRoles);
			createDepartmentDropDown(response.departmentList);

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


	function crateDropDownPermissionList(masterRoleList) {

			var dropdown1 = document.getElementById("permissionList");
			dropdown1.innerHTML = "";
			for (let i = 0; i < masterRoleList.length; i++) {
				const option = document.createElement("option");
				option.text = masterRoleList[i].roleName;
				option.value = masterRoleList[i].roleId;
				dropdown1.appendChild(option);
				
			}

		}





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





function assign_newRole() {

	 var forcePersonalIdAssignRole = $("forcePersonalIdAssignRole").val();
     
     var roleId = $('#roleTypeSelect :selected').val();
     var permissionRoleId = $('#permissionList :selected').val();
     var department=$('#departmentId :selected').val();
     var rankDropDown=$('#rankDropDown :selected').val();
    
     
    document.getElementById("roleIdId").value=roleId;
    document.getElementById("permissionRoleId").value=permissionRoleId;
    document.getElementById("designationId").value=rankDropDown;
   document.getElementById("departmentId_Id").value=department;
    
    
 
    return false;
    
     	
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
	}

	// When the user clicks anywhere outside of the modal, close it
	window.onclick = function(event) {
		if (event.target == modal2) {
			modal2.style.display = "none";
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
			crateDropDownPermissionList(response.masterRoleList);
			document.getElementById("irlaNo").innerHTML = response.forcePersonalDto.forceId;
			document.getElementById("name").innerHTML = response.forcePersonalDto.name;
			document.getElementById("rank").innerHTML = response.forcePersonalDto.rank;
			document.getElementById("forceName").innerHTML = response.forcePersonalDto.forceName;
			document.getElementById("unit-name").innerHTML = response.forcePersonalDto.unitName;
			document.getElementById("forcePersonalIdAssignRole").value= response.forcePersonalId;

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
	window.location = window.location;
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




     	
