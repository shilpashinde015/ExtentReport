package PageObject;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.io.*;
import java.util.*;


public class loginFeature extends BasePage {
    private WebDriver driver;
    private String url;
    private static String[] columns = {"userName", "Password", "Status"};
    private String sheetName = "details";
    XSSFCell status;
    @FindBy(xpath = "//*[@id=\"app\"]/div/div/div[2]/div/div[6]/div/div[3]/h5")
    private WebElement accountlogo;
    @FindBy(xpath = "//*[@id=\"item-0\"]")
    private WebElement item;

    @FindBy(id = "userName")
    private WebElement uName;
    @FindBy(id = "password")
    private WebElement uPass;


    public loginFeature(WebDriver driver) {
        super(driver);
        this.driver = driver;
        PageFactory.initElements(driver, this);
        this.url = "https://demoqa.com";
    }

    @Override
    public boolean isAt() {
        return false;
    }

    public void goToHome() {

        this.driver.get(this.url);
    }

    public void loginAccount() {
        driver.get("https://demoqa.com/login");
    }

    public void enterCredential(String username, String password) {
        uName.sendKeys(username);
        uPass.sendKeys(password);
    }

    public void displayMessage() {
        System.out.println("Login Successfully");
    }

    public void fillBackgroundColor(XSSFWorkbook wb, String result, XSSFCell cell) {
        XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
        if (result.equalsIgnoreCase("pass")) {
            style.setFillForegroundColor(new XSSFColor(new java.awt.Color(0, 102, 0)));
        } else if (result.equalsIgnoreCase("fail")) {
            style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255, 0, 0)));
        }
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        cell.setCellStyle(style);
    }

    public void Read_file_O(String path, String username, String password, XSSFCell status) throws IOException {
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet(sheetName);

        Map<String, Map<String, XSSFCell>> data = new HashMap<>();
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        System.out.println("rowcount " + rowCount);
        Map<String, XSSFCell> dataMap = new HashMap<>();
        for (int i = 1; i <= rowCount; i++) {
            username = sheet.getRow(i).getCell(0).getStringCellValue();
            password = sheet.getRow(i).getCell(1).getStringCellValue();
            status = sheet.getRow(i).getCell(2);
            dataMap.put(password, status);
            data.put(username, dataMap);
        }

        System.out.println("Size_map_2: " + data.size());


    }

    public void write_file_o(String username, String password, XSSFCell status) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        CreationHelper createHelper = workbook.getCreationHelper();
        Sheet sheet = workbook.createSheet("Result");
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        Row row = sheet.createRow(rowNum++);
        row.createCell(0).setCellValue(username);
        row.createCell(1).setCellValue(password);
        row.createCell(2).setCellValue(String.valueOf(status));

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream("poi-generated-file.xlsx");
        workbook.write(fileOut);
        fileOut.close();

    }

    public Map<String, Object[]> Read_file(String path) throws IOException {
        String username;
        String password;
        File file = new File(path);
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = wb.getSheet(sheetName);
        int i;
        Map<String, Object[]> dataMap = new HashMap<>();
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        for (i = 1; i < rowCount; i++) {
            username = sheet.getRow(i).getCell(0).getStringCellValue();
            password = sheet.getRow(i).getCell(1).getStringCellValue();
            dataMap.put(String.valueOf(i), new Object[]{username, password});
        }
        return dataMap;
    }

    public void write_file(Map<String, Object[]> mapData, String status) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Result");
        Font headerFont = workbook.createFont();
        headerFont.setBoldweight(Font.BOLDWEIGHT_NORMAL);
        headerFont.setFontHeightInPoints((short) 14);
        headerFont.setColor(IndexedColors.RED.getIndex());
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);

        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        //Iterate over data and write to sheet
        Set<String> keyid = mapData.keySet();
        for (String key : keyid) {
            System.out.println("key_id : " + key);
        }
        for (String key : keyid) {
            Row row = sheet.createRow(rowNum++);
            Object[] objArr = mapData.get(key);
            int cellnum = 0;
            for (Object obj : objArr) {
                Cell cell = row.createCell(cellnum++);
                if (obj instanceof String) {
                    cell.setCellValue((String) obj);

                }

            }

            XSSFCellStyle ce= workbook.createCellStyle();
            ce.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
            ce.setFillPattern(CellStyle.SOLID_FOREGROUND);
            Cell c = row.createCell(cellnum++);
            c.setCellValue(status);
            c.setCellStyle(ce);
        }

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream out = new FileOutputStream(
                new File("/home/shilpa/IdeaProjects/ExtentReport/src/test/resources/Writesheet.xlsx"));

        workbook.write(out);
        out.close();
        System.out.println("Writesheet.xlsx written successfully");
    }


}
