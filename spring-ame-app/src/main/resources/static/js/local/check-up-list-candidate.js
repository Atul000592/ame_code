function showCheckUpList(e) {
	var ameId = e.parentElement.elements.ameId.value;
	var forcepersonalId = e.parentElement.elements.forcepersonalId.value



	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	$.ajax({
		type: 'Post',
		url: 'get-candidate-check-up-list-by-ame-id',

		data: { ameId: ameId, forcepersonalId: forcepersonalId }, beforeSend: function(xhr) {

			xhr.setRequestHeader(header, token);
		},

		success: function(data) {

			createCheckuplist(data.checkUpList);
			$("#issueDate").text(data.issueDate);
			$("#ameIdId").text(data.ameId);
			$("#name").text(data.candidateDetails.name);
			$("#irlaNo").text(data.candidateDetails.forceId);
			$("#rankId").text(data.candidateDetails.designation);
			$("#unitId").text(data.candidateDetails.unitName);
			$("#forceNameId").text(data.candidateDetails.forceCodeName);
			$("#genderId").text(data.candidateDetails.gender);
		     var dob=formatDate(data.candidateDetails.dob);
			$("#dobId").text(dob);
			
			var modalElement = document.getElementById('scrollableModal');
			var modal = new bootstrap.Modal(modalElement, {
				backdrop: 'static',  // Disable clicking on the background to close the modal
				keyboard: false      // Disable closing the modal with the 'Esc' key
			});
			
			

			// Show the modal
			modal.show();


		},
		error: function() {


			alert('Error while request..........!');
		}

	});





}
function createCheckuplist(response) {
	
	 $('#checkupList').empty(); 
	var trHTML = '';
	$.each(response, function(i, o) {
		trHTML += '<tr>' +
		'<td>' + (i + 1) + '</td>' + // Add count here (i+1 because index starts at 0)
		'<td>' + o.testCode + '</td>' +
		'<td>' + o.testName + '</td>' +
		'</tr>';
	
	});
$('#checkupList').append(trHTML);
}

function formatDate(dateString) {
    var date = new Date(dateString);  // Parse the date string into a Date object
    
    // Get the components of the date
    var day = String(date.getDate()+1).padStart(2, '0');  // Add leading zero if single digit
    var month = String(date.getMonth() + 1).padStart(2, '0');  // getMonth() is zero-indexed (0 = January)
    var year = date.getFullYear();

    // Return the formatted date in DD-MM-YYYY format
    return day + '-' + month + '-' + year;
}
