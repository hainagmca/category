package org.dell.kube.category;

import ch.qos.logback.classic.Logger;
import org.dell.kube.category.data.Category;
import org.dell.kube.category.data.CategoryRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    Logger logger = (Logger)LoggerFactory.getLogger(CategoryController.class);
    @Autowired
    CategoryRepository repository;

    @PostMapping
    public ResponseEntity add(@RequestBody Category category){
        repository.save(category);
        logger.debug("Category created "+category);
        return new ResponseEntity(category, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity getAll(){
        return new ResponseEntity(repository.findAll(),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity find(@PathVariable Long id){
        Optional<Category> category = repository.findById(id);
        if(category.isPresent()){
            return new ResponseEntity(category.get(),HttpStatus.OK);
        }else {
            return new ResponseEntity("No Category Available",HttpStatus.NOT_FOUND);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id,@RequestBody Category category){
        if(repository.existsById(id)){
            category.setId(id);
            repository.save(category);
            return new ResponseEntity(category,HttpStatus.OK);
        }else {
            return new ResponseEntity("Category Not Available",HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id){
        repository.deleteById(id);
        return "Category Deleted";
    }
}