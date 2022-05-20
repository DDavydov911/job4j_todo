package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
public class ItemController {

    private final ItemService itemService;
    private final CategoryService categoryService;

    public ItemController(ItemService itemService, CategoryService categoryService) {
        this.itemService = itemService;
        this.categoryService = categoryService;
    }

    @GetMapping("/tasks")
    public String tasks(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.getAllItems(user));
        return "tasks";
    }

    @GetMapping("/addItem")
    public String getAddItemPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "addItemPage";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam String description,
                          @RequestParam Set<Integer> ids,
                          Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        Item item = new Item(description);
        item.setUser(user);
        Set<Category> categorySet = categoryService.findCategories(ids);
        item.setCategories(categorySet);
        itemService.addItem(item);
        return "redirect:/tasks";
    }

    @GetMapping("/doneItems")
    public String getDoneItemsPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.getAllDoneItems(user));
        return "tasks";
    }

    @GetMapping("/newItems")
    public String getNewItemsPage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("items", itemService.getAllNewItems(user));
        return "tasks";
    }

    @GetMapping("/anItem/{itemId}")
    public String getItemById(Model model, HttpSession session,
                              @PathVariable("itemId") int id) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        model.addAttribute("item", itemService.findItemById(id));
        return "anItem";
    }

    @PostMapping("/doneItem")
    public String done(@RequestParam int id,
                       Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        itemService.doneItemById(id);
        return "redirect:/tasks";
    }

    @GetMapping("/updateItem/{itemId}")
    public String getUpdateItem(Model model, @PathVariable int itemId) {
        Item item = itemService.findItemById(itemId);
        model.addAttribute("item", item);
        model.addAttribute("user", item.getUser());
        return "itemUpdateForm";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        Item itemDB = itemService.findItemById(item.getId());
        itemDB.setDescription(item.getDescription());
        itemService.update(itemDB);
        return "redirect:/tasks";
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam int id) {
        itemService.deleteItemById(id);
        return "redirect:/tasks";
    }
}
