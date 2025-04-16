function roleModificationMethod(e) {
	var roleId = e.value;
	var forcePersonalId = e.name;


	var modal3 = document.getElementById("modified-user-role");

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
		url: "get-force-personal-details-for-update-role",
		cache: false,
		dataType: 'json',

		data: { forcePersonalId: forcePersonalId, rCode: roleId },
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {


			document.getElementById("irlaNo").innerHTML = response.forcePersonalDto.forceId;
			document.getElementById("name").innerHTML = response.forcePersonalDto.name;
			document.getElementById("rank").innerHTML = response.forcePersonalDto.rank;
			document.getElementById("forceName").innerHTML = response.forcePersonalDto.forceName;
			document.getElementById("unitName").innerHTML = response.forcePersonalDto.unitName;
			document.getElementById("forcePersonalIdAssignRole").value = response.forcePersonalId;
			document.getElementById("roleIdId").value = response.rCode;

			if (response.statusCode == 1) {
				document.getElementById("statusCode").innerHTML = "Active"
			} else {
				document.getElementById("statusCode").innerHTML = "InActive"
			}




		},
		error: function() {
			alert('Error while request..');
		}

	});
	modal3.style.display = "block";

}


function closeModel() {
	var closeButton = document.getElementById("modified-user-role");
	closeButton.style.display = "none";
	return false;
}

function closeModel4() {
	var closeButton = document.getElementById("userRoleTransfer");
	document.getElementById("remark").value = '';
	closeButton.style.display = "none";
	return false;
}



//===========================================================================New Role Transfer By BoardMember to Reserve=============================================================//
function transferRole(e) {
	// Attach click event to an element with the ID "myButton"  
	console.log(e);
	

	var boardId = e.parentElement[0].value;
	var boardMemberForcePersonalId = e.parentElement[1].value;
	var statusCode = e.parentElement[2].value;
	
	
	
	$("#boardIdId").val(boardId);
	$("#boardMemberForcePersonalIdId").val(boardMemberForcePersonalId);
	$("#statusCodeId").val(statusCode)
	$("#transfer-role").submit();
   
	
}


function createAssignerDetails(response) {


	document.getElementById("irlaNo").innerHTML = response.irlaNumber;
	document.getElementById("transferBoardId").innerHTML = response.boardId;
	document.getElementById("name").innerHTML = response.name;
	document.getElementById("rank").innerHTML = response.designation;
	document.getElementById("forceName").innerHTML = response.forceName;
	document.getElementById("unit").innerHTML = response.unitName;
	//document.getElementById("forcePersonalIdAssignRole").value = response.forcePersonalId;



}


function createAssigneeDetails(response) {

	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr><td>' + (i + 1) +
			'</td><td>' + o.forceName +
			'</td><td>' + o.irlaNumber +
			'</td><td>' + o.name +
			'</td><td>' + o.designation +
			'</td><td>' + o.unitName +
			'</td><td> <input type="checkbox" name="candidateId" checked value="' + o.forcePersonalId + '"' +
			'</td></tr>';
	});

	$('#assignee').append(trHTML);
}
//==============assign or transfer role to reserve and vise-versa=================//

function transferRoleTo() {
	var arr = new Array();


	var hashMap = new Map();

	// Adding key-value pairs

	var remark = document.getElementById("remark").value;
	var rcd = document.getElementById("rcd").value;
	var rrcd = document.getElementById("rrcd").value;
	var boardId = document.getElementById("boardId").value;
	var transferForcePersonalId = document.getElementById("transferForcePersonalId").value;

	if (remark == "" || remark == null) {
		alert("Please give Remark....!");
		return false;
	}
	$.each($("input[name='candidateId']:checked"), function() {
		arr.push($(this).val());
		hashMap.set("candidateId", $(this).val());
	});


	var transferModal = document.getElementById("userRoleTransfer");
	alert(arr.length);
	if (arr.length > 1) {
		alert("Select Only One Personal....You Have Selected More than One....!");
		return false;
	} else {
		alert("You wish to tranfer your role to " + arr[0]);
		let text;
		if (confirm("Press Ok To Confirm !") == true) {
			hashMap.set("rcd", rcd);
			hashMap.set("rrcd", rrcd);
			hashMap.set("boardId", boardId);
			hashMap.set("transferForcePersonalId", transferForcePersonalId);
			hashMap.set("remark", remark);

			arr.push(rcd);
			arr.push(rrcd);
			arr.push(boardId);
			var token = $("meta[name='_csrf']").attr("content");
			var header = $("meta[name='_csrf_header']").attr("content");



			var jsonArray = [];
			hashMap.forEach(function(value, key) {
				var obj = {};
				obj[key] = value;
				jsonArray.push(obj);
			});

			// Convert the array to a JSON string
			var jsonData = JSON.stringify(jsonArray);

			$.ajax({

				type: "post",
				url: "transfer-role-to-boardmember-by-boardId",
				cache: false,
				dataType: 'json',
				contentType: 'application/json',

				data: jsonData,


				beforeSend: function(xhr) {

					xhr.setRequestHeader(header, token);
				},
				success: function(response) {

					if (response.code == 1) {

						transferModal.style.display = "none";
					}
					else {

						alert("Please contact Board detailing Authority....");

					}




				},
				error: function() {
					transferModal.style.display = "none";
					alert('Error while request..........!');
				}

			});

			return true;
		} else {
			alert("Process terminated......!");

			transferModal.style.display = "none";
			document.getElementById("remark").value = '';
			return false;

		}

	}

}


//=========================change Role Status===========================================//
function changeRoleStatus(e) {
	var remark = document.getElementById("remark").value;
	var roleStatus = $('select[name="status"] :selected').val();
	var forcePersonalIdAssignRole = document.getElementById("forcePersonalIdAssignRole").value;
	var userRoleId = document.getElementById("roleIdId").value;


	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	var modal3 = document.getElementById("modified-user-role");
	var span = document.getElementsByClassName("modal3");

	var data = {
		remark: remark,
		roleStatus: roleStatus,
		forcePersonalIdAssignRole: forcePersonalIdAssignRole,
		userRoleId: userRoleId
	}

	$.ajax({

		type: "post",
		url: "update-user-role-status",
		cache: false,
		dataType: 'json',
		contentType: 'application/json',
		data: JSON.stringify(data),

		beforeSend: function(xhr) {


			xhr.setRequestHeader(header, token);
		},
		success: function(response) {

			if (response.code == 1) {
				alert(response.message);
				modal3.style.display = "none";
				window.location.href = "manage-all-existing-user-role";

			}
			else {
				alert(response.message);
				modal3.style.display = "none";

			}
		},
		error: function() {
			modal3.style.display = "none";
			alert('Error while request..........!');
		}

	});


}



function privilageModification(e) {

	var transactionId = e.value;
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	$.ajax({

		type: "post",
		url: "get-role-assigned-privilge-for-user",
		data: { transactionId: transactionId },

		beforeSend: function(xhr) {


			xhr.setRequestHeader(header, token);
		},
		success: function(response) {
			if (response.code == 1) {
                   var items=response.RolePrivilage;
				document.addEventListener("DOMContentLoaded", function() {
					items.forEach(function(item, index) {
						var checkbox = document.getElementById('checkbox-' + (index + 1));
						if (item.checked) {
							checkbox.checked = true;
						}
					});
				});

			} else {
				alert("Operation Failed .........")
			}


		},
		error: function() {

			alert('Error while request..........!');
		}

	});


	function openModal() {
		var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
		myModal.show();
	}
}

