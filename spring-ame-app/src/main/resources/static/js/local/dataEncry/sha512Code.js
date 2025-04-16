function calculateSHA512() {
  var inputText = document.getElementById('inputText').value;
  
  if (!inputText) {
    alert('Please enter some text.');
    return;
  }
  
  sha512(inputText).then(function(sha512hash) {
    document.getElementById('hashResult').textContent = "SHA-512 Hash: " + sha512hash;
  }).catch(function(error) {
    console.error('Error calculating SHA-512 hash:', error);
    alert('Error calculating SHA-512 hash. Please check console for details.');
  });
}

function sha512(input) {
  var crypto = window.crypto || window.msCrypto; // for IE 11 support
  var buffer = new TextEncoder('utf-8').encode(input);
  return crypto.subtle.digest('SHA-512', buffer).then(function(hash) {
    return Array.prototype.map.call(new Uint8Array(hash), function(x) {
      return ('00' + x.toString(16)).slice(-2);
    }).join('');
  });
}