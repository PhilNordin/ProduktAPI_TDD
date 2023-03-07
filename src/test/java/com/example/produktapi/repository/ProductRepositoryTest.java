package com.example.produktapi.repository;

import com.example.produktapi.model.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Testar i databasen, mot databasen
class ProductRepositoryTest {
    @Autowired // en liten constructor av product
    private ProductRepository undertest;

    @Test
    void testingOurRepository(){
        List <Product> products = undertest.findAll();
        Assertions.assertFalse(products.isEmpty()); //True = fel
    }

    @Test //find by category test 1 - testar söka efter kategori "balls" och om den är empty = false
    void findByCategory_givenValidCategory_whenSearchingInFindByCategory_thenReturnProductsInSpecificCategory() {

        //given
        Product product = new Product("fotboll", 100.0, "balls", "rund","bild");
        undertest.save(product);

        //when
        List <Product> productList = undertest.findByCategory("balls");
        //then
        assertFalse(productList.isEmpty()); // assertTrue för fail

    }

    @Test  //find by category test 2 - testar söka efter kategori som inte finns pga .deleteAll();
    void findByNonExistingCategory_givenDeleteAll_whenFindByCategory_thenCheckCategoryIsEmpty(){
        //given
        undertest.deleteAll();
        //when
        List <Product> listProduct = undertest.findByCategory("electronics");
        //then
        assertTrue(listProduct.isEmpty()); //assertFalse för fail
    }

    @Test
    void findByTitle_givenValidTitle_whenSearchingForANonExistingTitle_thenReturnThatProduct(){
        //given
        String title = "ipad";
        Product product = new Product(title,200.0, "electronics","","");
        undertest.save(product);

        //when
        Optional<Product> optionalProduct = undertest.findByTitle("ipad");

        //then
        Assertions.assertAll(
                () -> assertTrue(optionalProduct.isPresent()),
                () -> assertFalse(optionalProduct.isEmpty()),
                () -> assertEquals(product.getTitle(),optionalProduct.get().getTitle())
        );
    }

    @Test
    void findByTitle_whenSearchingForANonExistingTitle_thenReturnEmptyProducts() {
        //given
        String title = "Ipod mini";

        //when
        Optional <Product> optionalProduct = undertest.findByTitle(title);

        //then
        Assertions.assertAll(
                () -> assertFalse(optionalProduct.isPresent()),
                () -> assertTrue(optionalProduct.isEmpty()),
                () -> assertThrows(NoSuchElementException.class, ()-> optionalProduct.get(),"skicka eget felmeddelande om det blir fel")
        );

    }

    @Test
    void findAllCategories_whenSearchingfForFindAllCategories_thenReturnAllFourCategorys() {
        //when
        List <String> listProduct = undertest.findAllCategories();
        //then
        assertFalse(listProduct.isEmpty());
        assertEquals(listProduct.size(),4);
    }
    @Test
    void findNoDuplicatedCategory_givenListOfValidCategoriesAndRestrictDuplicates_whenUsingFindAllCategories_thenReturnAllFourCategorys(){

        //given
        List <String> actualCategories = new ArrayList<>(Arrays.asList("electronics","jewelery", "men's clothing", "women's clothing")); //fail electornics
        actualCategories.stream().distinct().collect(Collectors.toList());
        //when
        List <String> listProducts = undertest.findAllCategories();
        //then
        assertTrue(listProducts.size()==4);  //Kollar antalet kateforier - fail annat än 4
        assertEquals(actualCategories,listProducts); //kollar om categories är duplicerade

    }
}