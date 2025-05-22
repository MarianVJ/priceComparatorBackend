package com.example.priceComparatorBackend.data;

import com.example.priceComparatorBackend.entity.*;
import com.example.priceComparatorBackend.service.database.*;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BrandService brandService;
    private final CategoryService categoryService;
    private final StoreService storeService;
    private final StoreDateBatchService storeDateBatchService;
    private final ProductService productService;
    private final StoreDateBatchProductService storeDateBatchProductService;
    private final StoreDiscountDateBatchService storeDiscountDateBatchService;
    private final StoreDiscountDateBatchProductService
            storeDiscountDateBatchProductService;

    private final DataValidator dataValidator;
    private final Logger logger =
            LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    public DataInitializer(
            StoreDateBatchProductService storeDateBatchProductservice,
            ProductService productService,
            StoreService storeService,
            CategoryService categoryService,
            BrandService brandService,
            StoreDateBatchService storeDateBatchService,
            DataValidator dataValidator,
            StoreDiscountDateBatchService storeDiscountDateBatchService,
            StoreDiscountDateBatchProductService storeDiscountDateBatchProductService
    ) {
        this.storeDateBatchProductService = storeDateBatchProductservice;
        this.productService = productService;
        this.storeService = storeService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.storeDateBatchService = storeDateBatchService;
        this.dataValidator = dataValidator;
        this.storeDiscountDateBatchService = storeDiscountDateBatchService;
        this.storeDiscountDateBatchProductService =
                storeDiscountDateBatchProductService;
    }


    public void run(String... args) {


        String pricesPathPrefix = "src/main/resources/data/prices/";
        String discountsPathPrefix = "src/main/resources/data/discounts/";

        // Populate the tables that store information about prices of the
        // products
        if (storeDateBatchProductService.count() == 0) {

            Path folderPath = Paths.get(pricesPathPrefix);
            try (Stream<Path> files = Files.list(folderPath)) {
                files.filter(Files::isRegularFile).forEach(file -> {
                    // Extract data from each *.csv file
                    String fileName = file.getFileName().toString();

                    try {
                        // Parse and read metadata from the name of the file
                        String[] parts = fileName.split("_");
                        String storeName = parts[0];
                        String date = parts[1].replace(".csv", "");

                        // Read each line, parse it, and populate the database
                        processProductPricesCsvFile(
                                new File(pricesPathPrefix + fileName),
                                storeName, date);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info(
                    "The tables was populated with data on a previous run of " +
                            "the app");
        }


        // Populate the tables that store information about discounts of the
        // products
        if (storeDiscountDateBatchProductService.count() == 0) {

            Path folderPath = Paths.get(discountsPathPrefix);
            try (Stream<Path> files = Files.list(folderPath)) {
                files.filter(Files::isRegularFile).forEach(file -> {
                    // Extract data from each *.csv file
                    String fileName = file.getFileName().toString();

                    try {
                        // Parse and read metadata from the name of the file
                        String[] parts = fileName.split("_");
                        String storeName = parts[0];


                        // Read each line, parse it, and populate the database
                        processProductDiscountsCsvFile(
                                new File(discountsPathPrefix + fileName),
                                storeName);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            logger.info(
                    "The tables was populated with data on a previous run of " +
                            "the app");
        }
    }


    public void processProductPricesCsvFile(File file, String store_name,
                                            String dateBatch) throws Exception {


        if (!dataValidator.isTextFieldValid(store_name) ||
                !dataValidator.isDateValid(dateBatch)) {
            logger.error("The store_name or dateBatch is not Valid");
            return;
        }

        Store store = getOrCreateStore(store_name);
        StoreDateBatch storeDateBatch =
                createAndSaveStoreDateBatch(store, dateBatch);

        String[] record;
        CSVReader csvReader = null;

        try {
            // Custom separator for each value is ';'
            csvReader =
                    new CSVReaderBuilder(new FileReader(file)).withCSVParser(
                                    new CSVParserBuilder().withSeparator(';').build())
                            .build();

            int currentLine = 0;

            //Read first line that is the name of the columns
            record = csvReader.readNext();

            while ((record = csvReader.readNext()) != null) {
                saveProductToDatabase(record, store,
                        storeDateBatch);
                currentLine++;
            }
        } finally {
            //Close the reader
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }

    public void processProductDiscountsCsvFile(File file, String store_name)
            throws Exception {


        if (!dataValidator.isTextFieldValid(store_name)) {
            logger.error("The store_name is not Valid");
            return;
        }

        Store store = getOrCreateStore(store_name);

        String[] record;
        CSVReader csvReader = null;

        try {
            // Custom separator for each value is ';'
            csvReader =
                    new CSVReaderBuilder(new FileReader(file)).withCSVParser(
                                    new CSVParserBuilder().withSeparator(';').build())
                            .build();

            int currentLine = 0;

            //Read first line that is the name of the columns
            record = csvReader.readNext();

            while ((record = csvReader.readNext()) != null) {
                saveDiscountToDatabase(record, store);
                currentLine++;
            }
        } finally {
            //Close the reader
            if (csvReader != null) {
                csvReader.close();
            }
        }
    }

    public void saveProductToDatabase(String[] productFields, Store storeObject,
                                      StoreDateBatch storeDateBatch) {
        Long productId;
        String productName, productCategory, brandName, packageUnit, currency;
        Double packageQuantity, price;

        productId = Long.parseLong(productFields[0].replaceAll("\\D", ""));
        productName = productFields[1];
        productCategory = productFields[2];
        brandName = productFields[3];
        packageQuantity = Double.parseDouble(productFields[4]);
        packageUnit = productFields[5];
        price = Double.parseDouble(productFields[6]);
        currency = productFields[7];

        if (!dataValidator.isTextFieldValid(productName) ||
                !dataValidator.isTextFieldValid(productCategory) ||
                !dataValidator.isTextFieldValid(brandName) ||
                !dataValidator.isPositiveNumber(packageQuantity) ||
                !dataValidator.isPositiveNumber(price) ||
                !dataValidator.isTextFieldValid(packageUnit) ||
                !dataValidator.isTextFieldValid(currency)) {

            logger.error("One of the values from the " +
                    "following  list{productName, productCategory, brand, " +
                    "packageQuantity, price, packageUnit, currency} is not " +
                    "Valid");
            return;
        }

        Brand brand = getOrCreateBatch(brandName);

        Category category = getOrCreateCategory(productCategory);

        Product product = getOrCreateProduct(productId, category, brand,
                packageUnit, packageQuantity, productName);

        StoreDateBatchProduct storeDateBatchProduct =
                createAndSaveStoreProduct(storeDateBatch,
                        product, price, currency);


        System.out.println(
                "Product ID: " + productId + ", Name: " + productName +
                        ", Price: " + price + " " + currency + ", Category: " +
                        productCategory + ", Brand: " + brandName +
                        ", Package: " +
                        packageQuantity + " " + packageUnit);
    }


    public void saveDiscountToDatabase(String[] productFields,
                                       Store store) {
        Long productId;
        String productName, productCategory, brandName, packageUnit, fromDate,
                toDate;
        Double packageQuantity, percentageOfDiscount;

        productId = Long.parseLong(productFields[0].replaceAll("\\D", ""));
        productName = productFields[1];
        brandName = productFields[2];
        packageQuantity = Double.parseDouble(productFields[3]);
        packageUnit = productFields[4];
        productCategory = productFields[5];
        fromDate = productFields[6];
        toDate = productFields[7];
        percentageOfDiscount = Double.parseDouble(productFields[8]);


        if (!dataValidator.isTextFieldValid(productName) ||
                !dataValidator.isTextFieldValid(productCategory) ||
                !dataValidator.isTextFieldValid(brandName) ||
                !dataValidator.isPositiveNumber(packageQuantity) ||
                !dataValidator.isTextFieldValid(packageUnit) ||
                !dataValidator.isDateValid(fromDate) ||
                !dataValidator.isDateValid(toDate) ||
                !dataValidator.isPositiveNumber(percentageOfDiscount)) {


            logger.error("One of the values from the " +
                    "following  list{productName, productCategory, brand, " +
                    "packageQuantity, packageUnit, fromDate, toDate, " +
                    "percentageOfDiscount} is not Valid");
            return;
        }


        Brand brand = getOrCreateBatch(brandName);

        Category category = getOrCreateCategory(productCategory);

        Product product = getOrCreateProduct(productId, category, brand,
                packageUnit, packageQuantity, productName);

        StoreDiscountDateBatch storeDiscountDateBatch =
                createAndSaveStoreDiscountDateBatch(store, fromDate, toDate);

        StoreDiscountDateBatchProduct storeDiscountDateBatchProduct =
                createAndSaveStoreDiscountDateBatchProduct(
                        storeDiscountDateBatch, product, percentageOfDiscount);

    }


    // Auxiliary function to retrieve an existing store or create a new one if not found.
    public Store getOrCreateStore(String store_name) {
        Store store = storeService.findByName(store_name).orElse(null);
        if (store == null) {
            store = new Store(store_name);
            storeService.save(store);
        }
        return store;
    }

    public StoreDateBatch createAndSaveStoreDateBatch(Store store,
                                                      String dateBatch) {
        LocalDate date = LocalDate.parse(dateBatch);
        StoreDateBatch storeDateBatch = new StoreDateBatch(store, date);
        return storeDateBatchService.save(storeDateBatch);
    }

    public Brand getOrCreateBatch(String brandName) {
        Brand brand = brandService.findByName(brandName).orElse(null);
        if (brand == null) {
            brand = new Brand(brandName);
            brandService.save(brand);
        }
        return brand;
    }

    private Category getOrCreateCategory(String productCategory) {
        Category category =
                categoryService.findByName(productCategory).orElse(null);
        if (category == null) {
            category = new Category(productCategory);
            categoryService.save(category);
        }
        return category;
    }

    private Product getOrCreateProduct(Long productId, Category category,
                                       Brand brand, String packageUnit,
                                       Double packageQuantity,
                                       String productName) {
        Product product;
        if (!productService.existsById(productId)) {
            product = new Product(productId, category, brand,
                    packageUnit, packageQuantity, productName);
            productService.save(product);
        } else {
            product = productService.findById(productId);
            if (product.getBrand().getId() != brand.getId() ||
                    product.getCategory().getId() != category.getId() ||
                    !product.getPackageUnit().equals(packageUnit) ||
                    product.getPackageQuantity() != packageQuantity ||
                    !product.getName().equals(productName)
            ) {
                logger.error("One of the data introduced for the product with" +
                        " id: " + productId + " was different from the value " +
                        "associated for it in database");
            }
        }
        return product;
    }

    private StoreDateBatchProduct createAndSaveStoreProduct(
            StoreDateBatch storeDateBatch, Product product, Double price,
            String currency) {
        StoreDateBatchProduct storeDateBatchProduct =
                new StoreDateBatchProduct(storeDateBatch, product, price,
                        currency);
        storeDateBatchProductService.save(storeDateBatchProduct);
        return storeDateBatchProduct;
    }

    private StoreDiscountDateBatch createAndSaveStoreDiscountDateBatch(
            Store store, String fromDate, String toDate) {

        LocalDate fromDateObj = LocalDate.parse(fromDate);
        LocalDate toDateObj = LocalDate.parse(toDate);

        StoreDiscountDateBatch storeDiscountDateBatch =
                storeDiscountDateBatchService.findByStoreAndFromDateAndToDate(store, fromDateObj, toDateObj).orElse(null);
        if(storeDiscountDateBatch == null) {
            storeDiscountDateBatch = new StoreDiscountDateBatch(store,
                fromDateObj, toDateObj);
            storeDiscountDateBatchService.save(storeDiscountDateBatch);
        }

        return storeDiscountDateBatch;
    }

    private StoreDiscountDateBatchProduct createAndSaveStoreDiscountDateBatchProduct(
            StoreDiscountDateBatch storeDiscountDateBatch, Product product,
            double percentageOfDiscount) {
        StoreDiscountDateBatchProduct storeDiscountDateBatchProduct =
                new StoreDiscountDateBatchProduct(storeDiscountDateBatch,
                        product, percentageOfDiscount);

        storeDiscountDateBatchProductService.save(
                storeDiscountDateBatchProduct);
        return storeDiscountDateBatchProduct;
    }

}