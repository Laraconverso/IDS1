function loadAllProducts() {
    $.ajax({
        url: '/products',
        method: 'GET',
        success: function(products) {
            const productList = $('#product-list');
            productList.empty();

            if (Array.isArray(products) && products.length > 0) {
                let hasAvailableProducts = false;

                products.forEach(product => {
                    if (product.cantidad > 0) {
                        const productCard = createProductCard(product);
                        productList.append(productCard);
                        hasAvailableProducts = true;
                    }
                });

                if (!hasAvailableProducts) {
                    productList.html('<p>No hay productos disponibles con stock.</p>');
                }
            } else {
                productList.html('<p>No hay productos disponibles.</p>');
            }
        },
        error: function(error) {
            console.error('Error al obtener los productos:', error);
            $('#product-list').html('<p>Error al obtener los productos.</p>');
        }
    });
}

function filterProducts() {
    const productName = $('#productSearchInput').val().trim();

    $.ajax({
        url: '/products',
        method: 'GET',
        success: function(products) {
            const productList = $('#product-list');
            const productDetail = $('#product-detail');
            productList.empty();
            productDetail.empty();

            const filteredProducts = products.filter(product =>
                product.name.toLowerCase().includes(productName.toLowerCase())
            );

            if (filteredProducts.length > 0) {
                filteredProducts.forEach(product => {
                    const productCard = createProductCard(product);
                    productList.append(productCard);
                });
            } else {
                productDetail.html('<p>No se encontraron productos con ese nombre.</p>');
            }
        },
        error: function(error) {
            console.error('Error al buscar productos:', error);
            $('#product-detail').html('<p>Error al buscar el producto.</p>');
        }
    });
}

function createProductCard(product) {
    return `
        <div class="col-md-4 product-card">
            <div class="card mb-4" style="display: flex; flex-direction: column;">
                <!-- Imagen y detalles del producto -->
                <div class="card-body" style="display: flex; flex-direction: column; height: 100%;">
                    <img src="/images/product.jpg" class="card-img-top" alt="${product.name}" style="max-width: 100%; height: auto;">

                    <div style="flex-grow: 1; display: flex; flex-direction: row; justify-content: space-between; margin-top: 10px;">
                        <div style="flex-grow: 1;">
                            <h5 class="card-title">${product.name}</h5>
                            <p class="card-text">${product.description}</p>
                            <p class="card-text"><strong>$${product.price}</strong></p>
                            <p class="card-text"><strong>Marca:</strong> ${product.brand}</p>
                            <p class="card-text"><strong>Stock Disponible:</strong> ${product.cantidad}</p>
                        </div>

                        <!-- Contador a la derecha con los botones de incremento y decremento -->
                        <div class="quantity-selector" style="display: flex; flex-direction: column; align-items: center;">
                            <button class="btn btn-secondary increment-btn" data-product-id="${product.id}" style="width: 40px;">+</button>
                            <input type="number"
                                   class="form-control quantity-input"
                                   id="quantityInput-${product.id}"
                                   value="1"
                                   min="1"
                                   max="${product.cantidad}"
                                   style="width: 60px; text-align: center; margin: 10px 0;"
                                   readonly />
                            <button class="btn btn-secondary decrement-btn" data-product-id="${product.id}" style="width: 40px;">-</button>
                        </div>
                    </div>
                </div>

                <!-- Botones de acción (en la parte inferior) -->
                <div class="button-container" style="padding: 10px;">
                    <button class="btn btn-success w-100" id="buyNowButton" data-product-id="${product.id}">Comprar Ahora</button>
                    <button class="btn btn-warning w-100" id="addToCartButton" data-product-id="${product.id}">Agregar al Carrito</button>
                </div>
            </div>
        </div>
    `;
}

$(document).on('click', '.increment-btn', function() {
    const inputElement = $(this).siblings('.quantity-input');
    let currentValue = parseInt(inputElement.val(), 10);
    const maxValue = parseInt(inputElement.attr('max'), 10);

    if (currentValue < maxValue) {
        inputElement.val(currentValue + 1);
    }
});

$(document).on('click', '.decrement-btn', function() {
    const inputElement = $(this).siblings('.quantity-input');
    let currentValue = parseInt(inputElement.val(), 10);
    const minValue = parseInt(inputElement.attr('min'), 10);

    if (currentValue > minValue) {
        inputElement.val(currentValue - 1);
    }
});




