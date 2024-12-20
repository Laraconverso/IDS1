package ar.uba.fi.ingsoft1.product_example.brands;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
class BrandService {

    private final BrandRepository brandRepository;

    public List<BrandDTO> getBrands() {
        return brandRepository.findAll().stream()
                .map(BrandDTO::new)
                .toList();
    }

    public BrandDTO createBrand(BrandCreateDTO data) {
        return new BrandDTO(brandRepository.save(data.asBrand()));
    }
}
