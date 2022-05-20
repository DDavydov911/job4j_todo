package ru.job4j.todo.service;

import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.store.CategoryStore;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryStore categoryStore;

    public CategoryService(CategoryStore categoryStore) {
        this.categoryStore = categoryStore;
    }

    public Set<Category> findCategories(Set<Integer> ids) {
        return categoryStore.findCategories(ids);
    }

    public List<Category> getAllCategories() {
        return categoryStore.getAllCategories();
    }

    public Category findCategoryById(int id) {
        return categoryStore.findCategoryById(id);
    }

    public Optional<Set<Category>> add(Set<Category> categorySet) {
        return categoryStore.add(categorySet);
    }

}
