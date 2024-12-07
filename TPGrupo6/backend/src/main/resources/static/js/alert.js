function showCustomAlert(message, type) {
    const alert = document.getElementById('customAlert');
    const alertMessage = document.getElementById('alertMessage');

    alertMessage.textContent = message;
    alert.classList.add(type);
    alert.style.display = 'block';

    document.getElementById('closeAlert').onclick = function() {
        alert.style.display = 'none';
    };

    setTimeout(() => {
        alert.style.display = 'none';
    }, 5000);
}
