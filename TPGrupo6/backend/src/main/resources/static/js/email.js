// Función para enviar el correo con los detalles del carrito
function sendPurchaseEmail(cartDetails, totalPrice) {
    console.log(cartDetails);
    const emailData = {
        to: localStorage.getItem("username"),
        subject: 'Confirmación de compra',
        text:
`Gracias por tu compra. Vas a poder encontrar el resumen a continuación:

      ${cartDetails}

El monto total de la compra realizada es de: $${totalPrice}
Saludos,
Grupo 6
        `
};

    $.ajax({
        url: '/send-email',
        method: 'POST',
        data: emailData,
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
        },
    });
}