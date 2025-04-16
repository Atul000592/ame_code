function validateFileSize() {

		var fileInput = document.getElementById('fileInput');
		var file = fileInput.files[0];
		var maxSize = 1048576; // 1 MB
		var x = document.getElementById("file_error_msg");

		var allowedFormats = ['pdf', 'docx'];

		if (file) {
			// Check file format
			var fileName = file.name;
			var fileExtension = fileName.split('.').pop().toLowerCase();
			if (!allowedFormats.includes(fileExtension)) {

				x.style.display = "block";
				x.innerHTML = 'Invalid file format. Please choose a file with one of the following formats: ' + allowedFormats.join(', ');


				fileInput.value = ''; // Clear the file input

				setTimeout(function () {
					x.style.display = 'none';
				}, 7000);
				return;
			}

			// Check file size
			if (file.size > maxSize) {
				x.style.display = "block";
				x.innerHTML='File size exceeds the allowed limit.Select file less than ' + (maxSize / ((1024 * 1024))+' MB hint* (10 kb > 1 mb)');
				fileInput.value = ''; // Clear the file input
				setTimeout(function () {
					x.style.display = 'none';
				}, 7000);
				return;
			}

			// Both format and size are valid
			
		}
	}
	
	
	
	
	
	
	
	
	
	
	function validateFileSizeAmeReport() {

		var fileInput = document.getElementById('fileInput');
		var file = fileInput.files[0];
		var maxSize = 5048576; // 5 MB
		var x = document.getElementById("file_error_msg");

		var allowedFormats = ['pdf'];

		if (file) {
			// Check file format
			var fileName = file.name;
			var fileExtension = fileName.split('.').pop().toLowerCase();
			if (!allowedFormats.includes(fileExtension)) {

				x.style.display = "block";
				x.innerHTML = 'Invalid file format. Please choose a file with one of the following formats: ' + allowedFormats.join(', ');


				fileInput.value = ''; // Clear the file input

				setTimeout(function () {
					x.style.display = 'none';
				}, 7000);
				return;
			}

			// Check file size
			if (file.size > maxSize) {
				x.style.display = "block";
				x.innerHTML='File size exceeds the allowed limit.Select file less than ' + (maxSize / ((1024 * 1024))+' MB hint* (10 kb > 1 mb)');
				fileInput.value = ''; // Clear the file input
				setTimeout(function () {
					x.style.display = 'none';
				}, 7000);
				return;
			}

			// Both format and size are valid
		}
	}