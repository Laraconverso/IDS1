async function buyNow() {
    try {
        // Obtener el ID del producto del botón que disparó el evento
        const productId = $(this).data('product-id');
        const productQuantity = $(`#quantityInput-${productId}`).val();

        // Obtener detalles del producto
        const product = await getProductDetails(productId);

        // Construir los detalles del producto
        const productDetails = `
            Producto: ${product.name}
            Precio: $${product.price}
            Marca: ${product.brand}
            Descripción: ${product.description}
            Cantidad: ${productQuantity}
        `;

        // Realizar pedido
        await makePedido([{ id: productId, cantidad: productQuantity }]);
        totalPrice = product.price * productQuantity
        // Enviar el correo con los detalles del producto
        sendPurchaseEmail(productDetails, totalPrice);
        setTimeout(() => {
            location.reload();
        }, 1500);
    } catch (error) {
        console.error('Error durante el proceso de compra:', error);
        showCustomAlert('Hubo un problema al procesar la compra.', 'error');
    }

}

function getProductDetails(productId) {
    return new Promise((resolve, reject) => {
        $.ajax({
            url: `/products/${productId}`,
            type: 'GET',
            success: function (product) {
                resolve(product); // Resolver la promesa con los detalles del producto
            },
            error: function (xhr, status, error) {
                console.error(`Error al obtener el producto con ID ${productId}:`, error);
                reject(error); // Rechazar la promesa si ocurre un error
            }
        });
    });
}

function makePedido(temporaryCart) {
    return new Promise((resolve, reject) => {
        let username = localStorage.getItem('username');
        if(username === null){
            reject()
            window.location.href = "/login";
            return;
        }
        // Crear los datos para la solicitud
        const requestData = {
            products: temporaryCart,
            estado: "CONFIRMADO",
            fecha: new Date().toISOString().replace('Z', ''),
            username: username
        };

        // Realizar la solicitud AJAX
        $.ajax({
            url: '/pedidos',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
            },
            success: function (response) {
                showCustomAlert('Pedido procesado con éxito.', 'success'); // Alerta de éxito
                resolve(response); // Resolver la promesa
            },
            error: function (xhr, status, error) {
                if (xhr.status === 418) {
                    // Extract the error message from the response
                    const errorMessage = xhr.responseText;

                    // Show the custom popup
                    handleFailedValidation(
                        `El pedido no puede concretarse por fallar la siguiente validación: ${errorMessage}`,
                        requestData
                    );
                } else if (xhr.status === 403) {
                    window.location.href = "/login";
                } else {
                    console.error('Error al procesar el pedido:', error);
                    showCustomAlert('Error al procesar el pedido.', 'danger'); // Alerta de error genérico
                }
            }
        });
    })
}

// Function to show the custom popup
function handleFailedValidation(message, requestData) {
    // Create the popup container
    const popupContainer = document.createElement('div');
    popupContainer.style.position = 'fixed';
    popupContainer.style.top = '0';
    popupContainer.style.left = '0';
    popupContainer.style.width = '100%';
    popupContainer.style.height = '100%';
    popupContainer.style.backgroundColor = 'rgba(0, 0, 0, 0.5)';
    popupContainer.style.display = 'flex';
    popupContainer.style.justifyContent = 'center';
    popupContainer.style.alignItems = 'center';
    popupContainer.style.zIndex = '9999';

    // Create the popup content
    const popupContent = document.createElement('div');
    popupContent.style.backgroundColor = 'white';
    popupContent.style.padding = '20px';
    popupContent.style.borderRadius = '8px';
    popupContent.style.boxShadow = '0 4px 8px rgba(0, 0, 0, 0.2)';
    popupContent.style.textAlign = 'center';

    // Add the message
    const messageElement = document.createElement('p');
    messageElement.textContent = message;
    messageElement.style.marginBottom = '20px';
    popupContent.appendChild(messageElement);

    // Add the "Cancelar" button
    const cancelButton = document.createElement('button');
    cancelButton.textContent = 'Cancelar';
    cancelButton.style.marginRight = '10px';
    cancelButton.style.padding = '10px 20px';
    cancelButton.style.border = 'none';
    cancelButton.style.borderRadius = '4px';
    cancelButton.style.backgroundColor = '#d9534f';
    cancelButton.style.color = 'white';
    cancelButton.style.cursor = 'pointer';
    cancelButton.onclick = function () {
        document.body.removeChild(popupContainer); // Remove the popup
    };
    popupContent.appendChild(cancelButton);

    // Add the "Dividir en varios pedidos" button
    const splitButton = document.createElement('button');
    splitButton.textContent = 'Dividir en varios pedidos';
    splitButton.style.padding = '10px 20px';
    splitButton.style.border = 'none';
    splitButton.style.borderRadius = '4px';
    splitButton.style.backgroundColor = '#5bc0de';
    splitButton.style.color = 'white';
    splitButton.style.cursor = 'pointer';
    splitButton.onclick = async function () {
        document.body.removeChild(popupContainer); // Remove the popup

        // Block for splitting the order logic
        for (let i = 0; i < requestData.products.length; i++) {
            for (let j = 0; j < requestData.products[i].cantidad; j++) {
                let data = JSON.parse(JSON.stringify(requestData)) // se clona para no modificar el original
                data.products = [({id: requestData.products[i].id, cantidad: 1})];
                await $.ajax({
                    url: '/pedidos',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem("accessToken")}`
                    },
                    success: function (response) {
                        showCustomAlert('Pedido procesado con éxito.', 'success'); // Alerta de éxito
                    },
                    error: function (xhr, status, error) {
                        if (xhr.status === 403) {
                            window.location.href = "/login";
                            return;
                        }
                        console.error('Error al procesar el pedido:', error);
                        showCustomAlert('Error al procesar el pedido.', 'danger'); // Alerta de error genérico
                    }
                });
            }
        }

    };
    popupContent.appendChild(splitButton);

    // Append the content to the container
    popupContainer.appendChild(popupContent);

    // Append the container to the body
    document.body.appendChild(popupContainer);
}

