/*=============================get Unit details by Force Id=====================*/

 function getForceData (e) {

				var forceId = e.value;
                 var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$.ajax({
					type: 'Post',
					url: 'get-unit-by-forceNo-board',

					data: {forceId: forceId}, beforeSend: function (xhr) {

						xhr.setRequestHeader(header, token);
					},

					success: function (data) {
                     createUnit(data.unitList);
			           createAttachUnit(data.unitList);			
				

					},
					error: function (e) {



						console.log("ERROR : ", e);

					}
				});
			}

			
			
/*======================================get details by unit id===============================*/

			function getUnitData(e) {
				
				var unitId = e.value;
				var forceName_id = $("#forceName_id").val();
				
				let text = "Press a button!\nEither OK or Cancel.";
				

					document.getElementById("unit_Id").value = unitId;
					document.getElementById("forceName_id").value = forceName_id;

                        createMedicalEmployeesAjax(forceName_id,unitId);

					
				

					document.getElementById("attach_unit").value = '';
				
				}
			




/*======================================get details by Attach unit id===============================*/

			function getAttachUnitData(e) {
				
				var unitId = e.value;
				var forceName_id = $("#forceName_id").val();
				
				
					text = "CONFIRM TO SEARCH DATA PRESS OK ";

					document.getElementById("unit_Id").value = unitId;
					document.getElementById("forceName_id").value = forceName_id;

                    createAttachUnitMedicalEmployeesAjax(forceName_id,unitId);

					
				

					document.getElementById("unit_Id").value = '';
				
			}

			
			
/*=================================Function to create table of Medical Employees===========================*/			
			
		function createMedicalEmployeesAjax(force,unit) {
	// Attach click event to an element with the ID "myButton"


	

	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

$.ajax({

		type: "post",
		url: "get-force-personal-by-unit-doctor",
		cache: false,
		dataType: 'json',

		data: {
			forceId: force,
			unitId: unit,
			},
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {

			if (response.code == 1) {

				var table=document.getElementById("forcePersonalDoctor");
				while (table.rows.length > 0) {
					table.deleteRow(0);
				}
				
				createMedicalEmployeesTable(response.unitDoctorList);
				
			}
			else {

				alert("Please contact Board detailing Authority....");

			}




		},
		error: function() {
			
			
			alert('Error while request..........!');
		}

	});



}



/*=================================Function to create table of Medical Employees===========================*/			
			
function createAttachUnitMedicalEmployeesAjax(force,unit) {
	// Attach click event to an element with the ID "myButton"
   
   var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

     $.ajax({

		type: "post",
		url: "get-force-personal-by-attach-unit-doctor",
		cache: false,
		dataType: 'json',

		data: {
			forceId: force,
			unitId: unit,
			},
		beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},
		success: function(response) {

			if (response.code == 1) {

				var table=document.getElementById("forcePersonalDoctor");
				while (table.rows.length > 0) {
					table.deleteRow(0);
				}
				
				createAttachUnitMedicalEmployeesTable(response.unitDoctorList);
				
			}
			else {

				alert("Please contact Board detailing Authority....");

			}




		},
		error: function() {
			
			
			alert('Error while request..........!');
		}

	});



}


function createMedicalEmployeesTable(response){
	

	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr class="memberData">' +
			'</td><td id="force_no" th:text="'+o.forceName+'">' + o.forceName +
			'</td><td id="irlaNumber" class="irlaNumberCls" value="'+o.forcePersonalId+'">' + o.irlaNumber +
			'</td><td id="personalName">' + o.name +

			'</td><td id="gender">' + o.gender +
			'</td><td  id="designation">' + o.designation +
			'</td><td>' + o.unitName +
			'<td> <button class="btn btn-info btn-sm" name="btnAddToList" onclick="addtoList(this)">Add</button></td>'
			'</td></tr>';
	});

	$('#forcePersonalDoctor').append(trHTML);
}



function createAttachUnitMedicalEmployeesTable(response){
	

	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr class="memberData">' +
			'</td><td id="force_no" th:text="'+o.forceName+'">' + o.forceName +
			'</td><td id="irlaNumber" class="irlaNumberCls" value="'+o.forcePersonalId+'">' + o.irlaNumber +
			'</td><td id="personalName">' + o.name +
			'</td><td id="gender">' + o.gender +
			'</td><td  id="designation">' + o.designation +
			'</td><td><span style="color: darkred;font-weight: 600;">(Original)</span> /' + o.unitName +'(Attach)'+o.attachUnit+
			'<td> <button class="btn btn-info btn-sm" name="btnAddToList" onclick="addtoList(this)">Add</button></td>'
			'</td></tr>';
	});

	$('#forcePersonalDoctor').append(trHTML);
}


function createUnit(data){
			       const dropdown = document.getElementById("unit_Id");
						dropdown.innerHTML = "";
						const option = document.createElement("option");
						option.text = 'Select';
						option.value = 0;
						// !-- option.setAttribute("data", data[i].forceId)-->
						dropdown.appendChild(option);
						for (let i = 0; i < data.length; i++) {
							const option = document.createElement("option");
							option.text = data[i].unitName;
							option.value = data[i].unitId;
							// !-- option.setAttribute("data", data[i].forceId)-->
							dropdown.appendChild(option);
						}
}
	
	function createAttachUnit(data){
			       const dropdown = document.getElementById("attach_unit");
						dropdown.innerHTML = "";
						const option = document.createElement("option");
						option.text = 'Select';
						option.value = 0;
						// !-- option.setAttribute("data", data[i].forceId)-->
						dropdown.appendChild(option);
						for (let i = 0; i < data.length; i++) {
							const option = document.createElement("option");
							option.text = data[i].unitName;
							option.value = data[i].unitId;
							// !-- option.setAttribute("data", data[i].forceId)-->
							dropdown.appendChild(option);
						}
}
	
			
			