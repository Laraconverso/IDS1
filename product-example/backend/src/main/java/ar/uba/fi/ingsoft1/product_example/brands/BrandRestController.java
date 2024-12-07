package ar.uba.fi.ingsoft1.product_example.brands;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/brands")
@Validated
@RequiredArgsConstructor
class BrandRestController {
    private final BrandService brandService;

    @GetMapping
    public List<BrandDTO> getBrands() {
        return brandService.getBrands();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO createBrand(
            @NonNull @RequestBody BrandCreateDTO data
    ) {
        return brandService.createBrand(data);
    }
}
