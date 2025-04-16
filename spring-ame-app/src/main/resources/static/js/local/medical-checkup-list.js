function finalSubmitCheckUpList() {
    // Get all checkboxes
    const checkboxes = document.querySelectorAll('input[name="testValue"]');
    // Prepare an array to hold the data of checked rows
    const checkedRows = [];

    checkboxes.forEach(checkbox => {
        if (checkbox.checked) {
            // Find the parent row
            const row = checkbox.closest('tr');

            // Extract data from the row
            const name = row.querySelector('.testName').textContent;
            const code = row.querySelector('input[name="testCode"]').value;
            const ameId = row.querySelector('.ameId').value;

            // Store the data in the array
            checkedRows.push({
                name: name,
                code: code,
                ameId: ameId
            });
        }
    });

    // Get CSRF token and header name
    const token = $("meta[name='_csrf']").attr("content");
    const header = $("meta[name='_csrf_header']").attr("content");

    // Convert array to JSON string
    const dataToSend = JSON.stringify(checkedRows);

    // Send data using jQuery AJAX
    $.ajax({
        type: 'POST',
        url: 'final-submit-checkup-list', // Ensure this URL is correct
        contentType: 'application/json',
        data: dataToSend, // Use 'data' instead of 'body'
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token); // Set CSRF token header
        },
        success: function (response) {
			
			    $("#myForm").attr('action',response.uri);
	            $("#myForm").attr("method", "get"); 
	            $("#myForm").submit();
            console.log('Success:', response);
        },
        error: function (xhr, status, error) {
            console.error('Error:', error);
        }
    });

    // For demonstration: log the result to the console
    console.log(checkedRows);

    // You can use the data as needed, for example, send it to a server or update the UI
}
