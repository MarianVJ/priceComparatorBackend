package com.example.priceComparatorBackend.data;

import com.example.priceComparatorBackend.entity.*;
import com.example.priceComparatorBackend.service.*;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

@Component
public class DataInitializer implements CommandLineRunner {

    private BrandService brandService;
    private CategoryService categoryService;
    private StoreService storeService;
    private StoreDateBatchService storeDateBatchService;
    private ProductService productService;
    private StoreProductService storeProductService;

    @Autowired
    public DataInitializer(StoreProductService storeProductservice, ProductService productService, StoreService storeService, CategoryService categoryService, BrandService brandService, StoreDateBatchService theStoreDateBatchService) {
        this.storeProductService = storeProductservice;
        this.productService = productService;
        this.storeService = storeService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.storeDateBatchService = theStoreDateBatchService;
    }



    public void run(String... args) throws Exception{

        String pathPrefix = "src/main/resources/data/prices/";

        String[] fileNames = {
                "lidl_2025-05-01.csv",
                "kaufland_2025-05-01.csv",
                "profi_2025-05-01.csv",
                "lidl_2025-05-08.csv",
                "kaufland_2025-05-08.csv",
                "profi_2025-05-08.csv",
        };

        String[] storeNames = {
        "lidl",
                "kaufland",
                "profi",   "lidl",
                "kaufland",
                "profi"
        };

        String[] shopDateBatches = {"2025-05-01","2025-05-01", "2025-05-01","2025-05-08","2025-05-08" ,"2025-05-08"};
        for(int i = 0 ; i < fileNames.length;i ++){
            read(new File(pathPrefix + fileNames[i]), storeNames[i],shopDateBatches[i]);
        }



    }


    public void read(File file, String store_name, String dateBatch) throws Exception {


        Store storeObject = storeService.findByName(store_name).orElse(null);
        if(storeObject == null) {
            storeObject = new Store(store_name);
            storeService.save(storeObject);
        }

        LocalDate date = LocalDate.parse(dateBatch);
        StoreDateBatch storeDateBatchObject = new StoreDateBatch(storeObject, date);
        storeDateBatchService.save(storeDateBatchObject);


        String [] record;
        CSVReader csvReader = null;



        try {
            /*
                Build the reader with custom separator. Default is ','
                The default quote character is '"', if you want to use custom, use withQuoteChar(char)
                For example withQuoteChar('\'')
            */
            csvReader = new CSVReaderBuilder(new FileReader(file))
                    .withCSVParser(new CSVParserBuilder()
                            .withSeparator(';')
                            .build())
                    .build();

            int currentLine = 0;

            //Read first line that is the name of the columns
            record = csvReader.readNext();

            while ((record = csvReader.readNext()) != null) {


                addNewProductInDatabase(record, storeObject, storeDateBatchObject);

                currentLine++;
            }
        } finally {
            //Close the reader
            if (csvReader != null) {
                csvReader.close();
            }
        }

    }


    public void addNewProductInDatabase(String[] productFields, Store storeObject, StoreDateBatch storeDateBatch) {
        Long product_id;
        String product_name, product_category, brand, package_unit, currency;
        Double package_quantity, price;

        product_id = Long.parseLong(productFields[0].replaceAll("\\D", ""));
        product_name = productFields[1];
        product_category = productFields[2];
        brand = productFields[3];
        package_quantity = Double.parseDouble(productFields[4]);
        package_unit = productFields[5];
        price = Double.parseDouble(productFields[6]);
        currency = productFields[7];

        Brand brandObject = brandService.findByName(brand).orElse(null);
        if(brandObject == null){
            brandObject = new Brand(brand);
            brandService.save(brandObject);
        }


        Category categoryObject = categoryService.findByName(product_category).orElse(null);
        if(categoryObject == null){
            categoryObject = new Category(product_category);
            categoryService.save(categoryObject);
        }




        Product productObject;
        if(productService.existsById(product_id) == false){
            productObject = new Product(product_id,categoryObject,brandObject,package_unit,package_quantity,product_name);
            productService.save(productObject);
        }else {
            // todo
            productObject = productService.findById(product_id);
        }

//
//
        StoreProduct storeProductObject = new StoreProduct(storeDateBatch,productObject,price, currency);
        storeProductService.save(storeProductObject);

        System.out.println("Product ID: " + product_id +
                ", Name: " + product_name +
                ", Price: " + price +
                " " + currency +
                ", Category: " + product_category +
                ", Brand: " + brand +
                ", Package: " + package_quantity + " " + package_unit);
    }
}