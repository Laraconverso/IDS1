$(document).ready(function() {
    loadAllProducts();

    $('#productSearchInput').on('input', filterProducts);

    $(document).on('click', '#buyNowButton', buyNow);

    $(document).on('click', '#addToCartButton', addToCart);

    $('#cartButton').click(showCart);

});
