package com.example.priceComparatorBackend.entity;

import com.example.priceComparatorBackend.dao.database.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryTest {

    private final CategoryRepository categoryRepository;

    private Category category;

    @Autowired
    public CategoryTest(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @BeforeEach
    public void setUp() {
        category = new Category("Lactate");
    }

    @Test
    public void testCategoryEntityMapping() {
        Category saved = categoryRepository.save(category);
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
        categoryRepository.save(category);
        categoryRepository.delete(category);
        assertThat(categoryRepository.findById(category.getId())).isEmpty();
    }

    @Test
    public void testUniqueNameConstraint() {
        categoryRepository.save(category);
        Category duplicate = new Category("Lactate");
        try {
            categoryRepository.saveAndFlush(duplicate);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(DataIntegrityViolationException.class);
        }
    }
}
