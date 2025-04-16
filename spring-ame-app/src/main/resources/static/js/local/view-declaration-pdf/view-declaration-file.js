function view_declaration_file_pdf(e){

	var candidateForcePersonnelId=e.parentElement[0].defaultValue;
	var ameId=e.parentElement[1].defaultValue;
	console.log("candidateForcePersonnelId "+candidateForcePersonnelId+"ameId "+ameId);
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type: 'Post',
			url: 'get-file-data-path-declaration',

			data:
			{
				candidateForcePersonnelId: candidateForcePersonnelId,
				ameId:ameId
				
			},
			beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
			success: function(data) {
				console.log(data);
				const encodedPath = encodeURIComponent(data.filePath);
           
               document.getElementById("fileName").innerText=data.fileName;
             document.getElementById("pdfIframe").src = "/ViewDocuments?documentPath=" + encodedPath;

        // Open the modal
        new bootstrap.Modal(document.getElementById('pdfModal')).show();
			},
			error: function(e) {
				console.log("ERROR : ", e);

			}
		});
	
	
}


function view_ame_final_file_pdf(e){

	var candidateForcePersonnelId=e.parentElement[0].defaultValue;
	var ameId=e.parentElement[1].defaultValue;
	console.log("candidateForcePersonnelId "+candidateForcePersonnelId+"ameId "+ameId);
		var token = $("meta[name='_csrf']").attr("content");
		var header = $("meta[name='_csrf_header']").attr("content");
		$.ajax({
			type: 'Post',
			url: 'get-file-data-path-ame-final-report',

			data:
			{
				candidateForcePersonnelId: candidateForcePersonnelId,
				ameId:ameId
				
			},
			beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
			success: function(data) {
				console.log(data);
				const encodedPath = encodeURIComponent(data.filePath);
             
               document.getElementById("fileName").innerText=data.fileName;
               document.getElementById("pdfIframe").src = "/view-ame-final-pdf?documentPath=" + encodedPath;

        // Open the modal
        new bootstrap.Modal(document.getElementById('pdfModal')).show();
			},
			error: function(e) {
				console.log("ERROR : ", e);

			}
		});
	
	
}
	 

