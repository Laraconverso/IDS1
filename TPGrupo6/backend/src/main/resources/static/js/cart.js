
let temporaryCart = [];

function addToCart() {
    const productId = $(this).data('product-id');
    const productQuantity = parseInt($(`#quantityInput-${productId}`).val(), 10);

    const existingProductIndex = temporaryCart.findIndex(item => item.id === productId);
    if (existingProductIndex !== -1) {
        return;
    }

    temporaryCart.push({ id: productId, quantity: productQuantity });

    // Actualizamos el contenido del carrito antes de abrir el offcanvas
    showCart();

    const cartOffcanvas = new bootstrap.Offcanvas(document.getElementById('cartOffcanvas'));
    cartOffcanvas.show();
}



function showCart() {
    console.log("ShowCart");
    const cartElement = $('#cart');
    cartElement.empty();
    cartDetails = "";
    totalPrice = 0;

    if (temporaryCart.length === 0) {
        cartElement.append('<div>El carrito está vacío.</div>');
        return;
    }

    temporaryCart.forEach((item, index) => {
        const { id: productId, quantity: productQuantity } = item;

        // Obtener detalles del producto mediante AJAX
        $.ajax({
            url: `/products/${productId}`,
            type: 'GET',
            success: function (product) {
                cartElement.append(`
                    <div class="product-item" style="display: flex; align-items: center; gap: 15px; margin-bottom: 15px; position: relative;">
                        <img src="/images/product.jpg" alt="${product.name}" class="product-image" style="width:100px; height:auto; margin-bottom:10px;">
                        <div class="product-details" style="flex-grow: 1;">
                            <h4>${product.name}</h4>
                            <h5>$${product.price}</h5>
                            <p>${product.description}</p>
                            <p><strong>Cantidad:</strong> ${productQuantity}</p>
                        </div>
                        <button class="delete-product-btn" data-index="${index}" style="background: none; border: none; color: red; font-size: 18px; cursor: pointer;">
                            <i class="fas fa-trash-alt"></i>
                        </button>
                    </div>
                    <hr>
                `);
                cartDetails += `
                Producto: ${product.name}
                Precio: $${product.price}
                Marca: ${product.brand}
                Descripción: ${product.description}
                Cantidad: ${productQuantity}
                -------------------------------
                `;

                totalPrice += product.price*productQuantity;
            },
            error: function (xhr, status, error) {
                console.error(`Error al obtener el producto con ID ${productId}:`, error);
            }
        });
    });

    // Evitar agregar eventos duplicados
    cartElement.off('click', '.delete-product-btn'); // Elimina eventos antiguos

    cartElement.on('click', '.delete-product-btn', function() {
        const index = $(this).data('index');
        temporaryCart.splice(index, 1);
        showCart();
    });
}

$('#confirmPurchaseButton').on('click', async function() {
    console.log(temporaryCart);
    const cartForPurchase = temporaryCart.map(item => ({ id: item.id, cantidad: item.quantity }));
    await makePedido(cartForPurchase);
    sendPurchaseEmail(cartDetails, totalPrice);
    console.log(cartForPurchase);

    // Limpiar el carrito
    temporaryCart = [];
    showCart();

    const cartOffcanvas = bootstrap.Offcanvas.getInstance(document.getElementById('cartOffcanvas'));
    if (cartOffcanvas) {
        cartOffcanvas.hide();
    }
    setTimeout(() => {
        location.reload();
    }, 1500);
});




