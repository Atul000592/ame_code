
 
    async function encryptPassword(password, key) {
        const encoder = new TextEncoder();
        const data = encoder.encode(password);
        const keyData = Uint8Array.from(atob(key), c => c.charCodeAt(0));
        const keyMaterial = await crypto.subtle.importKey(
            "raw",
            keyData,
            { name: "AES-GCM", length: 256 },
            true,
            ["encrypt"]
        );
        const iv = crypto.getRandomValues(new Uint8Array(12)); // Initialization vector
        const encrypted = await crypto.subtle.encrypt(
            { name: "AES-GCM", iv: iv },
            keyMaterial,
            data
        );

        return {
            iv: Array.from(iv),
            encrypted: Array.from(new Uint8Array(encrypted))
        };
    }

    async function submitForm() {
        const passwordField = document.getElementById('password');
        var key ;
        
        $.ajax({
					type: 'GET',
					url: '/getKey',
					success: function (data) {
                     key=data;

					},
					error: function (e) {



						console.log("ERROR : ", e);

					}
				});
        
        
        const encryptedData = await encryptPassword(passwordField.value, key);
        document.getElementById('encryptedPassword').value = JSON.stringify(encryptedData);
        // Now you can submit the form
        document.getElementById('yourForm').submit();
    }

