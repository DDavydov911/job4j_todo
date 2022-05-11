package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.time.LocalDateTime;

@Controller
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/index")
    public String index(Model model) {
        model.addAttribute("items", itemService.getAllItems());
        return "index";
    }

    @GetMapping("/addItem")
    public String getAddItemPage() {
        return "addItemPage";
    }

    @PostMapping("/addItem")
    public String addItem(@RequestParam String description) {
        Item item = new Item(description);
        itemService.addItem(item);
        return "redirect:/index";
    }

    @GetMapping("/doneItems")
    public String getDoneItemsPage(Model model) {
        model.addAttribute("items", itemService.getAllDoneItems());
        return "index";
    }

    @GetMapping("/newItems")
    public String getNewItemsPage(Model model) {
        model.addAttribute("items", itemService.getAllNewItems());
        return "index";
    }

    @GetMapping("/anItem/{itemId}")
    public String getItemById(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findItemById(id));
        return "anItem";
    }

    @PostMapping("/doneItem")
    public String done(@RequestParam int id) {
        itemService.doneItemById(id);
        return "redirect:/index";
    }

    @GetMapping("/updateItem/{itemId}")
    public String getUpdateItem(Model model, @PathVariable int itemId) {
        model.addAttribute("item", itemService.findItemById(itemId));
        return "itemUpdateForm";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item) {
        itemService.update(item);
        return "redirect:/anItem/" + item.getId();
    }

    @PostMapping("/deleteItem")
    public String deleteItem(@RequestParam int id) {
        itemService.deleteItemById(id);
        return "redirect:/index";
    }
}
