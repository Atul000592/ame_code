 function viewDocument(button) {
        const documentPath = button.getAttribute('data-document-path');
        const encodedPath = encodeURIComponent(documentPath);

     

        // Open the modal
        new bootstrap.Modal(document.getElementById('pdfModal')).show();
    }