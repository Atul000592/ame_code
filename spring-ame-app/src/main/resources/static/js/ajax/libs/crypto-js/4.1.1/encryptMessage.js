/*function loginMain()
 {

            const message = document.getElementById('passwordId').value;
           
           
            const key = document.getElementById('hexKey').value;
            const iv = document.getElementById('ivHex').value;

            const secretKey = CryptoJS.enc.Hex.parse(key);
            const ivParam = CryptoJS.enc.Hex.parse(iv);

            const encrypted = CryptoJS.AES.encrypt(message, secretKey, { iv: ivParam });
            const encryptedMessage = encrypted.ciphertext.toString(CryptoJS.enc.Base64);

            
             document.getElementById('passwordId').value=encryptedMessage;
      
            // var form = document.getElementById('loginForm');
            document.getElementById("loginForm").submit();
            
            }*/
		/*	
			async function sha256WithSalt(message, salt) {
			         const encoder = new TextEncoder();
			         const data = encoder.encode(salt + message);
			         const hashBuffer = await crypto.subtle.digest('SHA-256', data);
			         const hashArray = Array.from(new Uint8Array(hashBuffer));
			         const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');
			         return hashHex;
			     }*/
		
 function getPassword(){
	
	const message=document.getElementById('passwordId').value;
	const salt=document.getElementById('salt').value;
	const randomKey=document.getElementById('randomKey').value;
	const randomId=document.getElementById('randomId').value;
     
	
	//const hash = await sha256WithSalt(message, salt);
	 var hashedData = sha256WithSalt(message, salt);
	document.getElementById('passwordId').value=hashedData+randomKey+randomId;
	document.getElementById("loginform").submit();
	


	
}			
			
 function sha256WithSalt(data, salt) {
            // Combine the data with the salt
            var combinedData = data + salt;

            // Generate the SHA-256 hash
            var hash = CryptoJS.SHA256(combinedData);

            // Convert the hash to a string
            return hash.toString(CryptoJS.enc.Hex);
        }			

			
			
			
			
			
            
            
            
            function resetPassword()
 {

            const message = document.getElementById('passwordId').value;
            const messageReType = document.getElementById('passwordIdReType').value;
            passwordIdReType
            const key = document.getElementById('hexKey').value;
            const iv = document.getElementById('ivHex').value;

            const secretKey = CryptoJS.enc.Hex.parse(key);
            const ivParam = CryptoJS.enc.Hex.parse(iv);

            const encrypted = CryptoJS.AES.encrypt(message, secretKey, { iv: ivParam });
            const encryptedReTypePassword = CryptoJS.AES.encrypt(messageReType, secretKey, { iv: ivParam });
            const encryptedMessage = encrypted.ciphertext.toString(CryptoJS.enc.Base64);
            const encryptedReTypeMessage = encryptedReTypePassword.ciphertext.toString(CryptoJS.enc.Base64);
            
             document.getElementById('passwordId').value=encryptedMessage;
             document.getElementById('passwordIdReType').value=encryptedReTypeMessage;
            // var form = document.getElementById('loginForm');
            validatePassword();

            // Submit the form
           document.getElementById("resetform").submit();
            
            }

           // document.getElementById('encryptedOutput').innerText = "Encrypted message: " + encryptedMessage;
           
           function encryptedValue(plainText)
 {

            const message = plainText;
           
           
            const key = document.getElementById('hexKey').value;
            const iv = document.getElementById('ivHex').value;

            const secretKey = CryptoJS.enc.Hex.parse(key);
            const ivParam = CryptoJS.enc.Hex.parse(iv);

            const encrypted = CryptoJS.AES.encrypt(message, secretKey, { iv: ivParam });
            const encryptedMessage = encrypted.ciphertext.toString(CryptoJS.enc.Base64);

            return encryptedMessage;
  
            
            }
            