package com.example.priceComparatorBackend.dao.database;

import com.example.priceComparatorBackend.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CategoryRepositoryTest {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryRepositoryTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private Category category;

    @BeforeEach
    void setUp() {
        category = new Category("Lactate");
    }

    @Test
    public void testSaveCategory() {
        Category saved = categoryRepository.save(category);
        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Lactate");
    }

    @Test
    public void testFindByName() {
        categoryRepository.save(category);
        Optional<Category> found = categoryRepository.findByName("Lactate");
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("Lactate");
    }

    @Test
    public void testDeleteCategory() {
        Category saved = categoryRepository.save(category);
        categoryRepository.delete(saved);
        Optional<Category> deleted = categoryRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }

    @Test
    public void testUniqueNameConstraint() {
        categoryRepository.save(category);
        Category duplicate = new Category("Lactate");
        assertThatThrownBy(() -> categoryRepository.saveAndFlush(duplicate))
                .isInstanceOf(DataIntegrityViolationException.class);
    }
}
