document.getElementById("uploadForm").onsubmit = function (event) {
  event.preventDefault();
  const formData = new FormData(this);

  fetch(this.action, {
    method: this.method,
    body: formData,
  })
    .then((response) => response.arrayBuffer())
    .then((buffer) => {
      // Convert buffer to base64 string to display as an image
      let binary = "";
      const bytes = new Uint8Array(buffer);
      const len = bytes.byteLength;
      for (let i = 0; i < len; i++) {
        binary += String.fromCharCode(bytes[i]);
      }
      const base64String = window.btoa(binary);

      // Display the image
      const img = document.getElementById("uploadedImage");
      img.src = "data:image/jpeg;base64," + base64String;
      img.style.display = "block";

      // Display the byte array in the response section
      document.getElementById("response").textContent = JSON.stringify(bytes);
    })
    .catch((error) => {
      document.getElementById("response").textContent = "Error: " + error;
    });
};
