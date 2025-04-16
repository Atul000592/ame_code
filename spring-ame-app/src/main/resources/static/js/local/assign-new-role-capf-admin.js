

$(document).ready(function() {
	$('#forceName_id').on('change', function() {
		var forceId = $(this).val();

		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type: 'Post',
			url: 'get-unit-by-forceNo',

			data:
			{
				forceId: forceId,
				
			},
			beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
			success: function(data) {
				createUnitTable(data);
				createAttachUnitTable(data);
			},
			error: function(e) {
				console.log("ERROR : ", e);

			}
		});
	});
});



//=======get forcePersonal details by unit==================//
$(document).ready(function() {
$('#unit_Id').on('change',
	function() {
		var unitId = $(this).val();
		var forceName_id = $("#forceName_id").val();
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");

	

			$.ajax({
				type: "post",
				url: "get-force-personal-by-unit-force-id",
				cache: false,
				dataType: 'json',

				data: ({ queryData: JSON.stringify({ unitNo: unitId, forceNo: forceName_id }) }),

				beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
				success: function(response) {
                 
					if(response.personalResponeseAjax.length ==0){
						
						
						document.getElementById("modal-body").innerHTML="no data found in the given unit";
						showModal();
						
					}else{
					
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
									return '<input type="button" class="btn btn-primary" value="Assign"  onclick="createNewRoleModelCapfAdmin(this)" id="' + data.forcePersonalId + '">';
									
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
				url: "get-force-personal-by-attach-unit-force-id",
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
														return '<input type="button" class="btn btn-primary" value="Assign" onclick="createNewRoleModelCapfAdmin(this)" id="' + data.forcePersonalId + '">';
														
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


//===================create table dynamically to fetch data==================//
function createForUnitTable(response) {
	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr  class="memberData" ><td>'
			+ (i + 1)
			+ '<input type="hidden" id="forcePersonalId" value="'
			+ o.forcePersonalId + '"'
			+ '</td><td><input type="hidden" id="forceNameId" value="' + o.forceName + '">'
			+ o.forceName
			+ '</td><td><input type="hidden"  id="irlaNoId" value="' + o.irlaNumber + '">'
			+ o.irlaNumber
			+ '</td><td><input type="hidden" id="nameId" value="' + o.name + '">'
			+ o.name
			+

			'</td><td><input type="hidden" id="unitNameId" value="' + o.unitName + '" >'
			+ o.unitName
			+ '</td><td>'
			+ ' <button class="btn btn-primary" name="btnAddToList" onclick="createNewRoleModelCapfAdmin(this)">Assign Role</button>'
			+ '</td></tr>';
	});

	$('#myTable').append(trHTML);

}
















//================================createForAttachUnitTable==================================================//

function createForAttachUnitTable(response) {
	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr  class="memberData" ><td>'
			+ (i + 1)
			+ '<input type="hidden" id="forcePersonalId" value="'
			+ o.forcePersonalId + '"'
			+ '</td><td><input type="hidden" id="forceNameId" value="' + o.forceName + '">'
			+ o.forceName
			+ '</td><td><input type="hidden"  id="irlaNoId" value="' + o.irlaNumber + '">'
			+ o.irlaNumber
			+ '</td><td><input type="hidden" id="nameId" value="' + o.name + '">'
			+ o.name
			+

			'</td><td><input type="hidden" id="unitNameId" value="' + o.unitName + ' " >'
			+ o.attachUnit + '<span style="color: darkred;font-weight: 600;">(Original)</span> / ' + o.unitName
			+ '<span style="color: darkblue;font-weight: 600;">(Attached Unit)</span> </td><td>'
			+ ' <button class="btnAddToList" name="btnAddToList" onclick="createNewRoleModelCapfAdmin(this)">Assign Role</button>'
			+ '</td></tr>';
	});

	$('#myTable').append(trHTML);

}























//========sending data to save =======//
$(document).ready(function() {
$('#membersubmit').click(
		function() {
			var formData = [];

			var table = $(tbodyfinal);
			console.log(table.children.length);
			var token = $("meta[name='_csrf']")
				.attr("content");
			var header = $(
				"meta[name='_csrf_header']")
				.attr("content");
			var rows = table.find('.memberData');
			if (rows.length != 0) {
				for (var i = 0; i < rows.length; i++) {
					var rowData = {};
					rowData['irlaNo'] = $(rows[i])
						.find('#irlaNoId')
						.val();
					rowData['name'] = $(rows[i])
						.find('#nameId').val();
					rowData['forceName'] = $(
						rows[i]).find(
							'#forceNameId').val();
					rowData['unitName'] = $(rows[i])
						.find('#unitNameId')
						.val();
					rowData['roleName'] = $(rows[i])
						.find(
							'#roleTypeSelect :selected')
						.val();
					rowData['forcePersonalId'] = $(
						rows[i]).find(
							"#forcePersonalId")
						.val();
					// set all data

					formData.push(rowData);
				}

				console.log(formData);
				var datan = "hello";
				var data1 = JSON
					.stringify(formData);
				//set value of committeeMemberData 
				//$('committeeMemberData').val(JSON.stringify(formData));

				//$('membersubmit').submit(

				$
					.ajax({
						type: "POST",
						contentType: "application/json",
						url: "get-new-role-to-user",
						data: data1,
						dataType: 'json',
						/* cache: false,
						timeout: 600000, */
						beforeSend: function(
							xhr) {

							xhr
								.setRequestHeader(
									header,
									token);
						},
						success: function(data) {
							alert(data.message);
							rows.remove();
							window.location.href = "assign-new-role-to-user";
						},
						error: function(e) {

							console.log(
								"ERROR : ",
								e);

						}
					});
			} else {
				alert("Please chose atleast one member in Board");
			}

		

		});});

function deleteFromList(e) {
	var row = $(e).closest('.memberData').remove();

}
function datechange(){
/*date disable future date picking*/
var today = new Date().toISOString().split('T')[0];
document.getElementById("date-input").setAttribute("max", today);
}

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

function showModal() {

      const modal = document.querySelector('#staticBackdrop');
      var modalInstance = new bootstrap.Modal(modal);
       modalInstance.show();

}