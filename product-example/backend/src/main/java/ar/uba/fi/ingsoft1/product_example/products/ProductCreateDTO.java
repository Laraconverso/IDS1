package ar.uba.fi.ingsoft1.product_example.products;

import ar.uba.fi.ingsoft1.product_example.brands.Brand;
import lombok.NonNull;

import java.util.function.LongFunction;

record ProductCreateDTO(
        @NonNull String name,
        @NonNull String description,
        long brandId
) {
    public Product asProduct(LongFunction<Brand> getBrand) {
        return new Product(name, description, getBrand.apply(brandId));
    }
}
