package com.project.shopaap.controllers;

import com.project.shopaap.components.LocalizationUtil;
import com.project.shopaap.dtos.CategoryDTO;
import com.project.shopaap.models.Category;

import com.project.shopaap.respones.UpdateCategoryRespones;
import com.project.shopaap.services.CategoryService;
import com.project.shopaap.services.ICategoryService;
import com.project.shopaap.utils.MessageKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;


import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
//Dependency Injection

public class CategoriesController {
    private final ICategoryService categoryService;
    private final LocalizationUtil localizationUtil;


    @PostMapping("")
    //Nếu tham số truyền vào là  1 object => Data Stranfer Object = Request Object
    public ResponseEntity<UpdateCategoryRespones> createCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result){
        if(result.hasErrors()){

            List<String> errorMessage = result.getAllErrors().stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return  ResponseEntity.badRequest().body(UpdateCategoryRespones.builder()
                            .message(localizationUtil.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_FAILED,errorMessage))
                    .build());
        }
        categoryService.createCategory(categoryDTO);
        return ResponseEntity.ok(UpdateCategoryRespones.builder()
                        .message(localizationUtil.getLocalizedMessage(MessageKeys.INSERT_CATEGORY_SUCCESSFULLY))
                .build());
    }


    @GetMapping("")//http://localhost:8088/api/v1/categories?page=1&limit=10
    public ResponseEntity<List<Category>> getAllCategories(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        List<Category> categories=categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategoryRespones> updateCategory(@PathVariable Long id,
                                                                 @Valid @RequestBody CategoryDTO categoryDTO
                                                                 ){
        categoryService.updateCategory(id,categoryDTO);
        return ResponseEntity.ok(UpdateCategoryRespones.builder()
                        .message(localizationUtil.getLocalizedMessage(MessageKeys.UPDATE_CATEGORY_SUCCESSFUULY))
                .build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<UpdateCategoryRespones> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return ResponseEntity.ok(UpdateCategoryRespones.builder()
                        .message(localizationUtil.getLocalizedMessage(MessageKeys.DELETE_CATEGORY_SUCCESSFULLY))
                .build());
    }
}
